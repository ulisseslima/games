package com.dvlcube.game.arkanoid;

import static com.dvlcube.gaming.util.Cuber.map;
import static com.dvlcube.gaming.util.Range.$range;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.GamePanel;

/**
 * @author wonka
 * @since 13/10/2013
 */
public class Block extends ControllableObject {

	private Color color;
	private static Random random = new Random();
	// protected static int hstart = random.nextInt(300);
	protected static float hue = random.nextFloat();

	/**
	 * @author wonka
	 * @since 13/10/2013
	 */
	public Block(Dimension dimension, Point point) {
		super(dimension, point);
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
		garbage = true;
	}

	@Override
	public void setSource(Game game) {
		super.setSource(game);
		// float h = map(y, $range(hstart, game.sHeight()));
		float saturation = map(x, $range(0, game.sWidth()));
		// float s = 0.6f;
		float brightness = 0.6f;
		color = Color.getHSBColor(hue, saturation, brightness);
	}
}
