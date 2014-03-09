package com.dvlcube.gaming;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class DrawableObject implements Drawable {

	protected Game game;
	public int axis = 0;
	/**
	 * angle in degrees.
	 */
	public double angle = 0;
	public int width, height;
	public int x, y;
	private Shape shape;
	public Color color;

	public DrawableObject() {
	}

	public DrawableObject(Dimension dimension, Point point) {
		if (dimension != null) {
			this.width = dimension.width;
			this.height = dimension.height;
		}
		if (point != null) {
			this.x = point.x;
			this.y = point.y;
		}
		shape = new Rectangle2D.Double(x, y, width, height);
	}

	public DrawableObject(Point point) {
		this(null, point);
	}

	public DrawableObject(Dimension dimension) {
		this(dimension, null);
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

	@Override
	public boolean isVisible() {
		return y() < game.sHeight() && x() < game.sWidth() && (y() + height()) > 0 && (x() + width()) > 0;
	}

	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, x, y);
		shape = transform.createTransformedShape(getShape());
		Color previousColor = null;
		if (color != null) {
			previousColor = g.getColor();
			g.setColor(color);
		}
		g.fill(shape);
		if (previousColor != null)
			g.setColor(previousColor);
	}

	/**
	 * @return the angle in radians (double ranging from -1 to 1);
	 * @author wonka
	 * @since 09/03/2014
	 */
	public double getAngleRadians() {
		return Math.toRadians(angle);
	}

	/**
	 * @param angle
	 *            the angle to set, in degrees.
	 */
	public void setAngle(double angle) {
		this.angle = angle;
		if (this.angle > 360 || this.angle < -360)
			this.angle = 0;
	}
}
