package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import com.dvlcube.game.beats.BeatsGame;
import com.dvlcube.game.biogame.BioGame;
import com.dvlcube.game.cookieclicker.CookieClickerGame;
import com.dvlcube.game.cookieclicker.EditorGame;
import com.dvlcube.game.pong.PongGame;
import com.dvlcube.game.sound.SoundGame;
import com.dvlcube.game.space.SpaceGame;

/**
 * @author wonka
 * @since 28/09/2013
 */
public class CubeBIOS extends BIOS {
	public static final float VERSION = 1.1f;

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
		Set<Class<? extends Game>> classes = new HashSet<>();
		classes.add(BeatsGame.class);
		classes.add(PongGame.class);
		classes.add(SoundGame.class);
		classes.add(SpaceGame.class);
		classes.add(CookieClickerGame.class);
		classes.add(BioGame.class);
		classes.add(EditorGame.class);

		Dimension d = MenuItem.DEFAULT_DIMENSION;
		int x = 10, y = 10, spacing = 5;
		for (Class<? extends Game> gameClass : classes) {
			gameMenus.add(new GameMenu(d, new Point(x, y), gameClass));
			y += d.height + spacing;
		}
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
