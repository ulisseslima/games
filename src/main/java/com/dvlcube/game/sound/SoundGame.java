package com.dvlcube.game.sound;

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
public class SoundGame extends Game {
	public boolean debug = true;
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private Synthesizer synth = new Synthesizer();

	{
		synth.setSource(this);
		addTerminatables(synth);
	}

	public SoundGame(Dimension screen) {
		super(screen);
	}

	public SoundGame(Dimension screen, double scale) {
		super(screen, scale);
	}

	@Override
	public void doLogic() {
	}

	@Override
	public void doGraphics(Graphics2D g) {
		synth.draw(g);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			synth.mouseMoved(e, screen.width, screen.height);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			synth.mousePressed(e, scale(e.getX()), scale(e.getY()));
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			synth.mouseWheelMoved(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			synth.mouseDragged(e, screen.width, screen.height);
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			synth.keyPressed(e);
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
	public List<Controllable> getControllables() {
		return null;
	}
}