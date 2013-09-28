package com.dvlcube.gaming.soundgame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.sound.Synthesizer;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SoundGame implements Game {
	public boolean debug = true;
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private Synthesizer sound = new Synthesizer();

	/**
	 * Screen
	 */
	private final int SC_W = 1024;
	private final int SC_H = 768;

	{
		sound.setSource(this);
	}

	@Override
	public void doLogic() {
	}

	@Override
	public void doGraphics(Graphics2D g) {
		sound.draw(g);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			sound.mouseMoved(e, SC_W, SC_H);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			sound.mousePressed(e, e.getX() / 2, e.getY() / 2);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			sound.mouseWheelMoved(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			sound.mouseDragged(e, SC_W, SC_H);
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			sound.keyPressed(e);
		}
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public void reset() {
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(SC_W, SC_H);
	}

	@Override
	public List<Controllable> getControllables() {
		return null;
	}
}