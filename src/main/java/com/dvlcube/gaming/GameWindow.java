package com.dvlcube.gaming;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * From: Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
 * <p>
 * The display includes a textfield for showing the current time. The average
 * FPS/UPS values are drawn in the game's JPanel.
 * 
 * Pausing/Resuming/Quiting are controlled via the frame's window listener
 * methods. (-not exactly)
 * 
 * Uses active rendering to update the JPanel.
 * 
 * Using Java 3D's timer: J3DTimer.getValue() nanosecs rather than millisecs for
 * the period
 */
public class GameWindow extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	private static int FPS = 60;

	private static volatile long fpsPeriod;

	private RunnableGamePanel gamePanel; // where the game is drawn
	private JTextField jtfTime; // displays time spent in game

	public GameWindow(long period) {
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

		JPanel ctrls = new JPanel(); // a row of textfields
		ctrls.setLayout(new BoxLayout(ctrls, BoxLayout.X_AXIS));

		jtfTime = new JTextField("Time Spent: 0 secs");
		jtfTime.setEditable(false);
		ctrls.add(jtfTime);

		container.add(ctrls, "South");
	}

	public void setTimeSpent(long t) {
		jtfTime.setText("Time Spent: " + t + " secs");
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
		// TODO fix fps reporting do adjust when fps changes
		System.out.println("fps: " + fps + "; period: " + period + " ms");

		new GameWindow(period * 1000000L); // ms --> nanosecs
	}

	public static void changeFps(int fps) {
		FPS += fps;
		if (FPS < 1)
			FPS = 1;
		if (FPS > 200)
			FPS = 200;
		long period = (long) 1000.0 / getFPS();
		GameWindow.fpsPeriod = period;
	}

	public static int getFPS() {
		return FPS;
	}

	public static long getFpsPeriod() {
		return fpsPeriod * 1000000L;
	}
}
