package com.dvlcube.gaming;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Adapted from: Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
 * <p>
 * 
 * Pausing/Resuming/Quitting are controlled via the frame's window listener methods. (-not exactly)
 * 
 * Uses active rendering to update the JPanel.
 */
public class MainGameWindow extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	/**
	 * where the game is drawn
	 */
	private RunnableGamePanel gamePanel;
	private static int FPS = 60;
	private static volatile long fpsPeriod;

	public MainGameWindow(long period) {
		super("Some Game");
		makeGUI(period);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		pack();
		setResizable(false);
		setVisible(true);
	}

	private void makeGUI(long period) {
		Container container = getContentPane(); // default BorderLayout used

		gamePanel = new RunnableGamePanel(this, period);
		container.add(gamePanel, "Center");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		gamePanel.resumeGame();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		gamePanel.pauseGame();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		gamePanel.resumeGame();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		gamePanel.pauseGame();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		gamePanel.stopGame();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	public static void main(String args[]) {
		go(args);
	}

	private static void go(String[] args) {
		int fps = FPS;
		if (args.length != 0)
			fps = Integer.parseInt(args[0]);

		long period = (long) 1000.0 / fps;
		fpsPeriod = period;
		// TODO fix FPS reporting do adjust when FPS changes
		System.out.println("fps: " + fps + "; period: " + period + " ms");
		new MainGameWindow(period * 1000000L); // ms --> nano seconds.
	}

	public static void changeFps(int fps) {
		FPS += fps;
		if (FPS < 1)
			FPS = 1;
		if (FPS > 200)
			FPS = 200;
		long period = (long) 1000.0 / getFPS();
		MainGameWindow.fpsPeriod = period;
	}

	public static int getFPS() {
		return FPS;
	}

	public static long getFpsPeriod() {
		return fpsPeriod * 1000000L;
	}
}
