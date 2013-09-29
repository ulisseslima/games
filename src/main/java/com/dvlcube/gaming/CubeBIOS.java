package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import com.dvlcube.game.beats.BeatsGame;
import com.dvlcube.game.pong.PongGame;
import com.dvlcube.game.sound.SoundGame;
import com.dvlcube.game.space.SpaceGame;

/**
 * @author wonka
 * @since 28/09/2013
 */
public class CubeBIOS extends BIOS {

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public CubeBIOS(GamePanel panel, Dimension screen, double scale) {
		super(panel, screen, scale);
	}

	/**
	 * @param screen
	 * @author wonka
	 * @since 28/09/2013
	 */
	public CubeBIOS(Dimension screen) {
		super(screen);
	}

	{
		Dimension d = MenuItem.DEFAULT_DIMENSION;
		int x = 10, y = 10, spacing = 5;
		gameMenus.add(new GameMenu(d, new Point(x, y), BeatsGame.class));
		y += d.height + spacing;
		gameMenus.add(new GameMenu(d, new Point(x, y), PongGame.class));
		y += d.height + spacing;
		gameMenus.add(new GameMenu(d, new Point(x, y), SoundGame.class));
		y += d.height + spacing;
		gameMenus.add(new GameMenu(d, new Point(x, y), SpaceGame.class));
	}

	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private boolean sigTermSent = false;

	@Override
	public void doLogic() {
	}

	@Override
	public void doGraphics(Graphics2D g) {
		for (GameMenu menu : gameMenus) {
			menu.draw(g);
		}
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	@Override
	public void reset() {
	}

	@Override
	public List<Controllable> getControllables() {
		return null;
	}

	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int mx = scale(e.getX()), my = scale(e.getY());
			for (GameMenu menu : gameMenus) {
				if (menu.activated(mx, my)) {
					panel.loadGame(menu.gameClass);
					sigTermSent = false;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			for (GameMenu menu : gameMenus) {
				menu.mouseMoved(e, scale(e.getX()), scale(e.getY()));
			}
		}
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_END:
				if (!sigTermSent)
					panel.gameSelect();
				break;
			}
		}
	}

	@Override
	public void terminate() {
	}
}
