package com.dvlcube.game.biogame;

import static com.dvlcube.gaming.util.Cuber.getRandomSquare;

import java.awt.Graphics2D;

import com.dvlcube.gaming.ControllableObject;

/**
 * @author wonka
 * @since 01/10/2013
 */
public class Biomorph extends ControllableObject {

	/**
	 * @param point
	 * @author wonka
	 * @since 01/10/2013
	 */
	public Biomorph() {
		super(getRandomSquare(50));
		speed = 0.1f;
	}

	@Override
	public void update() {
		if (destination == null) {
			destination = game.getRandomPoint();
			x = destination.x;
			y = destination.y;
		}
		if (destination == null || !moving) {
			destination = game.getRandomPoint();
		}
		super.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawOval(x, y, width, height);
	}

	public void absorb(Biomorph b) {
	}
}
