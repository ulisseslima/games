package com.dvlcube.gaming;

import java.awt.Graphics2D;

import com.dvlcube.gaming.util.Range;

/**
 * Adjusts values.
 * 
 * @author wonka
 * @since 28/09/2013
 */
public interface Knob {
	String getName();

	Number getValue();

	Number setValue(Number number);

	Number setValue(Number number, Range<? extends Number> range);

	Number increase();

	Number decrease();

	void draw(Graphics2D g, int x, int y);

	/**
	 * @return
	 * @author wonka
	 * @since 28/09/2013
	 */
	Range<? extends Number> getRange();
}