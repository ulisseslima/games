package com.dvlcube.game.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.ControllableObject;

/**
 * @author wonka
 * @since 13/10/2013
 */
public class Platform extends ControllableObject {

	public Platform(Point point) {
		super(new Dimension(100, 10), point);
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {
		this.x = x;
	}
}
