package com.dvlcube.gaming;

import java.awt.Graphics2D;

/**
 * @author wonka
 * @since 27/09/2013
 */
public interface Drawable {
	void draw(Graphics2D g);

	int width();

	int height();

	int x();

	int y();

	void setSource(Game game);
}
