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
public abstract class Game {

	public boolean debug = false;
	public double scale = 1;
	public Dimension screen = null;

	public Game(Dimension screen) {
		this.screen = screen;
	}

	public Game(Dimension screen, double scale) {
		this(screen);
		this.scale = scale;
	}

	/**
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract void doLogic();

	/**
	 * @param g
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract void doGraphics(Graphics2D g);

	/**
	 * @return the mouse adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract MouseAdapter getMouseAdapter();

	/**
	 * @return the key adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract KeyAdapter getKeyAdapter();

	/**
	 * reset state.
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract void reset();

	/**
	 * @return controllable elements.
	 * @author wonka
	 * @since 28/09/2013
	 */
	public abstract List<Controllable> getControllables();

	/**
	 * @param value
	 * @return the value corrected to adjust to the game scale.
	 * @author wonka
	 * @since 28/09/2013
	 */
	public int scale(double value) {
		return (int) (value * scale);
	}
}
