package com.dvlcube.gaming.util;

/**
 * @author wonka
 * @since 21/09/2013
 */
public class NumberUtils {
	public static double map(double n, Range<Double> originalRange,
			Range<Double> newRange) {
		double newNumber = (n - originalRange.getStart())
				/ (originalRange.getEnd() - originalRange.getStart())
				* (newRange.getEnd() - newRange.getStart())
				+ newRange.getStart();
		return newNumber;
	}
}
