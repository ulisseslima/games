package com.dvlcube.game.flappy;

import static com.dvlcube.gaming.util.Cuber.$;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.physics.PhysicalProperties;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class FlappinGame extends Game {
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

	public boolean newHiScore = false;

	public long lastDebugCalc = 0;
	float gravPull = 0;
	float weight = 0;
	float speed = 0;
	float acceleration = 0;
	private Pipe lastPipe;

	@Override
	public void doGraphics(Graphics2D g) {
		super.doGraphics(g);

		if (ended) {
			int xPosition = sWidth() / 2;
			int yPosition = sHeight() / 2;
			$("High score: " + score.best).write(xPosition, yPosition);
			$("Your score: " + score.current).write(xPosition, yPosition + 10);
			$("(space to start again)").write(xPosition, yPosition + 20);
		} else {
			score.draw();
			createPipes();
		}

		test.doGraphics(g);
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
			Pipe pipeTop = new Pipe(new Dimension(pipeMan.width, pipeMan.randomHeight()), new Point(pipeMan.currX,
					pipey));
			pipeTop.setPipeMan(pipeMan);
			addObject(pipeTop);
			lastPipe = pipeTop;

			int pipeh = pipeTop.height + pipeMan.yGap;
			if (pipeh > 0) {
				Dimension dimension = new Dimension(pipeMan.width, sHeight() - pipeh);
				Point point = new Point(pipeMan.currX, pipeh);
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
			newHiScore = true;
		}
	}

	private void startGame() {
		ended = false;
		score.current = 0l;
		bird.y = 200;
		newHiScore = false;
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
				}
				break;

			case KeyEvent.VK_0:
				debug = !debug;
				break;

			default:
				if (!ended)
					FlappinGame.super.keyPressed(e);
			}

			test.handleInput(e);
		}
	}

	class Score {
		public int size = 40;
		private final Font FONT = new Font("SansSerif", Font.BOLD, size);
		public Long current = 0l;
		public Long best = 0l;
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
		public float speed = 1;
		public float minSpeed = 0;
		public int width = 40;
		public int xGap = (int) (this.width * 2.5);
		public int yGap = 80;
		public int maxHeight = sHeight() / 2;
		public int minHeight = 20;
		public int maxWidth = sWidth() / 2;
		public int minWidth = 5;

		public int currX = sWidth() / 2;
		public boolean fromBottom;

		/**
		 * @return random height for the pipe, ranging from the min height to the maximum height.
		 * @author wonka
		 * @since 02/03/2014
		 */
		public int randomHeight() {
			return random.nextInt(maxHeight) + minHeight;
		}
	}

	private class Test {

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

				if (elapsed > 500) {
					gravPull = pGround.gravitationalPull;
					weight = pBird.weight;
					speed = pBird.speed;
					acceleration = pBird.acceleration;
					lastDebugCalc = System.currentTimeMillis();
				}

				int debugx = sWidth() - 150;
				int debugy = 10;

				$("0_TOGGLE DEBUG").write(debugx, debugy);
				debugy += 10;
				$("ENDED: " + ended).write(debugx, debugy);
				debugy += 10;
				$("BIRD ACCEL: " + acceleration).write(debugx, debugy);
				debugy += 10;
				$("1/2_GRAV. PULL: " + gravPull).write(debugx, debugy);
				debugy += 10;
				$("3/4_BIRD WEIGHT: " + weight).write(debugx, debugy);
				debugy += 10;
				$("5/6_BIRD SPD: " + speed).write(debugx, debugy);
				debugy += 10;
				$("◄/►_BIRD WIDTH: " + bird.width).write(debugx, debugy);
				debugy += 10;
				$("▼/▲_BIRD HEIGHT: " + bird.height).write(debugx, debugy);
				debugy += 10;
				$("7/8_SPEED: " + pipeMan.speed).write(debugx, debugy);
			}
		}
	}
}
