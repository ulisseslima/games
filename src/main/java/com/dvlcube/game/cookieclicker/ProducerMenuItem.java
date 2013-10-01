package com.dvlcube.game.cookieclicker;

import static com.dvlcube.gaming.util.Cuber.df;
import static com.dvlcube.gaming.util.Cuber.strWidth;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.Action;
import com.dvlcube.gaming.GamePanel;
import com.dvlcube.gaming.MenuItem;

/**
 * @author wonka
 * @since 30/09/2013
 */
public class ProducerMenuItem extends MenuItem {

	private final CookieProducer producer;
	private List<String> infoStrings = new ArrayList<>();

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
	public void update() {
		super.update();
		infoStrings.clear();
		infoStrings.add("Cookies: " + producer.getCookies() * producer.getMultiplier());
		infoStrings.add("Rate: " + df(producer.getProductionRate()));
		infoStrings.add("Cost: " + df(producer.getPrice()));
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		String multi = (producer.getMultiplier()) + "";
		int stringWidth = strWidth(g, multi) / 2;
		g.drawString(multi, (x + width - stringWidth) - 10, y + paddingTop);

		if (hasMouseOver) {
			int spacing = 10;
			int infoX = x, infoY = game.getScaledScreen().height - spacing;

			for (String string : infoStrings) {
				g.setColor(GamePanel.FG_COLOR);
				g.drawString(string, infoX, infoY);
				infoY -= spacing;
			}
		}
	}
}
