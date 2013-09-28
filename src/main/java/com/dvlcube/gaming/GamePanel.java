package com.dvlcube.gaming;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * From: Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
 * <p>
 * The call to Toolkit.sync( ) after drawImage( ) ensures that the display is
 * promptly updated. This is required for Linux, which doesn't automatically
 * flush its display buffer. Without the sync( ) call, the animation may be only
 * partially updated, creating a "tearing" effect. My thanks to Kyle Husmann for
 * pointing this out.
 */
public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1299991756757114278L;

	/**
	 * Number of frames with a delay of 0 ms before the animation thread yields
	 * to other running threads.
	 */
	private static final int NO_DELAYS_PER_YIELD = 16;

	/**
	 * no. of frames that can be skipped in any one animation loop i.e the games
	 * state is updated but not rendered
	 */
	private static int MAX_FRAME_SKIPS = 5;

	private static final int PWIDTH = 500; // size of panel
	private static final int PHEIGHT = 400;

	private Thread animator; // for the animation
	private volatile boolean running = false; // stops the animation
	private volatile boolean isPaused = false;
	private volatile boolean gameOver = false; // for game termination

	// global variables for off-screen rendering
	private Graphics dbg;
	private Image dbImage = null;

	private final long period = 10; // 10ms, 100fps

	public void pauseGame() {
		isPaused = true;
	}

	public GamePanel() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		setFocusable(true);
		requestFocus(); // JPanel now receives key events
		readyForTermination();

		// create game components
		// ...

		// listen for mouse presses
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				testPress(e.getX(), e.getY());
			}
		});
	}

	private void readyForTermination() {
		addKeyListener(new KeyAdapter() {
			// listen for esc, q, end, ctrl-c
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if ((keyCode == KeyEvent.VK_ESCAPE)
						|| (keyCode == KeyEvent.VK_Q)
						|| (keyCode == KeyEvent.VK_END)
						|| ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
					running = false;
				}
			}
		});
	}

	// is (x,y) important to the game?
	private void testPress(int x, int y) {
		if (!isPaused && !gameOver) {
			// do something
		}
	}

	/**
	 * Wait for the JPanel to be added to the JFrame/JApplet before starting.
	 */
	@Override
	public void addNotify() {
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}

	// initialise and start the thread
	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	// called by the user to stop execution
	public void stopGame() {
		running = false;
	}

	/**
	 * Repeatedly update, render, sleep so loop takes close to period nsecs.
	 * Sleep inaccuracies are handled. The timing calculation use nanoseconds.
	 * 
	 * Overruns in update/renders will cause extra updates to be carried out so
	 * UPS tild== requested FPS
	 */
	@Override
	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;
		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime / 1000000L); // nano -> ms
				} catch (InterruptedException ex) {
				}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; frame took longer than the period
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
			while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				gameUpdate(); // update state but don't render
				skips++;
			}
			System.out.println("oi");
		}
		System.exit(0);
	}

	// actively render the buffer image to the screen
	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics(); // get the panel's graphic context
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);

			// sync the display on some systems (linux)
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}

	// draw the current frame to an image buffer
	private void gameRender() {
		if (dbImage == null) { // create the buffer
			dbImage = createImage(PWIDTH, PHEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				dbg = dbImage.getGraphics();
		}

		// clear the background
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

		// draw game elements
		// ...

		if (gameOver)
			gameOverMessage(dbg);
	}

	// center the game-over message
	private void gameOverMessage(Graphics g) {
		// code to calculate x and y...
		int x = 0;
		int y = 0;
		String msg = "Game Over";
		g.drawString(msg, x, y);
	}

	private void gameUpdate() {
		if (!isPaused && !gameOver)
			;// update game state ...
	}

	public void resumeGame() {
		isPaused = false;
	}
}
