package com.dvlcube.game.flappy;

import static com.dvlcube.gaming.util.Cuber.$;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.Terminatable;
import com.dvlcube.gaming.physics.PhysicalProperties;
import com.dvlcube.gaming.sound.Sfx;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class FlappinGame extends Game implements Terminatable {
	{
		debug = true;
	}
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private Ground ground;
	private Bird bird;
	private Test test = new Test();
	private PipeManagement pipeMan = new PipeManagement();
	private Score score = new Score();
	private volatile LinkedList<Pipe> pipesAhead = new LinkedList<>();
	private Sfx pleen = new Sfx("0001.wav");
	private Pipe lastPipe;

	@Override
	public void doLogic() {
		super.doLogic();
		createPipes();

		Pipe pipeAhead = pipesAhead.peekFirst();
		if (pipeAhead != null) {
			if (bird.x + bird.width >= pipeAhead.getScoreMark()) {
				score.current++;
				pipesAhead.pop();
				pleen.play();
			}
		}
	}

	@Override
	public void doGraphics(Graphics2D g) {
		super.doGraphics(g);

		if (ended) {
			showGameOverInfo();
		} else {
			score.draw();
		}

		test.doGraphics(g);
	}

	private void showGameOverInfo() {
		int _x = sWidth() / 2;
		int _y = sHeight() / 2;
		$("High score: " + score.best).write(_x, _y);
		$("Your score: " + score.current).write(_x, _y + 10);
		$("(space to start again)").write(_x, _y + 20);
	}

	/**
	 * Pipes are always moving to the left, creating an illusion of movement.
	 * 
	 * @author wonka
	 * @since 02/03/2014
	 */
	private void createPipes() {
		if (pipeMan.currX <= sWidth()) {
			int pipey = 0;
			Dimension dimension = new Dimension(pipeMan.width, pipeMan.randomHeight());
			Point point = new Point(pipeMan.currX, pipey);
			Pipe pipeTop = new Pipe(dimension, point);
			pipeTop.setPipeMan(pipeMan);
			addObject(pipeTop);
			lastPipe = pipeTop;
			pipesAhead.add(pipeTop);

			int pipeh = pipeTop.height + pipeMan.yGap;
			if (pipeh > 0) {
				dimension = new Dimension(pipeMan.width, sHeight() - pipeh);
				point = new Point(pipeMan.currX, pipeh);
				Pipe pipeBottom = new Pipe(dimension, point);
				pipeBottom.setPipeMan(pipeMan);
				addObject(pipeBottom);
			}

			pipeMan.currX += pipeMan.xGap + pipeMan.width;
		} else {
			if (lastPipe != null)
				pipeMan.currX = lastPipe.x + lastPipe.width + pipeMan.xGap;
		}
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void endGame() {
		ended = true;
		if (score.current > score.best) {
			score.best = score.current;
			score.newHiScore = true;
		}
	}

	private void startGame() {
		ended = false;
		score.current = 0l;
		bird.y = 200;
		score.newHiScore = false;
		pipesAhead.clear();
		pipeMan.reset();
		lastPipe = null;
		destroyAll(Pipe.class);
	}

	@Override
	public void collisionEvent(ControllableObject hitter, Object hitObject) {
		if (!ended) {
			if (hitObject instanceof Ground || hitObject instanceof Pipe) {
				endGame();
			}
		}
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void createBird() {
		bird = new Bird();
		addObject(bird);
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void createGround() {
		int height = 10;
		ground = new Ground(new Dimension(sWidth(), height), new Point(0, sHeight() - height));
		addObject(ground);
	}

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 13/10/2013
	 */
	public FlappinGame(Dimension screen, double scale) {
		super(screen, scale);
		createGround();
		createBird();
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	private class Mouse extends MouseAdapter {
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_SPACE:
				if (ended) {
					startGame();
				} else {
					FlappinGame.super.keyPressed(e);
				}
				break;

			case KeyEvent.VK_0:
				debug = !debug;
				break;
			}

			test.handleInput(e);
		}
	}

	class Score {
		public int size = 40;
		private final Font FONT = new Font("SansSerif", Font.BOLD, size);
		public Long current = 0l;
		public Long best = 0l;
		public boolean newHiScore = false;
		public int x = sWidth() / 2;
		public int y = sHeight() / 2 - 30;

		/**
		 * 
		 * @author wonka
		 * @since 08/03/2014
		 */
		public void draw() {
			$(score.current.toString(), FONT).write(score.x, score.y);
		}
	}

	class PipeManagement {
		public float speed = 1.6f;
		public float minSpeed = 0;
		public int width = 45;
		public int xGap = (int) (this.width * 2.5);
		public int yGap = 80;
		public int maxHeight = sHeight() / 2;
		public int minHeight = 20;
		public int maxWidth = sWidth() / 2;
		public int minWidth = 5;

		public int currX = sWidth() - 1;
		public boolean fromBottom;

		/**
		 * @return random height for the pipe, ranging from the min height to the maximum height.
		 * @author wonka
		 * @since 02/03/2014
		 */
		public int randomHeight() {
			return random.nextInt(maxHeight) + minHeight;
		}

		/**
		 * 
		 * @author wonka
		 * @since 08/03/2014
		 */
		public void reset() {
			currX = sWidth() - 1;
		}
	}

	private class Test {
		public long lastDebugCalc;
		float gravPull;
		float weight;
		float speed;
		float acceleration;
		float aero;

		int pipeWidth;
		int pipeDistance;
		int pipeGap;

		/**
		 * @param keyCode
		 * @author wonka
		 * @since 02/03/2014
		 */
		public void handleInput(KeyEvent e) {
			if (debug) {
				PhysicalProperties pGround = ground.getPhysicalProperties();
				PhysicalProperties pBird = bird.getPhysicalProperties();

				switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					pGround.gravitationalPull -= 0.01;
					break;
				case KeyEvent.VK_2:
					pGround.gravitationalPull += 0.01;
					break;

				case KeyEvent.VK_3:
					pBird.weight -= 0.05;
					break;
				case KeyEvent.VK_4:
					pBird.weight += 0.05;
					break;

				case KeyEvent.VK_5:
					pBird.speed -= 0.05;
					break;
				case KeyEvent.VK_6:
					pBird.speed += 0.05;
					break;

				case KeyEvent.VK_LEFT:
					bird.width -= 1;
					break;
				case KeyEvent.VK_RIGHT:
					bird.width += 1;
					break;

				case KeyEvent.VK_UP:
					bird.height += 1;
					break;
				case KeyEvent.VK_DOWN:
					bird.height -= 1;
					break;

				case KeyEvent.VK_7:
					pipeMan.speed -= 0.05;
					break;
				case KeyEvent.VK_8:
					pipeMan.speed += 0.05;
					break;

				case KeyEvent.VK_Q:
					pBird.aerodynamic -= 0.05;
					break;
				case KeyEvent.VK_W:
					pBird.aerodynamic += 0.05;
					break;

				case KeyEvent.VK_A:
					pipeMan.xGap -= 1;
					break;
				case KeyEvent.VK_S:
					pipeMan.xGap += 1;
					break;

				case KeyEvent.VK_Z:
					pipeMan.yGap -= 1;
					break;
				case KeyEvent.VK_X:
					pipeMan.yGap += 1;
					break;

				case KeyEvent.VK_E:
					pipeMan.width -= 1;
					break;
				case KeyEvent.VK_R:
					pipeMan.width += 1;
					break;
				}
			}
		}

		/**
		 * @param g
		 * @author wonka
		 * @since 02/03/2014
		 */
		public void doGraphics(Graphics2D g) {
			if (debug) {
				PhysicalProperties pGround = ground.getPhysicalProperties();
				PhysicalProperties pBird = bird.getPhysicalProperties();

				long elapsed = System.currentTimeMillis() - lastDebugCalc;

				if (elapsed > 200) {
					gravPull = pGround.gravitationalPull;
					weight = pBird.weight;
					speed = pBird.speed;
					acceleration = pBird.acceleration;
					lastDebugCalc = System.currentTimeMillis();
					aero = pBird.aerodynamic;
					pipeDistance = pipeMan.xGap;
					pipeGap = pipeMan.yGap;
					pipeWidth = pipeMan.width;
				}

				int debugx = sWidth() - 150;
				int debugy = 10;

				$("0_TOGGLE DEBUG").write(debugx, debugy);
				debugy += 10;
				$("BIRD ACCEL: " + acceleration).write(debugx, debugy);
				debugy += 10;
				$("1/2_GRAV. PULL: " + gravPull).write(debugx, debugy);
				debugy += 10;
				$("3/4_BIRD WEIGHT: " + weight).write(debugx, debugy);
				debugy += 10;
				$("5/6_BIRD SPD: " + speed).write(debugx, debugy);
				debugy += 10;
				$("7/8_SPEED: " + pipeMan.speed).write(debugx, debugy);
				debugy += 10;
				$("MAX_ACCEL: " + pBird.maxAcceleration).write(debugx, debugy);
				debugy += 10;
				$("◄/►_BIRD WIDTH: " + bird.width).write(debugx, debugy);
				debugy += 10;
				$("▼/▲_BIRD HEIGHT: " + bird.height).write(debugx, debugy);
				debugy += 10;
				$("Q/W_AERO: " + aero).write(debugx, debugy);
				debugy += 10;
				$("A/S_PIPE DISTANCE: " + pipeDistance).write(debugx, debugy);
				debugy += 10;
				$("Z/X_PIPE GAP: " + pipeGap).write(debugx, debugy);
				debugy += 10;
				$("E/R_PIPE WIDTH: " + pipeWidth).write(debugx, debugy);
			}
		}
	}

	@Override
	public void terminate() {
		pleen.terminate();
	}
}
