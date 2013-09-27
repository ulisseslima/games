package com.dvlcube.gaming.ponggame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 26/09/2013
 */
public class Ball extends Rectangle implements Controllable {

	private static final long serialVersionUID = 3929278993084690115L;
	public boolean debug = false;
	private Game game;
	private int scW;
	private int scH;
	private int xDirection = 1;
	private int yDirection = 1;

	public Ball(Point point, Dimension dimension) {
		super(point, dimension);
	}

	public Ball(Point point) {
		super(point, new Dimension(10, 10));
	}

	@Override
	public void update() {
		x += xDirection;
		y += yDirection;
		collide();
		correctBounds();
	}

	/**
	 * 
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void collide() {
		for (Controllable element : game.getControllables()) {
			if (!this.equals(element)) {
				if (this.intersects((Rectangle) element)) {
					xDirection *= -1;
				}
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void correctBounds() {
		if (x > scW) {
			xDirection = -1;
		} else if (x < 0) {
			xDirection = 1;
		}

		if (y > scH) {
			yDirection = -1;
		} else if (y < yDirection) {
			yDirection = 1;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.draw(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e, int screenW, int screenH) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {

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
		scW = game.getDimension().width;
		scH = game.getDimension().height;
	}
}
