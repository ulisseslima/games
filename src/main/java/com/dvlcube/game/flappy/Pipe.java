package com.dvlcube.game.flappy;

import static com.dvlcube.gaming.util.Cuber.$;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.game.flappy.FlappinGame.PipeManagement;
import com.dvlcube.gaming.physics.PhysicalObject2D;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class Pipe extends PhysicalObject2D {
	{
		physics.fixed = true;
	}

	/**
	 * @param dimension
	 * @param point
	 * @author wonka
	 * @since 01/03/2014
	 */
	public Pipe(Dimension dimension, Point point) {
		super(dimension, point);
	}

	@Override
	public void update() {
		super.update();

		if (pipeMan != null && !game.ended) {
			xbuffer += pipeMan.speed;
			if (xbuffer > 1) {
				int mvcount = (int) xbuffer;
				x -= mvcount;
				xbuffer %= mvcount;
			}
		}

		if (x + width < 0) {
			garbage = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(x, y, width, height);
		if (game.debug) {
			$(String.format("h %d", height)).write(x, y + 10);
			$(String.format("y %d", y)).write(x, y + 20);
		}
	}

	private PipeManagement pipeMan;

	/**
	 * @param pipeMan
	 *            the pipeMan to set
	 */
	public void setPipeMan(PipeManagement pipeMan) {
		this.pipeMan = pipeMan;
	}

	/**
	 * @return the point where the player should gain a point.
	 * @author wonka
	 * @since 08/03/2014
	 */
	public int getScoreMark() {
		return (x + (width / 2));
	}
}
