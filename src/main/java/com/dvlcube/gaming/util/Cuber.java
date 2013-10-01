package com.dvlcube.gaming.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.Random;

import com.dvlcube.gaming.Coords;

/**
 * @author wonka
 * @since 21/09/2013
 */
public class Cuber {
	public static final DecimalFormat df = new DecimalFormat("#,##0.##");
	public static final Random random = new Random();

	public static <T> Range<T> r(T start, T end) {
		return new Range<T>(start, end);
	}

	public static double mapd(Number n, Range<? extends Number> range, Range<? extends Number> newRange) {
		return NumberUtils.map(n.doubleValue(), range.getStart().doubleValue(), range.getEnd().doubleValue(), newRange
				.getStart().doubleValue(), newRange.getEnd().doubleValue());
	}

	public static float mapf(Number n, Range<? extends Number> range, Range<? extends Number> newRange) {
		return (float) NumberUtils.map(n.doubleValue(), range.getStart().doubleValue(), range.getEnd().doubleValue(),
				newRange.getStart().doubleValue(), newRange.getEnd().doubleValue());
	}

	public static int map(Number n, Range<? extends Number> range, Range<? extends Number> newRange) {
		return (int) NumberUtils.map(n.doubleValue(), range.getStart().doubleValue(), range.getEnd().doubleValue(),
				newRange.getStart().doubleValue(), newRange.getEnd().doubleValue());
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

	public static String df(double number) {
		return df.format(number);
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
	public static void draws(int x, int y, Graphics2D g, String string, Object... args) {
		g.drawString(String.format(string, args), x, y);
	}

	/**
	 * @param name
	 *            name to convert.
	 * @return A "static" name (e.g. STATIC_NAME) to a text string (e.g. Static Name)
	 * @author wonka
	 * @since 29/09/2013
	 */
	public static String staticToString(String name) {
		StringBuilder builder = new StringBuilder();
		String[] narr = name.split("_");
		for (String string : narr) {
			char firstLetter = string.charAt(0);
			String rest = string.substring(1).toLowerCase();
			builder.append(" ").append(firstLetter + rest);
		}
		return builder.toString().replaceFirst(" ", "");
	}

	public static int strWidth(Graphics2D g, String string) {
		return g.getFontMetrics().stringWidth(string);
	}

	public static Dimension getRandomDimension(int maxw, int maxh) {
		int rw = random.nextInt(maxw);
		int rh = random.nextInt(maxh);
		return new Dimension(rw, rh);
	}

	public static Dimension getRandomSquare(int side) {
		int rh = random.nextInt(side);
		return new Dimension(rh, rh);
	}

	public static int getRandomRadius(int maxr) {
		return random.nextInt(maxr);
	}
}
