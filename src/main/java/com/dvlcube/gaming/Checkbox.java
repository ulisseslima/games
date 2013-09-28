package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wonka
 * @since 27/09/2013
 */
public class Checkbox extends DefaultController {

	public static final int DEFAULT_WIDTH = 10;
	public static final int DEFAULT_HEIGHT = 10;
	public boolean checked;

	public Checkbox() {
		super(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	public Checkbox(int x, int y) {
		super(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), new Point(x, y));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics2D g) {
		if (checked) {
			g.fillRect(x, y, width, height);
		} else {
			g.drawRect(x, y, width, height);
		}
	}

	public boolean toggle() {
		return checked = checked ? false : true;
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		if (isInside(mx, my)) {
			toggle();
		}
	}

	/**
	 * @param n
	 *            number of checkboxes
	 * @param xOffset
	 *            x alignment
	 * @param yOrder
	 *            y alignment
	 * @return a list of checkboxes
	 * @author wonka
	 * @since 28/09/2013
	 */
	public static List<Checkbox> list(int n, int xOffset, int yOrder) {
		List<Checkbox> checkboxes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			checkboxes.add(new Checkbox(xOffset, (DEFAULT_HEIGHT * yOrder)));
			xOffset += DEFAULT_WIDTH;
		}
		return checkboxes;
	}

	/**
	 * @param e
	 * @param mx
	 * @param my
	 * @return whether this element was toggled as a result of a click.
	 * @author wonka
	 * @since 28/09/2013
	 */
	public boolean toggled(MouseEvent e, int mx, int my) {
		if (isInside(mx, my)) {
			toggle();
			return true;
		}
		return false;
	}
}
