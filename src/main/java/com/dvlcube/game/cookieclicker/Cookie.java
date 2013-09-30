package com.dvlcube.game.cookieclicker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Action;
import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.util.PolygonFactory;

/**
 * 
 * @author wonka
 * @since 29/09/2013
 */
public class Cookie extends ControllableObject {

	private Dimension screen;
	public static final Dimension DEFAULT_SIZE = new Dimension(50, 50);
	public static final Point DEFAULT_LOCATION = new Point(20, 10);
	public Polygon shape;

	/**
	 * @param point
	 * @author wonka
	 * @since 29/09/2013
	 */
	public Cookie(Point point) {
		super(DEFAULT_SIZE, point);
		shape = PolygonFactory.hexagon(x, y);
	}

	/**
	 * @author wonka
	 * @since 29/09/2013
	 */
	public Cookie(Action action) {
		this(new Point(DEFAULT_LOCATION.x + DEFAULT_SIZE.width, DEFAULT_LOCATION.y + DEFAULT_SIZE.height));
		this.action = action;
	}

	@Override
	public void update() {
		shape = PolygonFactory.hexagon(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawPolygon(shape);
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		if (activated(mx, my)) {
			doAction();
		}
	}

	@Override
	public void setSource(Game game) {
		screen = game.getScaledScreen();
		y = screen.height / 2;
	}
}
