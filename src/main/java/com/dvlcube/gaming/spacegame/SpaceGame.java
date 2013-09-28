package com.dvlcube.gaming.spacegame;

import static com.dvlcube.gaming.util.Cuber.xy;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Coords;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.GamePolygon;
import com.dvlcube.gaming.sound.Synthesizer;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SpaceGame implements Game {
	public boolean debug = false;
	private Random random = new Random();

	private Synthesizer soundTrack = new Synthesizer();
	private List<GamePolygon> polys = new ArrayList<GamePolygon>();
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private final SpaceCraft spaceCraft = new SpaceCraft(new Coords(150, 150));

	/**
	 * Effective screen size
	 */
	private final int SC_W = 1024 / 2;
	private final int SC_H = 768 / 2;

	private int frequencyGoal = 40;

	{
		polys.add(spaceCraft);
		soundTrack.osc.setWaveshape(WAVESHAPE.SIN);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(SC_W), y = random.nextInt(SC_H);
			polys.add(new Asteroid(xy(x, y)));
		}
	}

	@Override
	public void doLogic() {
		for (GamePolygon polygon : polys) {
			polygon.update();
		}
		if (soundTrack.osc.getFrequency() < frequencyGoal) {
			soundTrack.osc.addFrequency(2.5);
			soundTrack.osc.addModulationDepth(0.1);
			if (soundTrack.osc.getFrequency() > frequencyGoal)
				frequencyGoal = 1;
		} else {
			soundTrack.osc.addFrequency(-2.5);
			soundTrack.osc.addModulationDepth(-0.1);
			if (soundTrack.osc.getFrequency() < frequencyGoal)
				frequencyGoal = 40;
		}
	}

	@Override
	public void doGraphics(Graphics2D g) {
		for (GamePolygon polygon : polys) {
			int polyX = polygon.getX();
			int polyY = polygon.getY();
			int polyAngle = polygon.getAngle();
			double fixedAngle = Math.toRadians(polyAngle);
			if (debug)
				System.out.printf("filling %d,%d at %fº\n", polyX, polyY,
						fixedAngle);
			g.rotate(fixedAngle, polyX, polyY);
			g.drawPolygon((Polygon) polygon);
			g.rotate(-fixedAngle, polyX, polyY);
			polygon.draw(g);
		}
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX() / 2, y = e.getY() / 2;
			spaceCraft.go(x, y);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			if (keyCode == KeyEvent.VK_UP) {
				spaceCraft.up();
			}

			if (keyCode == KeyEvent.VK_DOWN) {
				spaceCraft.down();
			}

			if (keyCode == KeyEvent.VK_LEFT) {
				spaceCraft.left();
			}

			if (keyCode == KeyEvent.VK_RIGHT) {
				spaceCraft.right();
			}
		}
	}

	@Override
	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	@Override
	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	@Override
	public void reset() {
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(SC_W, SC_H);
	}

	@Override
	public List<Controllable> getControllables() {
		return null;
	}
}