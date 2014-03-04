package com.dvlcube.gaming;

import java.util.Random;

/**
 * @author wonka
 * @since 02/03/2014
 */
public class Debug {
	private static final boolean ON = true;
	private static final Random random = new Random();

	public static void println(Object object, Object... objects) {
		if (ON) {
			if (random.nextInt(100) > 95)
				System.out.printf(object + "\n", objects);
		}
	}
}
