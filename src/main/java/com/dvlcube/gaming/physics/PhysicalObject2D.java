package com.dvlcube.gaming.physics;

import java.awt.Dimension;
import java.awt.Point;

import com.dvlcube.game.flappy.Ground;
import com.dvlcube.game.flappy.Pipe;
import com.dvlcube.gaming.Collision;
import com.dvlcube.gaming.ControllableObject;

/**
 * @author wonka
 * @since 27/09/2013
 */
public abstract class PhysicalObject2D extends ControllableObject implements Physical {

	public PhysicalProperties physics = new PhysicalProperties();

	public PhysicalObject2D(Dimension dimension, Point point) {
		super(dimension, point);
	}

	@Override
	public PhysicalProperties getPhysicalProperties() {
		return physics;
	}

	@Override
	public void update() {
		for (Object object : game.getObjects()) {
			if (this == object)
				continue;

			if (!physics.fixed) {
				if (canCollideWith(object)) {
					Collision collision = collided((ControllableObject) object);
					if (!(collision == Collision.NO_COLLISION)) {
						game.collisionEvent(this, object);
						if (object instanceof PhysicalObject2D) {
							if (((PhysicalObject2D) object).breaksFall()) {
								stopFalling();
							} else {
								keepFalling();
							}
						} else {
							keepFalling();
						}
					}
				}
			} else if (this.isGround() && object instanceof PhysicalObject2D) {
				PhysicalObject2D object2d = (PhysicalObject2D) object;
				this.pull(object2d);
			}
		}
	}

	/**
	 * @param object2d
	 * @author wonka
	 * @since 02/03/2014
	 */
	private void pull(PhysicalObject2D object) {
		if (object.isAirborne()) {
			object.physics.acceleration -= physics.gravitationalPull;
			object.ybuffer += object.physics.acceleration;
			object.mvcount = (int) object.ybuffer;

			// Debug.println("pulling %s (%f - %f)", object.toString(), object.physics.acceleration,
			// object.ybuffer);

			if (object.mvcount >= 1 || object.mvcount <= -1) {
				object.y -= object.mvcount;
				object.ybuffer %= object.mvcount;
			}
		}
	}

	/**
	 * @author wonka
	 * @since 02/03/2014
	 */
	protected void stopFalling() {
		this.physics.falling = false;
		this.physics.acceleration = 0;
	}

	/**
	 * @author wonka
	 * @since 02/03/2014
	 */
	protected void keepFalling() {
		this.physics.falling = true;
	}

	/**
	 * @param object
	 * @return true if this object can collide with the other object.
	 * @author wonka
	 * @since 01/03/2014
	 */
	public boolean canCollideWith(Object object) {
		return object instanceof Pipe || object instanceof Ground;
	}

	/**
	 * @return whether this object can keep another object from falling.
	 * @author wonka
	 * @since 02/03/2014
	 */
	public boolean breaksFall() {
		return physics.ground || physics.fixed;
	}

	/**
	 * @return whether this object is the lowest place in the world.
	 * @author wonka
	 * @since 02/03/2014
	 */
	public boolean isGround() {
		return physics.ground;
	}

	/**
	 * @return whether this object is airborne.
	 * @author wonka
	 * @since 02/03/2014
	 */
	public boolean isAirborne() {
		return !physics.fixed && (physics.jumping || physics.falling);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("#").append(Thread.currentThread().getName());
		builder.append("\n");
		builder.append("direction: " + direction);
		return builder.toString();
	}
}
