package com.dvlcube.gaming;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import com.dvlcube.gaming.ponggame.PongGame;

/**
 * From: Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
 * <p>
 * The game's drawing surface. It shows: - the game - the current average FPS
 * and UPS
 */
public class RunnableGamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int PANEL_WIDTH = 1024;
	private static final int PANEL_HEIGHT = 768;

	private static long MAX_STATS_INTERVAL = 1000000000L;
	// private static long MAX_STATS_INTERVAL = 1000L;
	// record stats every 1 second (roughly)

	private static final int NO_DELAYS_PER_YIELD = 16;
	/*
	 * Number of frames with a delay of 0 ms before the animation thread yields
	 * to other running threads.
	 */
	private static int MAX_FRAME_SKIPS = 5;
	// no. of frames that can be skipped in any one animation loop
	// i.e the games state is updated but not rendered

	private static int NUM_FPS = 10;
	// number of FPS values stored to get an average

	// used for gathering statistics
	private long statsInterval = 0L; // in ns
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	private int timeSpentInGame = 0; // in seconds

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;

	private DecimalFormat decimalFormat = new DecimalFormat("0.##"); // 2 dp
	// private DecimalFormat timedf = new DecimalFormat("0.####"); // 4 dp

	/**
	 * the thread that performs the animation
	 */
	private Thread animator;
	/**
	 * used to stop the animation thread
	 */
	private volatile boolean isRunning = false;
	private boolean isPaused = false;

	private long fpsPeriod; // period between drawing in _nanosecs_

	private GameWindow window;
	private final Game game = new PongGame();

	// used at game termination
	private boolean gameOver = false;
	private Font font;
	private FontMetrics metrics;

	// off screen rendering
	private Graphics graphics;
	private Image dbImage = null;
	public static final Color bgColor = new Color(60, 60, 60);
	public static final Color fgColor = new Color(8, 130, 230);

	public RunnableGamePanel(GameWindow window, long period) {
		this.window = window;
		this.fpsPeriod = period;

		setBackground(Color.white);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		setFocusable(true);
		requestFocus(); // the JPanel now has focus, so receives key events
		readyForTermination();

		resetState();

		addMouseListener(game.getMouseAdapter());
		addMouseMotionListener(game.getMouseAdapter());
		addMouseWheelListener(game.getMouseAdapter());

		// set up message font
		font = new Font("SansSerif", Font.BOLD, 10);
		metrics = this.getFontMetrics(font);

		// initialize timing elements
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];
		for (int i = 0; i < NUM_FPS; i++) {
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
	}

	/**
	 * create game components
	 */
	private void resetState() {
		game.reset();
	}

	private void readyForTermination() {
		addKeyListener(game.getKeyAdapter());
	}

	@Override
	public void addNotify() {
		// wait for the JPanel to be added to the JFrame before starting
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}

	/**
	 * initialize and start the thread
	 */
	private void startGame() {
		if (animator == null || !isRunning) {
			animator = new Thread(this);
			animator.start();
		}
	}

	/**
	 * called when the JFrame is activated / deiconified
	 */
	public void resumeGame() {
		isPaused = false;
	}

	/**
	 * called when the JFrame is deactivated / iconified
	 */
	public void pauseGame() {
		isPaused = true;
	}

	/**
	 * called when the JFrame is closing
	 */
	public void stopGame() {
		isRunning = false;
	}

	public void restartGame() {
		stopGame();
		resetState();
		startGame();
	}

	@Override
	public synchronized void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		gameStartTime = System.nanoTime();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;

		isRunning = true;

		while (isRunning) {
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (GameWindow.getFpsPeriod() - timeDiff) - overSleepTime;

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime / 1000000L); // nano -> ms
				} catch (InterruptedException ex) {
				}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; the frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield(); // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */
			int skips = 0;
			while ((excess > fpsPeriod) && (skips < MAX_FRAME_SKIPS)) {
				excess -= fpsPeriod;
				gameUpdate(); // update state but don't render
				skips++;
			}
			framesSkipped += skips;
			storeStats();
		}
		printStats();
	}

	private void gameUpdate() {
		if (!isPaused && !gameOver) {
			game.doLogic();
		}
	}

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(PANEL_WIDTH / 2, PANEL_HEIGHT / 2);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				graphics = dbImage.getGraphics();
		}

		// clear the background
		graphics.setColor(bgColor);
		graphics.fillRect(0, 0, PANEL_WIDTH / 2, PANEL_HEIGHT / 2);

		graphics.setColor(fgColor);
		graphics.setFont(font);

		// report frame count & average FPS and UPS at top left
		// dbg.drawString("Frame Count " + frameCount, 10, 25);
		graphics.drawString(
				"Average FPS/UPS: " + decimalFormat.format(averageFPS) + ", "
						+ decimalFormat.format(averageUPS), 20, 25);

		graphics.setColor(fgColor);

		// draw game elements
		game.doGraphics((Graphics2D) graphics);

		if (gameOver)
			gameOverMessage(graphics);
	}

	/**
	 * center the game-over message in the panel
	 * 
	 * @param g
	 *            graphics 2d.
	 */
	private void gameOverMessage(Graphics g) {
		String msg = "Game Over.";
		int x = (PANEL_WIDTH - metrics.stringWidth(msg)) / 2;
		int y = (PANEL_HEIGHT - metrics.getHeight()) / 2;
		g.setColor(Color.red);
		g.setFont(font);
		g.drawString(msg, x, y);
	}

	/**
	 * use active rendering to put the buffered image on-screen
	 */
	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}

	/**
	 * The statistics: - the summed periods for all the iterations in this
	 * interval (period is the amount of time a single frame iteration should
	 * take), the actual elapsed time in this interval, the error between these
	 * two numbers;
	 * 
	 * - the total frame count, which is the total number of calls to run();
	 * 
	 * - the frames skipped in this interval, the total number of frames
	 * skipped. A frame skip is a game update without a corresponding render;
	 * 
	 * - the FPS (frames/sec) and UPS (updates/sec) for this interval, the
	 * average FPS & UPS over the last NUM_FPSs intervals.
	 * 
	 * The data is collected every MAX_STATS_INTERVAL (1 sec).
	 */
	private void storeStats() {
		frameCount++;
		statsInterval += fpsPeriod;

		// record stats every MAX_STATS_INTERVAL
		if (statsInterval >= MAX_STATS_INTERVAL) {
			long timeNow = System.nanoTime();
			// ns --> secs
			timeSpentInGame = (int) ((timeNow - gameStartTime) / 1000000000L);

			long realElapsedTime = timeNow - prevStatsTime; // time since last
															// stats collection
			totalElapsedTime += realElapsedTime;

			// double timingError = ((double) (realElapsedTime - statsInterval)
			// / statsInterval) * 100.0;

			totalFramesSkipped += framesSkipped;

			double actualFPS = 0; // calculate the latest FPS and UPS
			double actualUPS = 0;
			if (totalElapsedTime > 0) {
				actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
				actualUPS = (((double) (frameCount + totalFramesSkipped) / totalElapsedTime) * 1000000000L);
			}

			// store the latest FPS and UPS
			fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
			upsStore[(int) statsCount % NUM_FPS] = actualUPS;
			statsCount = statsCount + 1;

			double totalFPS = 0.0; // total the stored FPSs and UPSs
			double totalUPS = 0.0;
			for (int i = 0; i < NUM_FPS; i++) {
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}

			if (statsCount < NUM_FPS) { // obtain the average FPS and UPS
				averageFPS = totalFPS / statsCount;
				averageUPS = totalUPS / statsCount;
			} else {
				averageFPS = totalFPS / NUM_FPS;
				averageUPS = totalUPS / NUM_FPS;
			}
			/*
			 * System.out.println(timedf.format( (double)
			 * statsInterval/1000000000L) + " " + timedf.format((double)
			 * realElapsedTime/1000000000L) + "s " + df.format(timingError) +
			 * "% " + frameCount + "c " + framesSkipped + "/" +
			 * totalFramesSkipped + " skip; " + df.format(actualFPS) + " " +
			 * df.format(averageFPS) + " afps; " + df.format(actualUPS) + " " +
			 * df.format(averageUPS) + " aups" );
			 */
			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L; // reset
		}
	}

	private void printStats() {
		System.out.println("Frame Count/Loss: " + frameCount + " / "
				+ totalFramesSkipped);
		System.out.println("Average FPS: " + decimalFormat.format(averageFPS));
		System.out.println("Average UPS: " + decimalFormat.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " secs");
	}

}
