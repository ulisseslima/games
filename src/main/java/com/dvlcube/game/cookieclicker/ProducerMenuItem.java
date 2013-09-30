package com.dvlcube.game.cookieclicker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dvlcube.gaming.Action;
import com.dvlcube.gaming.MenuItem;

/**
 * @author wonka
 * @since 30/09/2013
 */
public class ProducerMenuItem extends MenuItem {

	private final CookieProducer producer;

	/**
	 * @param action
	 * @param dimension
	 * @param point
	 * @param label
	 * @author wonka
	 * @since 30/09/2013
	 */
	public ProducerMenuItem(Action action, Dimension dimension, Point point, CookieProducer producer) {
		super(action, dimension, point, producer.name);
		this.producer = producer;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawString((int) (producer.getMultiplier()) + "", (x + width) - 10, y + paddingTop);
	}
}
