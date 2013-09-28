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
public class Paddle extends Rectangle implements Controllable {
	private static final long serialVersionUID = -6768121714516610954L;
	public boolean debug = false;
	private Game game;
	public Integer score = 0;
	private int scW;
	private int scH;

	public Paddle(Point point, Dimension dimension) {
		super(point, dimension);
	}

	public Paddle(Point point) {
		super(point, new Dimension(10, 100));
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		g.draw(this);
		g.drawString(score.toString(), x, scH - 20);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e, int screenW, int screenH) {
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
		this.y = y;
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
		scW = game.screen.width;
		scH = game.screen.height;
	}
}
