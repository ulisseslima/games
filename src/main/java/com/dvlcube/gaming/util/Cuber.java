package com.dvlcube.gaming.util;

import java.awt.Graphics2D;

/**
 * @author wonka
 * @since 21/09/2013
 */
public class Cuber {

	public static <T> Range<T> r(T start, T end) {
		return new Range<T>(start, end);
	}

	public static double map(double n, Range<Double> range,
			Range<Double> newRange) {
		return NumberUtils.map(n, range, newRange);
	}

	public static int map(int n, Range<Integer> range, Range<Integer> newRange) {
		Range<Double> r = new Range<>((double) range.getStart(),
				(double) range.getEnd());
		Range<Double> nr = new Range<>((double) newRange.getStart(),
				(double) newRange.getEnd());
		return (int) NumberUtils.map(n, r, nr);
	}

	public static double mapi(int n, Range<Integer> range,
			Range<Double> newRange) {
		Range<Double> r = new Range<>((double) range.getStart(),
				(double) range.getEnd());
		return NumberUtils.map(n, r, newRange);
	}

	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
