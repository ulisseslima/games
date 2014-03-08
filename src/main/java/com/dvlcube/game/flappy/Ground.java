package com.dvlcube.game.flappy;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.gaming.physics.PhysicalObject2D;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class Ground extends PhysicalObject2D {
	{
		physics.ground = true;
		physics.fixed = true;
		physics.weight = 10000;
		physics.gravitationalPull = 0.2f;
	}

	/**
	 * @param dimension
	 * @param point
	 * @author wonka
	 * @since 01/03/2014
	 */
	public Ground(Dimension dimension, Point point) {
		super(dimension, point);
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(x, y, width, height);
	}
}
