package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class ControllableObject extends DrawableObject implements Controllable {

	public boolean hasMouseOver = false;
	public boolean moving = false;
	public boolean garbage = false;
	public float speed = 1;
	public float xybuffer = 0;
	public float xbuffer = 0;
	public float ybuffer = 0;
	public int mvcount = 0;
	protected Action action;
	protected Point destination;
	public Direction direction = Direction.NOT_MOVING;

	public ControllableObject() {
		super(new Dimension(100, 10), new Point(10, 10));
	}

	public ControllableObject(Dimension dimension, Point point) {
		super(dimension, point);
	}

	public ControllableObject(Point point) {
		super(point);
	}

	public ControllableObject(Dimension dimension) {
		super(dimension);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e, int mx, int my) {
	}

	@Override
	public void mouseReleased(MouseEvent e, int mx, int my) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
	}

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {
		hasMouseOver = isInside(x, y);
	}

	/**
	 * @param mx
	 *            mouse x
	 * @param my
	 *            mouse y
	 * @return true if the coordinates are within this drawable bounds.
	 * @author wonka
	 * @since 28/09/2013
	 */
	public boolean activated(int mx, int my) {
		return hasMouseOver = isInside(mx, my);
	}

	@Override
	public boolean doAction() {
		if (action != null)
			return action.doAction();
		return false;
	}

	@Override
	public void update() {
		if (angle > 360 || angle < -360)
			angle = 0;

		moveToDestination();
	}

	/**
	 * @return whether this object's coordinates have changed as a result of a move operation.
	 * @author wonka
	 * @since 01/10/2013
	 */
	public boolean moveToDestination() {
		xybuffer += speed;
		if (xybuffer >= 1) {
			mvcount = (int) xybuffer;
			xybuffer %= mvcount;

			if (destination != null) {
				if (destination.distance(x, y) != 0) {
					moving = true;

					if (this.x < destination.x) {
						x += mvcount;
					} else if (this.x > destination.x) {
						x -= mvcount;
					}

					if (this.y < destination.y) {
						y += mvcount;
					} else if (this.y > destination.y) {
						y -= mvcount;
					}
				} else {
					moving = false;
				}
			}
		}
		return false;
	}

	/**
	 * sets this object's destination.
	 * <p>
	 * A controllable object is always trying to reach its destination.
	 * 
	 * @param x
	 * @param y
	 * @author wonka
	 * @since 01/10/2013
	 */
	public void move(int x, int y) {
		destination = new Point(x, y);
	}

	public boolean isBiggerThan(ControllableObject o) {
		int thisArea = this.width * this.height;
		int oArea = o.width * o.height;
		return thisArea > oArea;
	}

	/**
	 * @param o
	 * @return type of collision.
	 * @author wonka
	 * @since 08/03/2014
	 */
	public Collision collided(ControllableObject o) {
		if (getShape() != null) {
			AffineTransform transform = new AffineTransform();
			transform.rotate(getAngleRadians(), x, y);

			Area area = new Area(getShape());
			Area transformedArea = area.createTransformedArea(transform);

			if (o.getShape() == null || o.angle == 0) {
				if (transformedArea.intersects(o.x, o.y, o.width, o.height)) {
					return Collision.ALL;
				}
			} else {
				AffineTransform otransform = new AffineTransform();
				otransform.rotate(getAngleRadians(), x, y);

				Area oarea = new Area(o.getShape());
				Area otransformedArea = oarea.createTransformedArea(otransform);
				if (transformedArea.intersects(otransformedArea.getBounds())) {
					return Collision.ALL;
				}
			}
			return Collision.NO_COLLISION;
		} else {
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
	}
}
