package com.dvlcube.game;

/**
 * coords.
 * 
 * @author wonka
 * @since 20/09/2013
 */
public class Coords {
	public int x;
	public int y;
	private boolean debug;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean is(int x, int y) {
		if (debug) {
			System.out.print(this.x + " == " + x + "? " + (this.x == x)
					+ " -- ");
			System.out.println(this.y + " == " + y + "? " + (this.y == y));
		}
		return this.x == x && this.y == y;
	}
}
