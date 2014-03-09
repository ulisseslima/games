package com.dvlcube.gaming.physics;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class PhysicalProperties {
	public float weight = 1;
	public float aerodynamic;
	public float downforce;
	public float acceleration = 0;
	public float maxAcceleration = Float.MAX_VALUE;
	public float minAcceleration = -50;
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
