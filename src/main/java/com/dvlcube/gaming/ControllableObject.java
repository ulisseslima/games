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
public abstract class ControllableObject extends DrawableObject implements
		Controllable {
	public boolean hasMouseOver = false;

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
}
