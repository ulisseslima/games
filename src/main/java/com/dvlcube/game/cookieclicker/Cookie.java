package com.dvlcube.game.cookieclicker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Action;
import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;

/**
 * 
 * @author wonka
 * @since 29/09/2013
 */
public class Cookie extends ControllableObject {

	private Dimension screen;
	public static final Dimension DEFAULT_SIZE = new Dimension(100, 100);
	public static final Point DEFAULT_LOCATION = new Point(10, 10);
	private int growRadius = 5;
	private Dimension grownSize = new Dimension(DEFAULT_SIZE.width + growRadius, DEFAULT_SIZE.height + growRadius);
	private boolean grow = false;

	/**
	 * @author wonka
	 * @since 29/09/2013
	 */
	public Cookie(Action action) {
		super(DEFAULT_SIZE, DEFAULT_LOCATION);
		this.action = action;
	}

	@Override
	public void update() {
		if (grow) {
			if (width < grownSize.width) {
				width++;
				height++;
			}
		} else {
			if (width > grownSize.width - growRadius) {
				width--;
				height--;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillOval(x, y, width, height);
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

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {
		if (isInside(x, y)) {
			grow = true;
		} else {
			grow = false;
		}
	}
}
