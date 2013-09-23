package com.dvlcube.gaming.util;

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

	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
