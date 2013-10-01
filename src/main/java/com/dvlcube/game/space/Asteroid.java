package com.dvlcube.game.space;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

import com.dvlcube.gaming.Coords;
import com.dvlcube.gaming.GamePolygon;

/**
 * @author wonka
 * @since 26/09/2013
 */
class Asteroid extends Polygon implements GamePolygon {

	private static final long serialVersionUID = 6842909318229368081L;
	public boolean debug = false;
	private Random random = new Random();

	/**
	 * Screen
	 */
	private final int SC_W = 1024 / 2;
	private final int SC_H = 768 / 2;

	private int x;
	private int y;
	private int w = 10;
	private int h = 10;

	public int angle;
	public int velocity = 1;
	public int weight;
	public int force;
	public boolean moving;

	private int xs[] = calcXs(x, w);
	private int ys[] = calcYs(y, h);
	private int n = xs.length;

	public final int[] calcXs(int x, int w) {
		return new int[] { x, x, x + (w / 2), x + w, x + w, x + (w / 2) };
	}

	public final int[] calcYs(int y, int h) {
		return new int[] { y + h, y, y - h, y, y + h, y + h * 2 };
	}

	public Asteroid(Coords coords) {
		x(coords.x);
		y(coords.y);
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
	}

	public Asteroid() {
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
	}

	public Asteroid(int w, int h) {
		super.xpoints = xs;
		super.ypoints = ys;
		super.npoints = n;
		this.w = w;
		this.h = h;
	}

	@Override
	public void x(int x) {
		this.x = x;
		xs = calcXs(x, w);
		super.xpoints = xs;
	}

	@Override
	public void y(int y) {
		this.y = y;
		if (this.y > SC_H) {
			this.y = 0;
		} else if (this.y < 0) {
			this.y = SC_H;
		}

		if (this.x > SC_W) {
			this.x = 0;
		} else if (this.x < 0) {
			this.x = SC_W;
		}

		ys = calcYs(y, h);
		super.ypoints = ys;
	}

	@Override
	public void w(int w) {
		this.w = w;
		xs = calcXs(x, w);
		super.xpoints = xs;
	}

	@Override
	public void h(int h) {
		this.h = h;
		ys = calcYs(y, h);
		super.ypoints = ys;
	}

	@Override
	public void down() {
		if (debug)
			System.out.printf("down (%d, %d)\n", x, y);
		y(y + 1);
	}

	@Override
	public void left() {
		if (debug)
			System.out.printf("left (%d, %d)\n", x, y);
		x(x - 1);
	}

	@Override
	public void right() {
		if (debug)
			System.out.printf("right (%d, %d)\n", x, y);
		x(x + 1);
	}

	@Override
	public void up() {
		if (debug)
			System.out.printf("up (%d, %d)\n", x, y);
		y(y - 1);
	}

	@Override
	public void go(int x, int y) {
	}

	@Override
	public void update() {
		angle += velocity;
		if (angle > 360)
			angle = 0;
		if (angle < 0)
			angle = 360;

		moving = true;

		int n = random.nextInt(5);
		switch (n) {
		case 0:
			up();
			break;
		case 1:
			left();
			velocity--;
			break;
		case 2:
			right();
			velocity--;
			break;
		default:
		case 3:
			down();
			velocity++;
			break;
		}

		if (velocity < -15)
			velocity = -15;
		else if (velocity > 15)
			velocity = 15;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getAngle() {
		return angle;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawString(String.format("%d m/s", velocity), x + 10, y + 10);
	}
}