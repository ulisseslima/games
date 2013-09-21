package com.dvlcube.spacegame;

import java.awt.Polygon;

import com.dvlcube.game.Coords;
import com.dvlcube.game.GamePolygon;

/**
 * @author wonka
 * @since 21/09/2013
 */
class SpaceCraft extends Polygon implements GamePolygon {

	private static final long serialVersionUID = 6842909318229368081L;
	public boolean debug = false;

	private int x;
	private int y;
	private int w = 10;
	private int h = 10;

	public int angle;
	public int velocity;
	public int weight;
	public int force;
	public boolean moving;
	public Coords destination = new Coords(x, y);

	private int xs[] = calcXs(x, w);
	private int ys[] = calcYs(y, h);
	private int n = xs.length;

	public final int[] calcXs(int x, int w) {
		return new int[] { x, x + (w / 2), x + w };
	}

	public final int[] calcYs(int y, int h) {
		return new int[] { y + h * 2, y, y + h * 2 };
	}

	public SpaceCraft(Coords coords) {
		x(coords.x);
		y(coords.y);
		destination = new Coords(x, y);
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
	}

	public SpaceCraft() {
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
	}

	public SpaceCraft(int w, int h) {
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
		this.w = w;
		this.h = h;
	}

	public void x(int x) {
		this.x = x;
		xs = calcXs(x, w);
		super.xpoints = xs;
	}

	public void y(int y) {
		this.y = y;
		ys = calcYs(y, h);
		super.ypoints = ys;
	}

	public void w(int w) {
		this.w = w;
		xs = calcXs(x, w);
		super.xpoints = xs;
	}

	public void h(int h) {
		this.h = h;
		ys = calcYs(y, h);
		super.ypoints = ys;
	}

	public void down() {
		y(y + 1);
		if (angle < 180) {
			angle++;
		} else if (angle > 180)
			angle--;
	}

	public void left() {
		x(x - 1);
		if (angle < -90) {
			angle++;
		} else if (angle > -90)
			angle--;
	}

	public void right() {
		x(x + 1);
		if (angle < 90) {
			angle++;
		} else if (angle > 90)
			angle--;
	}

	public void up() {
		y(y - 1);
		if (angle < 0) {
			angle++;
		} else if (angle > 0)
			angle--;
	}

	public void go(int x, int y) {
		destination = new Coords(x, y);
	}

	public void update() {
		if (!destination.is(x, y)) {
			moving = true;

			if (this.x < destination.x) {
				right();
			} else if (this.x > destination.x) {
				left();
			}

			if (this.y < destination.y) {
				down();
			} else if (this.y > destination.y) {
				up();
			}
		} else {
			moving = false;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAngle() {
		return angle;
	}
}