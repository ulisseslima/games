package com.dvlcube.gaming;

/**
 * @author wonka
 * @since 28/09/2013
 */
public interface GamePanel {
	void loadGame(Class<? extends Game> gameClass);

	void stopGame();

	void gameSelect();
}
