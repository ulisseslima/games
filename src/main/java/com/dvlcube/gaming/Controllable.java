package com.dvlcube.gaming;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author wonka
 * @since 27/09/2013
 */
public interface Controllable extends GameElement {
	void keyPressed(KeyEvent e);

	void mouseDragged(MouseEvent e, int mx, int my);

	void mouseReleased(MouseEvent e, int mx, int my);

	void mouseWheelMoved(MouseWheelEvent e);

	void mousePressed(MouseEvent e, int mx, int my);

	void mouseMoved(MouseEvent e, int x, int y);

	boolean doAction();
}
