package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.List;

/**
 * 
 * @author wonka
 * @since 21/09/2013
 */
public interface Game {

	/**
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	void doLogic();

	/**
	 * @param g
	 * @author wonka
	 * @since 21/09/2013
	 */
	void doGraphics(Graphics2D g);

	/**
	 * @return the mouse adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	MouseAdapter getMouseAdapter();

	/**
	 * @return the key adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	KeyAdapter getKeyAdapter();

	/**
	 * reset state.
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	void reset();

	Dimension getDimension();

	List<Controllable> getControllables();
}
