package com.dvlcube.game.cookieclicker;

import static com.dvlcube.gaming.util.Cuber.df;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.dvlcube.gaming.Action;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.MenuItem;
import com.dvlcube.gaming.RunnableGamePanel;

/**
 * Rip-off of the famous video game: http://orteil.dashnet.org/cookieclicker/
 * 
 * @author wonka
 * @since 29/09/2013
 */
public class CookieClickerGame extends Game {
	public static final float VERSION = 1.0f;

	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();

	private long displayedCookies = 0;
	private long cookies = 0;
	private long lastSecond = -1;
	private final Cookie cookie;

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 29/09/2013
	 */
	public CookieClickerGame(Dimension screen, double scale) {
		super(screen, scale);
		cookie = new Cookie(new Action() {
			@Override
			public boolean doAction() {
				cookies++;
				return true;
			}
		});
		addObject(cookie);

		Dimension d = new Dimension(110, 15);
		int x = scale(screen.width) - (d.width + 10), y = 10, spacing = 5;
		for (final CookieProducer producer : CookieProducer.values()) {
			final Action action = new Action() {
				@Override
				public boolean doAction() {
					if (cookies > producer.upgradeCost()) {
						cookies -= producer.add();
						return true;
					}
					return false;
				}
			};
			addObject(new MenuItem(action, d, new Point(x, y), producer.name));
			y += d.height + spacing;
		}
	}

	@Override
	public void doLogic() {
		if (RunnableGamePanel.GAME_SECONDS != lastSecond) {
			lastSecond = RunnableGamePanel.GAME_SECONDS;
			cookies += CookieProducer.doProduce();
			super.doLogic();
		}
		if (displayedCookies < cookies) {
			long diff = cookies - displayedCookies;
			long increase = (diff * 10) / 100;
			if (increase < 1)
				increase = 1;
			displayedCookies += increase;
		}

		if (displayedCookies > cookies)
			displayedCookies = cookies;
	}

	@Override
	public void doGraphics(Graphics2D g) {
		super.doGraphics(g);
		g.drawString(df(displayedCookies), cookie.x, cookie.y - (cookie.height));
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
		public void mouseMoved(MouseEvent e) {
			CookieClickerGame.super.mouseMoved(e, e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			CookieClickerGame.super.mousePressed(e, e.getX(), e.getY());
		}
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			CookieClickerGame.super.keyPressed(e);
		}
	}

	@Override
	public void reset() {
	}
}
