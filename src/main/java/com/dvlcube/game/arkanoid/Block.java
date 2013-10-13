package com.dvlcube.game.arkanoid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.GamePanel;

/**
 * @author wonka
 * @since 13/10/2013
 */
public class Block extends ControllableObject {

	private Random random = new Random();
	private Color color;

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	public Block(Dimension dimension, Point point) {
		super(dimension, point);
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		color = new Color(r, g, b);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRoundRect(x, y, width, height, 5, 5);
		g.setColor(GamePanel.FG_COLOR);
	}

	/**
	 * 
	 * @author wonka
	 * @since 13/10/2013
	 */
	public void disappear() {
		isGarbage = true;
	}
}
