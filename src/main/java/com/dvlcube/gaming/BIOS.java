package com.dvlcube.gaming;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wonka
 * @since 28/09/2013
 */
public abstract class BIOS extends Game {
	public List<GameMenu> gameMenus = new ArrayList<>();
	public GamePanel panel;

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public BIOS(Dimension screen, double scale) {
		super(screen, scale);
	}

	public BIOS(GamePanel panel, Dimension screen, double scale) {
		super(screen, scale);
		this.panel = panel;
	}

	/**
	 * @param screen
	 * @author wonka
	 * @since 28/09/2013
	 */
	public BIOS(Dimension screen) {
		super(screen);
	}
}

class GameMenu extends MenuItem {
	public Class<? extends Game> gameClass;

	/**
	 * @param title
	 * @param gameClass
	 * @author wonka
	 * @since 28/09/2013
	 */
	public GameMenu(Dimension dimension, Point point, String title, Class<? extends Game> gameClass) {
		super(null, dimension, point, title);
		this.gameClass = gameClass;
	}

	public GameMenu(Dimension dimension, Point point, Class<? extends Game> gameClass) {
		super(null, dimension, point, gameClass.getSimpleName());
		this.gameClass = gameClass;
	}
}