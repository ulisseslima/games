package com.dvlcube.gaming;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * @author wonka
 * @since 28/09/2013
 */
public class MenuItem extends ControllableObject {

	public static final Dimension DEFAULT_DIMENSION = new Dimension(100, 15);
	public String label = "<no label>";
	protected int paddingLeft = 2;
	protected int paddingTop = 12;
	public Color bgColor = new Color(60, 60, 60);
	public Color fgColor = new Color(8, 130, 230);

	public MenuItem(Action action, Dimension dimension, Point point, String label) {
		super(dimension, point);
		this.action = action;
		this.label = label;
	}

	/**
	 * @param dimension
	 * @param point
	 * @author wonka
	 * @since 28/09/2013
	 */
	public MenuItem(Dimension dimension, Point point) {
		super(dimension, point);
	}

	/**
	 * @param dimension
	 * @author wonka
	 * @since 28/09/2013
	 */
	public MenuItem(Dimension dimension) {
		super(dimension);
	}

	/**
	 * @param point
	 * @author wonka
	 * @since 28/09/2013
	 */
	public MenuItem(Point point) {
		super(point);
	}

	@Override
	public void draw(Graphics2D g) {
		if (hasMouseOver) {
			g.fillRect(x, y, width, height);
			g.setColor(bgColor);
		} else {
			g.setColor(fgColor);
			g.drawRect(x, y, width, height);
		}
		g.drawString(label, x + paddingLeft, y + paddingTop);
	}

	@Override
	public void update() {
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		if (activated(mx, my))
			doAction();
	}
}
