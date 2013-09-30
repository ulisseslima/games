package com.dvlcube.gaming.util;

import java.awt.Polygon;

/**
 * 
 * @author wonka
 * @since 29/09/2013
 */
public class PolygonFactory {

	public static Polygon hexagon(int x, int y, Integer radius, int angle) {
		int side = radius / 2 + 10;
		Polygon hex = new Polygon();
		for (int i = 0; i < 6; i++) {
			int r = angle + i * 2;
			hex.addPoint((int) (x + (side * Math.cos(r * Math.PI / 6))), (int) (y + (side * Math.sin(r * Math.PI / 6))));
		}
		return hex;
	}

	public static Polygon hexagon(int x, int y, int radius) {
		return hexagon(x, y, radius, 0);
	}
}
