package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class DrawableObject implements Drawable {

	protected Game game;
	public int axis = 0;
	public int width, height;
	public int x, y;

	public DrawableObject() {
	}

	public DrawableObject(Dimension dimension, Point point) {
		this.width = dimension.width;
		this.height = dimension.height;
		this.x = point.x;
		this.y = point.y;
	}

	public DrawableObject(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public DrawableObject(Dimension dimension) {
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

	public boolean intersects(ControllableObject o) {
		int tw = this.width;
		int th = this.height;
		int rw = o.width;
		int rh = o.height;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		int tx = this.x;
		int ty = this.y;
		int rx = o.x;
		int ry = o.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}
}
