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
		int thisw = this.width;
		int thish = this.height;
		int otherw = o.width;
		int otherh = o.height;
		if (otherw <= 0 || otherh <= 0 || thisw <= 0 || thish <= 0) {
			return false;
		}
		int thisx = this.x;
		int thisy = this.y;
		int otherx = o.x;
		int othery = o.y;
		otherw += otherx;
		otherh += othery;
		thisw += thisx;
		thish += thisy;
		// overflow || intersect
		return ((otherw < otherx || otherw > thisx) && (otherh < othery || otherh > thisy)
				&& (thisw < thisx || thisw > otherx) && (thish < thisy || thish > othery));
	}

	public Collision collided(ControllableObject o) {
		int thisw = this.width;
		int thish = this.height;
		int otherw = o.width;
		int otherh = o.height;
		if (otherw <= 0 || otherh <= 0 || thisw <= 0 || thish <= 0) {
			return Collision.NO_COLLISION;
		}
		int thisx = this.x;
		int thisy = this.y;
		int otherx = o.x;
		int othery = o.y;
		otherw += otherx;
		otherh += othery;
		thisw += thisx;
		thish += thisy;
		// overflow || intersect
		boolean overFlowsOrIntersects = ((otherw < otherx || otherw > thisx) && (otherh < othery || otherh > thisy)
				&& (thisw < thisx || thisw > otherx) && (thish < thisy || thish > othery));

		if (overFlowsOrIntersects) {
			int sumx = this.x - o.x;
			int sumy = this.y - o.y;

			if (sumx > sumy)
				return Collision.NORTH;
			else
				return Collision.WEST;
		}
		return Collision.NO_COLLISION;
	}

	@Override
	public boolean isVisible() {
		return y() < game.sHeight() && x() < game.sWidth() && (y() + height()) > 0 && (x() + width()) > 0;
	}
}
