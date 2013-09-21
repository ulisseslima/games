package com.dvlcube.gaming;

/**
 * @author wonka
 * @since 21/09/2013
 */
public interface GamePolygon {
	void x(int x);

	void y(int y);

	void w(int w);

	void h(int h);

	void up();

	void down();

	void left();

	void right();

	void go(int x, int y);

	void update();

	int getX();

	int getY();

	int getAngle();
}
