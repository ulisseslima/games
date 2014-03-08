package com.dvlcube.gaming;

import java.awt.Color;
import java.awt.Font;

/**
 * @author wonka
 * @since 28/09/2013
 */
public interface GamePanel {
	Color BG_COLOR = new Color(60, 60, 60);
	Color FG_COLOR = new Color(8, 130, 230);
	Font FONT = new Font("SansSerif", Font.BOLD, 10);

	void loadGame(Class<? extends Game> gameClass);

	void stopGame();

	void gameSelect();
}
