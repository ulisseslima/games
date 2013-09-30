package com.dvlcube.gaming.util;

import java.awt.Polygon;

/**
 * 
 * @author wonka
 * @since 29/09/2013
 */
public class PolygonFactory {

	public static Polygon hexagon(int x, int y, int angle) {
		Polygon hex = new Polygon();
		for (int i = 0; i < 6; i++) {
			int r = angle + i * 2;
			hex.addPoint((int) (x + (50 * Math.cos(r * Math.PI / 6))),
					(int) (y + (50 * Math.sin(r * Math.PI / 6))));
		}
		return hex;
	}

	public static Polygon hexagon(int x, int y) {
		return hexagon(x, y, 0);
	}
}
