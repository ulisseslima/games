package com.dvlcube.gaming.util;

import java.awt.Graphics2D;

import com.dvlcube.gaming.Coords;

/**
 * @author wonka
 * @since 21/09/2013
 */
public class Cuber {

	public static <T> Range<T> r(T start, T end) {
		return new Range<T>(start, end);
	}

	public static double mapd(Number n, Range<? extends Number> range,
			Range<? extends Number> newRange) {
		return NumberUtils.map(n.doubleValue(), range.getStart().doubleValue(),
				range.getEnd().doubleValue(),
				newRange.getStart().doubleValue(), newRange.getEnd()
						.doubleValue());
	}

	public static int map(Number n, Range<? extends Number> range,
			Range<? extends Number> newRange) {
		return (int) NumberUtils.map(n.doubleValue(), range.getStart()
				.doubleValue(), range.getEnd().doubleValue(), newRange
				.getStart().doubleValue(), newRange.getEnd().doubleValue());
	}

	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Coords xy(int x, int y) {
		return new Coords(x, y);
	}

	/**
	 * Draws a string.
	 * 
	 * @param x
	 * @param y
	 * @param g
	 * @param string
	 * @param args
	 * @author wonka
	 * @since 23/09/2013
	 */
	public static void draws(int x, int y, Graphics2D g, String string,
			Object... args) {
		g.drawString(String.format(string, args), x, y);
	}
}
