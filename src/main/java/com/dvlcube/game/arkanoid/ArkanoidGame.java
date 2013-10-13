package com.dvlcube.game.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 13/10/2013
 */
public class ArkanoidGame extends Game {
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	public int point = 500;
	public Long score = 0l;
	public Long hiscore = 0l;
	public int multiplier = 1;

	public boolean gameEnded = false;
	public boolean newHiScore = false;

	public int scorex = 10;
	public int scorey = 10;

	private List<Block> blocks = new ArrayList<>();

	@Override
	public void doGraphics(Graphics2D g) {
		super.doGraphics(g);

		if (gameEnded) {
			int xPosition = sWidth() / 2;
			int yPosition = sHeight() / 2;
			g.drawString("High score: " + hiscore, xPosition, yPosition);
			g.drawString("Your score: " + score, xPosition, yPosition + 10);
			g.drawString("(space to start again)", xPosition, yPosition + 20);
		} else {
			g.drawString(score.toString(), scorex, scorey);
			g.drawString(multiplier + "x", scorex, scorey + 10);
		}
	}

	@Override
	public void objectTrashed(ControllableObject controllableObject) {
		if (controllableObject instanceof Block) {
			blocks.remove(controllableObject);
		}

		if (blocks.isEmpty()) {
			gameEnded = true;
			endGame();
		} else {
			gameEnded = false;
		}
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void endGame() {
		if (score > hiscore) {
			hiscore = score;
			newHiScore = true;
		}
	}

	private void startGame() {
		gameEnded = false;
		createBlocks();
		multiplier = 1;
		score = 0l;
		newHiScore = false;
	}

	@Override
	public void collisionEvent(ControllableObject hitter, Object hitObject) {
		if (!gameEnded) {
			if (hitObject instanceof Block) {
				score += point * multiplier;
				multiplier++;
			} else if (hitObject instanceof Platform) {
				multiplier = 1;
			} else if (hitObject instanceof Pit) {
				multiplier = 1;
				score -= point * 3;
			}
		}
	}

	public int createBlocks() {
		int nblocks = 0;
		int margin = 10;
		// int width = random.nextInt(95) + 5;
		// int height = random.nextInt(10) + 5;
		int width = 80;
		int height = 10;

		for (int x = margin; x < sWidth() - margin;) {
			for (int y = margin; y < sHeight() / 2;) {
				// width = random.nextInt(95) + 5;
				// height = random.nextInt(10) + 5;

				addBlock(width, height, x, y);
				nblocks++;
				y += height;
			}
			x += width;
		}
		return nblocks;
	}

	private void addBlock(int width, int height, int x, int y) {
		Block block = new Block(new Dimension(width, height), new Point(x, y));
		blocks.add(block);
		addObject(block);
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void createPlatform() {
		addObject(new Platform(new Point(sWidth() / 2, sHeight() - 20)));
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void createBall() {
		int bally = (sHeight() / 2) + 100;
		addObject(new Ball(new Point(sWidth() / 2, bally)));
	}

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void createPit() {
		addObject(new Pit(new Dimension(sWidth(), 1), new Point(0, sHeight() - 1)));
	}

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 13/10/2013
	 */
	public ArkanoidGame(Dimension screen, double scale) {
		super(screen, scale);
		createBlocks();
		createPlatform();
		createPit();
		createBall();
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
			ArkanoidGame.super.mouseMoved(e, e.getX(), e.getY());
		}
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (gameEnded) {
					startGame();
				}
				break;
			}
		}
	}
}
