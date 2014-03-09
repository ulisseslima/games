package com.dvlcube.gaming;

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * @author wonka
 * @since 27/09/2013
 */
public interface Drawable {
	/**
	 * actual screen drawing code.
	 * 
	 * @param g
	 * @author wonka
	 * @since 09/03/2014
	 */
	void draw(Graphics2D g);

	int width();

	int height();

	int x();

	int y();

	void setSource(Game game);

	/**
	 * @return true if the object is visible on the screen.
	 * @author wonka
	 * @since 02/03/2014
	 */
	boolean isVisible();

	/**
	 * @return this object's shape, can be null.
	 * @author wonka
	 * @since 09/03/2014
	 */
	Shape getShape();
}
