package com.dvlcube.gaming.physics;

import com.dvlcube.gaming.F;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class PhysicalProperties {
	public float weight = 1;
	public float aerodynamic;
	public float downforce;
	public float acceleration = 0;
	public F maxAcceleration = new F() {

		@Override
		public Float x() {
			return Float.MAX_VALUE;
		}
	};
	public F minAcceleration = new F() {

		@Override
		public Float x() {
			return -50f;
		}
	};
	public float speed = 0;
	public float maxSpeed;
	public float gravitationalRadius;
	public float gravitationalPull;
	public boolean fixed;
	public boolean ground;
	public boolean onGround;
	public boolean jumping;
	public boolean falling = true;
	public float escapeVelocity = Float.MAX_VALUE;
}
