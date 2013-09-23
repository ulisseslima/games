package com.dvlcube.gaming.spacegame;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.Coords;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.GamePolygon;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SpaceGame implements Game {
	public boolean debug = false;
	private int x;
	private int y = 100;
	private List<Coords> mouseObs = new ArrayList<Coords>();
	private List<GamePolygon> polys = new ArrayList<GamePolygon>();
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private final SpaceCraft spaceCraft = new SpaceCraft(new Coords(150, 150));
	{
		polys.add(spaceCraft);
	}

	public void doLogic() {
		x++;
		if (x > 360)
			x = 0;

		if (mouseObs.size() > 10)
			mouseObs.remove(0);

		for (GamePolygon polygon : polys) {
			polygon.update();
		}
	}

	public void doGraphics(Graphics2D g) {
		synchronized (this) {
			for (Coords coords : mouseObs) {
				g.drawRect(coords.x, coords.y, 2, 2);
				g.drawString(String.format("%d,%d", coords.x, coords.y),
						coords.x + 10, coords.y - 10);
			}
		}

		g.drawRect(x, y, 10, 10);

		for (GamePolygon polygon : polys) {
			int polyX = polygon.getX();
			int polyY = polygon.getY();
			int polyAngle = polygon.getAngle();
			double fixedAngle = Math.toRadians(polyAngle);
			if (debug)
				System.out.printf("filling %d,%d at %fº\n", polyX, polyY,
						fixedAngle);
			g.rotate(fixedAngle, polyX, polyY);
			g.fillPolygon((Polygon) polygon);
			g.rotate(-fixedAngle, polyX, polyY);
			g.drawString(String.format("%dº", polyAngle), polyX + 10,
					polyY + 10);
		}
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX() / 2, y = e.getY() / 2;
			mouseObs.add(new Coords(x, y));
			spaceCraft.go(x, y);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			if (keyCode == KeyEvent.VK_UP) {
				spaceCraft.up();
			}

			if (keyCode == KeyEvent.VK_DOWN) {
				spaceCraft.down();
			}

			if (keyCode == KeyEvent.VK_LEFT) {
				spaceCraft.left();
			}

			if (keyCode == KeyEvent.VK_RIGHT) {
				spaceCraft.right();
			}
		}
	}

	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	public void reset() {
	}
}