package com.dvlcube.gaming.util;

/**
 * @author wonka
 * @since 21/09/2013
 */
public class NumberUtils {
	/**
	 * @param n
	 * @param originalRange
	 * @param newRange
	 * @return mapped value.
	 * @author wonka
	 * @since 25/09/2013
	 */
	public static double map(double n, Range<Double> originalRange,
			Range<Double> newRange) {
		return map(n, originalRange.getStart(), originalRange.getEnd(),
				newRange.getStart(), newRange.getEnd());
	}

	/**
	 * @param n
	 * @param n1
	 * @param n2
	 * @param nn1
	 * @param nn2
	 * @return mapped value.
	 * @author wonka
	 * @since 25/09/2013
	 */
	public static double map(double n, double n1, double n2, double nn1,
			double nn2) {
		double newNumber = (n - n1) / (n2 - n1) * (nn2 - nn1) + nn1;
		return newNumber;
	}
}
