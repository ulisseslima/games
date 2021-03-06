package com.dvlcube.game.space;

import java.awt.Graphics2D;
import java.awt.Polygon;

import com.dvlcube.gaming.Coords;
import com.dvlcube.gaming.GamePolygon;
import com.dvlcube.gaming.Terminatable;
import com.dvlcube.gaming.sound.Synthesizer;

/**
 * @author wonka
 * @since 21/09/2013
 */
class SpaceCraft extends Polygon implements GamePolygon, Terminatable {

	private static final long serialVersionUID = 6842909318229368081L;
	private Synthesizer synth = new Synthesizer();
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

	@Override
	public void x(int x) {
		this.x = x;
		xs = calcXs(x, w);
		super.xpoints = xs;
	}

	@Override
	public void y(int y) {
		this.y = y;
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
		destination = new Coords(x, y);
		face(destination);
	}

	@Override
	public void update() {
		if (!destination.is(x, y)) {
			accelerate();
			face(destination);
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
			decelerate();
		}
	}

	/**
	 * 
	 * @author wonka
	 * @since 26/09/2013
	 */
	private void decelerate() {
		synth.osc.setFrequency(0);
		synth.osc.setModulationDepth(0);
	}

	/**
	 * 
	 * @author wonka
	 * @since 26/09/2013
	 */
	private void accelerate() {
		synth.osc.addFrequency(0.4);
		synth.osc.setModulationDepth(1);
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

	/**
	 * Face towards destination.
	 * 
	 * @param coords
	 *            destination.
	 * @author wonka
	 * @since 22/09/2013
	 */
	public void face(Coords coords) {
		int currentPosition = (x + y) / 2;
		int targetPosition = (coords.x + coords.y) / 2;

		angle = targetPosition - currentPosition;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawString(String.format("%d�", angle), x + 10, y + 10);
	}

	@Override
	public void terminate() {
		synth.terminate();
		synth = null;
	}
}