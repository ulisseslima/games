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
public abstract class DefaultController extends DefaultDrawable implements
		Controllable {

	public DefaultController() {
	}

	public DefaultController(Dimension dimension, Point point) {
		super(dimension, point);
	}

	public DefaultController(Point point) {
		super(point);
	}

	public DefaultController(Dimension dimension) {
		super(dimension);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e, int screenW, int screenH) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
	}

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {
	}
}
