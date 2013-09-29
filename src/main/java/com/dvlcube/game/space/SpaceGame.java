package com.dvlcube.game.space;

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
public class SpaceGame extends Game {
	private Random random = new Random();
	private Synthesizer synth = new Synthesizer();
	private List<GamePolygon> polys = new ArrayList<GamePolygon>();
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private final SpaceCraft spaceCraft = new SpaceCraft(new Coords(150, 150));
	private int frequencyGoal = 40;

	/**
	 * @param screen
	 * @param scale
	 * @author wonka
	 * @since 28/09/2013
	 */
	public SpaceGame(Dimension screen, double scale) {
		super(screen, scale);
	}

	/**
	 * @param screen
	 * @author wonka
	 * @since 28/09/2013
	 */
	public SpaceGame(Dimension screen) {
		super(screen);
	}

	{
		addTerminatables(synth);
		polys.add(spaceCraft);
		synth.osc.setWaveshape(WAVESHAPE.SIN);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(screen.width), y = random
					.nextInt(screen.height);
			polys.add(new Asteroid(xy(x, y)));
		}
	}

	@Override
	public void doLogic() {
		for (GamePolygon polygon : polys) {
			polygon.update();
		}
		if (synth.osc.getFrequency() < frequencyGoal) {
			synth.osc.addFrequency(2.5);
			synth.osc.addModulationDepth(0.1);
			if (synth.osc.getFrequency() > frequencyGoal)
				frequencyGoal = 1;
		} else {
			synth.osc.addFrequency(-2.5);
			synth.osc.addModulationDepth(-0.1);
			if (synth.osc.getFrequency() < frequencyGoal)
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
				System.out.printf("filling %d,%d at %f�\n", polyX, polyY,
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
	public List<Controllable> getControllables() {
		return null;
	}
}