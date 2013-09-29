package com.dvlcube.game.pong;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.GameElement;

/**
 * @author wonka
 * @since 26/09/2013
 */
public class PongGame extends Game {

	private final List<Controllable> elements = new ArrayList<Controllable>();
	private MouseAdapter mouse = new Mouse();
	private Controllable player1 = new Paddle(new Point(10, 10));
	private Controllable player2 = new Paddle(new Point(scale(screen.width)
			- (10 + player1.width()), 10));
	private Controllable ball = new Ball(new Point(player1.x() + 10,
			player1.y() + 10));

	{
		add(player1);
		add(player2);
		add(ball);
		addTerminatables(ball);
	}

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public PongGame(Dimension screen, double scale) {
		super(screen, scale);
	}

	/**
	 * @param screen
	 * @author wonka
	 * @since 28/09/2013
	 */
	public PongGame(Dimension screen) {
		super(screen);
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
			int x = scale(e.getX()), y = scale(e.getY());
			for (Controllable element : elements) {
				element.mouseMoved(e, x, y);
			}
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

	@Override
	public List<Controllable> getControllables() {
		return elements;
	}
}