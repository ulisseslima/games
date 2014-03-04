package com.dvlcube.game.flappy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.game.flappy.FlappinGame.PipeManagement;
import com.dvlcube.gaming.GamePanel;
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
			x -= pipeMan.speed;
		}

		if (x + width < 0) {
			garbage = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(x, y, width, height);
		if (game.debug) {
			g.setColor(Color.red);
			g.drawString(String.format("height: %d, y: %d", height, y), x, y + 10);
			g.setColor(GamePanel.FG_COLOR);
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
}
