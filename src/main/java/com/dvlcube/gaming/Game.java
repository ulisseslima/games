package com.dvlcube.gaming;

import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.util.Range;

/**
 * 
 * @author wonka
 * @since 21/09/2013
 */
public abstract class Game implements Terminatable {

	public boolean debug = false;
	public boolean terminating = false;
	public double scale = 1;
	public final Dimension screen;
	public final Range<Integer> vRange;
	public final Range<Integer> hRange;
	protected List<Terminatable> terminatables = new ArrayList<>();

	public Game(Dimension screen) {
		this.screen = screen;
		vRange = r(0, screen.height);
		hRange = r(0, screen.width);
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

	public String getTitle() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void terminate() {
		System.out.println(this + " terminating");
		terminating = true;
		for (Terminatable terminatable : terminatables) {
			terminatable.terminate();
		}
	}

	/**
	 * @param ball2
	 * @author wonka
	 * @since 29/09/2013
	 */
	public void addTerminatables(Object... terminatable) {
		for (Object object : terminatable) {
			if (object instanceof Terminatable)
				terminatables.add((Terminatable) object);
			else
				throw new IllegalArgumentException(object
						+ " is not terminatable");
		}
	}
}
