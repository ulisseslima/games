package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class DefaultDrawable implements Drawable {
	protected Game game;
	public int width;
	public int height;
	public int x;
	public int y;
	public int axis = 0;

	public DefaultDrawable() {
	}

	public DefaultDrawable(Dimension dimension, Point point) {
		this.width = dimension.width;
		this.height = dimension.height;
		this.x = point.x;
		this.y = point.y;
	}

	public DefaultDrawable(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public DefaultDrawable(Dimension dimension) {
		this.width = dimension.width;
		this.height = dimension.height;
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}

	@Override
	public void setSource(Game game) {
		this.game = game;
	}

	public boolean withinX(int ox) {
		return ox >= x && ox <= x + width;
	}

	public boolean withinY(int oy) {
		return oy >= y && oy <= y + height;
	}

	public boolean isInside(int mx, int my) {
		return withinX(mx) && withinY(my);
	}
}
