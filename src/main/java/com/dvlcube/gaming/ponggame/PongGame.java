package com.dvlcube.gaming.ponggame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.DefaultSoundTrack;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.GameElement;

/**
 * @author wonka
 * @since 26/09/2013
 */
public class PongGame implements Game {
	public boolean debug = false;

	/**
	 * Effective screen size
	 */
	public final int SC_W = 1024 / 2;
	public final int SC_H = 768 / 2;

	private DefaultSoundTrack soundTrack = new DefaultSoundTrack();
	private final List<Controllable> elements = new ArrayList<Controllable>();
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private Controllable player1 = new Paddle(new Point(10, 10));
	private Controllable player2 = new Paddle(new Point(SC_W
			- (10 + player1.width()), 10));
	private Controllable ball = new Ball(new Point(player1.x() + 10,
			player1.y() + 10));

	{
		soundTrack.osc.setWaveshape(WAVESHAPE.SIN);
		add(player1);
		add(player2);
		add(ball);
	}

	@Override
	public void doLogic() {
		for (GameElement element : elements) {
			element.update();
		}
	}

	/**
	 * @param player12
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void add(Controllable element) {
		elements.add(element);
		element.setSource(this);
	}

	@Override
	public void doGraphics(Graphics2D g) {
		for (GameElement element : elements) {
			element.draw(g);
		}
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX() / 2, y = e.getY() / 2;
			for (Controllable element : elements) {
				element.mouseMoved(e, x, y);
			}
		}
	}

	public class Keyboard extends KeyAdapter {
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
		return elements;
	}

	@Override
	public DefaultSoundTrack getSoundTrack() {
		return soundTrack;
	}
}