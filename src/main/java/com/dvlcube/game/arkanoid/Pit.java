package com.dvlcube.game.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.gaming.ControllableObject;

/**
 * 
 * @author wonka
 * @since 13/10/2013
 */
public class Pit extends ControllableObject {

	public Pit(Dimension dimension, Point point) {
		super(dimension, point);
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(x, y, width, height);
	}
}
