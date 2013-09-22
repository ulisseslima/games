package com.dvlcube.gaming.util;

import java.awt.Color;

public class ColorUtils {
	public static Color getRandom() {
		int r = randomInt(256), //
		g = randomInt(256), //
		b = randomInt(256);
		return new Color(r, g, b);
	}

	private static int randomInt(int n) {
		return (int) (Math.random() * n);
	}
}
