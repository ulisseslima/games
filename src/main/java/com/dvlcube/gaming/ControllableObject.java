package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class ControllableObject extends DrawableObject implements Controllable {

	public boolean hasMouseOver = false;
	public boolean moving = false;
	public float speed = 1;
	public float xybuffer = 0;
	public int mvcount = 0;
	protected Action action;
	protected Point destination;

	public ControllableObject() {
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
		move();
	}

	/**
	 * @return whether this object's coordinates have changed as a result of a move operation.
	 * @author wonka
	 * @since 01/10/2013
	 */
	public boolean move() {
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
}
