package com.dvlcube.game.biogame;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 01/10/2013
 */
public class BioGame extends Game {

	private KeyAdapter keyboard = new Keyboard();
	private MouseAdapter mouse = new Mouse();

	public int nBiomorphs = 10;

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 01/10/2013
	 */
	public BioGame(Dimension screen, double scale) {
		super(screen, scale);
		for (int i = 0; i < nBiomorphs; i++) {
			addObject(new Biomorph());
		}
	}

	@Override
	public void doLogic() {
		super.doLogic();
		if (random.nextInt(1000) < 2) {
			addObject(new Biomorph());
		}
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	@Override
	public void reset() {
	}

	private class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			BioGame.super.mouseMoved(e, e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			BioGame.super.mousePressed(e, e.getX(), e.getY());
		}
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			BioGame.super.keyPressed(e);
		}
	}
}
