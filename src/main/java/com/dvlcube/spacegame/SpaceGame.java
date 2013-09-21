package com.dvlcube.spacegame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.game.Coords;
import com.dvlcube.game.Game;
import com.dvlcube.game.GamePolygon;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SpaceGame implements Game {
	private int x;
	private int y = 100;
	private List<Coords> mouseObs = new ArrayList<Coords>();
	private List<GamePolygon> polys = new ArrayList<GamePolygon>();
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private SpaceCraft spaceCraft = new SpaceCraft(new Coords(300, 300));
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
		for (Coords coords : mouseObs) {
			g.drawRect(coords.x, coords.y, 1, 1);
		}

		g.drawRect(x, y, 10, 10);

		g.setColor(Color.red);
		for (GamePolygon polygon : polys) {
			int polyX = polygon.getX();
			int polyY = polygon.getY();
			int polyAngle = polygon.getAngle();
			g.rotate(polyAngle, polyX, polyY);
			g.fillPolygon((Polygon) polygon);
			g.rotate(-polyAngle, polyX, polyY);
			g.drawString(String.format("%dº", polyAngle), polyX + 10,
					polyY + 10);
		}
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX(), y = e.getY();
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
}