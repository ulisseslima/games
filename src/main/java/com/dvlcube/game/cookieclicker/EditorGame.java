package com.dvlcube.game.cookieclicker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import com.dvlcube.gaming.Game;

/**
 * @author wonka
 * @since 03/10/2013
 */
public class EditorGame extends Game {

	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private LinkedList<Rectangle> rects = new LinkedList<>();
	private boolean completedRect = false;
	private int[] coords = new int[] { 0, 0, 1, 1 };
	private int width = 1;
	private int height = 1;

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 03/10/2013
	 */
	public EditorGame(Dimension screen, double scale) {
		super(screen, scale);
	}

	@Override
	public void doLogic() {
		super.doLogic();

		width = coords[2] - coords[0];
		height = coords[3] - coords[1];

		if (completedRect && coords[1] != 0) {
			Rectangle rectangle = new Rectangle(new Point(coords[0], coords[1]), new Dimension(width, height));
			rects.add(rectangle);
			addObject(rectangle);
		}
	}

	@Override
	public void doGraphics(Graphics2D g) {
		super.doGraphics(g);
		g.drawRect(coords[0], coords[1], width, height);
		g.drawString(String.format("%d,%d %d,%d - %dx%d", coords[0], coords[1], coords[2], coords[3], width, height),
				10, 10);
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			completedRect = false;
			int sx = scale(e.getX()), sy = scale(e.getY());
			coords[0] = sx;
			coords[2] = sx + 1;
			coords[1] = sy;
			coords[3] = sy + 1;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			completedRect = true;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				completedRect = false;
				int sx = scale(e.getX()), sy = scale(e.getY());
				if (coords[0] < coords[2]) {
					coords[2] = sx;
				} else {
					coords[0] = sx;
				}

				if (coords[1] < coords[3])
					coords[3] = sy;
				else
					coords[1] = sy;
			}
		}
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			EditorGame.super.keyPressed(e);
		}
	}
}
