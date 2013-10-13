package com.dvlcube.game.arkanoid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 13/10/2013
 */
public class ArkanoidGame extends Game {
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private List<Block> blocks = new ArrayList<>();

	public int createBlocks() {
		int nblocks = 0;
		int margin = 10;
		// int width = random.nextInt(95) + 5;
		// int height = random.nextInt(10) + 5;
		int width = 100;
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
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 13/10/2013
	 */
	public ArkanoidGame(Dimension screen, double scale) {
		super(screen, scale);
		createBlocks();
		createPlatform();
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
			ArkanoidGame.super.keyPressed(e);
		}
	}
}
