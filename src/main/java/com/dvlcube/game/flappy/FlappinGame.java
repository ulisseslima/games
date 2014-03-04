package com.dvlcube.game.flappy;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
			g.drawString("High score: " + Score.best, xPosition, yPosition);
			g.drawString("Your score: " + Score.current, xPosition, yPosition + 10);
			g.drawString("(space to start again)", xPosition, yPosition + 20);
		} else {
			g.drawString(Score.current.toString(), Score.x, Score.y);
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

			int pipeh = sHeight() - (pipeTop.height + pipeMan.yGap);
			if (pipeh > 0) {
				Pipe pipeBottom = new Pipe(new Dimension(pipeMan.width, pipeh), new Point(pipeMan.currX, pipeh / 2));
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
		if (Score.current > Score.best) {
			Score.best = Score.current;
			newHiScore = true;
		}
	}

	private void startGame() {
		ended = false;
		Score.current = 0l;
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
		@Override
		public void mouseMoved(MouseEvent e) {
			FlappinGame.super.mouseMoved(e, e.getX(), e.getY());
		}
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

	private static class Score {
		public static Long current = 0l;
		public static Long best = 0l;
		public static int x = 10;
		public static int y = 10;
	}

	class PipeManagement {
		public float speed = 1;
		public int width = 40;
		public int xGap = (int) (width * 2.5);
		public int yGap = 40;
		public int maxHeight = sHeight() * 90 / 100;
		public int minHeight = 5;
		public int maxWidth = sWidth() / 2;
		public int minWidth = 5;

		public int currX = sWidth() / 2;
		public boolean fromBottom;

		/**
		 * @return
		 * @author wonka
		 * @since 02/03/2014
		 */
		public int randomHeight() {
			return random.nextInt(maxHeight);
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

				int debugXOffset = sWidth() - 150;
				int debugYOffset = 10;

				g.drawString("0_TOGGLE DEBUG", debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("BIRD ACCEL: " + acceleration, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("1/2_GRAV. PULL: " + gravPull, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("3/4_BIRD WEIGHT: " + weight, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("5/6_BIRD SPD: " + speed, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("◄/►_BIRD WIDTH: " + bird.width, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("▼/▲_BIRD HEIGHT: " + bird.height, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("ENDED: " + ended, debugXOffset, debugYOffset);
				debugYOffset += 10;
				g.drawString("SCALED_H: " + sHeight(), debugXOffset, debugYOffset);
			}
		}
	}
}
