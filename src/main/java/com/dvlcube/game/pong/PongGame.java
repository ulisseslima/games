package com.dvlcube.game.pong;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 26/09/2013
 */
public class PongGame extends Game {
	public static final float VERSION = 2.1f;

	private MouseAdapter mouse = new Mouse();
	private Controllable player1 = new Paddle(new Point(10, 10));
	private Controllable player2 = new Paddle(new Point(scale(screen.width) - (10 + player1.width()), 10));
	private Controllable ball = new Ball(new Point(player1.x() + 10, player1.y() + 10));

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public PongGame(Dimension screen, double scale) {
		super(screen, scale);
		addObject(player1);
		addObject(player2);
		addObject(ball);
		ball.setSource(this);
		player1.setSource(this);
		player2.setSource(this);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			PongGame.super.mouseMoved(e, e.getX(), e.getY());
		}
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return null;
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public void reset() {
	}
}