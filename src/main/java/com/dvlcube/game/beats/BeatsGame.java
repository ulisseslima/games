package com.dvlcube.game.beats;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.sound.DrumSequencer;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class BeatsGame extends Game {
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private DrumSequencer sequencer = new DrumSequencer();

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public BeatsGame(Dimension screen, double scale) {
		super(screen, scale);
	}

	/**
	 * @param screen
	 * @author wonka
	 * @since 28/09/2013
	 */
	public BeatsGame(Dimension screen) {
		super(screen);
	}

	{
		sequencer.setSource(this);
		addObject(sequencer);
	}

	@Override
	public void doLogic() {
	}

	@Override
	public void doGraphics(Graphics2D g) {
		sequencer.draw(g);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (!terminating)
				sequencer.mousePressed(e, scale(e.getX()), scale(e.getY()));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!terminating)
				sequencer.mouseDragged(e, scale(e.getX()), scale(e.getY()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!terminating)
				sequencer.mouseReleased(e, scale(e.getX()), scale(e.getY()));
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (!terminating)
				sequencer.keyPressed(e);
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
}