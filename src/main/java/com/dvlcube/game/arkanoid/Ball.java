package com.dvlcube.game.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.gaming.Collision;
import com.dvlcube.gaming.ControllableObject;

/**
 * 
 * @author wonka
 * @since 13/10/2013
 */
public class Ball extends ControllableObject {

	private int xDirection = 1;
	private int yDirection = 1;
	private int velocity = 1;
	public static int maxVelocity = 10;

	/**
	 * @param point
	 * @author wonka
	 * @since 13/10/2013
	 */
	public Ball(Point point) {
		super(new Dimension(10, 10), point);
	}

	@Override
	public void update() {
		x += velocity * xDirection;
		y += velocity * yDirection;
		collide();
		correctBounds();
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	/**
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void collide() {
		for (Object object : game.getObjects()) {
			if (isFromArkanoid(object)) {
				Collision collision = collided((ControllableObject) object);
				switch (collision) {
				case WEST:
					xDirection *= -1;
					destroy(object);
					game.collisionEvent(this, object);
					accelerate();
					break;
				case NORTH:
					yDirection *= -1;
					destroy(object);
					game.collisionEvent(this, object);
					accelerate();
					break;
				default:
					break;
				}
			}
		}
	}

	private boolean isFromArkanoid(Object object) {
		return object instanceof Block || object instanceof Platform || object instanceof Pit;
	}

	/**
	 * @param object
	 * @author wonka
	 * @since 13/10/2013
	 */
	private void destroy(Object object) {
		if (object instanceof Block)
			((Block) object).disappear();
	}

	private void accelerate() {
		velocity++;
		if (velocity > maxVelocity)
			velocity = maxVelocity;
	}

	/**
	 * @param x
	 * @param y
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void correctBounds() {
		if (x > game.sWidth()) {
			xDirection = -1;
		} else if (x < 0) {
			xDirection = 1;
		}

		if (y > game.sHeight()) {
			yDirection = -1;
		} else if (y < yDirection) {
			yDirection = 1;
		}
	}
}
