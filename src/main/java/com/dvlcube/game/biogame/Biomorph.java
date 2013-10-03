package com.dvlcube.game.biogame;

import static com.dvlcube.gaming.util.Cuber.getRandomSquare;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.ControllableObject;

/**
 * @author wonka
 * @since 01/10/2013
 */
public class Biomorph extends ControllableObject {
	private List<Biomorph> biomorphs = new ArrayList<>();
	private boolean isDead;
	private float starvationBuffer = 0.01f;
	private float starvationRate = 0.01f;
	private float starvationCount = 0;

	/**
	 * @param point
	 * @author wonka
	 * @since 01/10/2013
	 */
	public Biomorph() {
		super(getRandomSquare(50));
		speed = rspeed();
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
		updateBiomorphs();
		checkSurroundings();
		starve();
	}

	/**
	 * @author wonka
	 * @since 02/10/2013
	 */
	private void starve() {
		starvationBuffer += starvationRate + ((width * 2) / 100);
		if (starvationBuffer >= 1) {
			starvationCount = (int) starvationBuffer;
			starvationBuffer %= starvationCount;

			width -= starvationCount;
			height -= starvationCount;
		}
	}

	/**
	 * @author wonka
	 * @since 01/10/2013
	 */
	private void checkSurroundings() {
		for (Biomorph biomorph : biomorphs) {
			if (this.intersects(biomorph)) {
				this.exchangeMass(biomorph);
			}
		}
	}

	private void updateBiomorphs() {
		List<Object> objects = game.getObjects();
		for (Object object : objects) {
			if (object instanceof Biomorph)
				biomorphs.add((Biomorph) object);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawRect(x, y, width, height);
	}

	public void exchangeMass(Biomorph b) {
		if (!b.isDead && this.isBiggerThan(b)) {
			absorb(b);
		}
	}

	private void absorb(Biomorph b) {
		width++;
		height++;
		b.width--;
		b.height--;
		b.checkLife();
	}

	private float rspeed() {
		float randomSpeed = (float) (Math.random() * 1);
		return randomSpeed;
	}

	public void checkLife() {
		if (this.width < 2) {
			this.width = 1;
			this.isDead = true;
			isGarbage = true;
		}
	}
}
