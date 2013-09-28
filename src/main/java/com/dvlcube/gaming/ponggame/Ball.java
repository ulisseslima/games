package com.dvlcube.gaming.ponggame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import com.dvlcube.gaming.Controllable;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.sound.Synthesizer;

/**
 * @author wonka
 * @since 26/09/2013
 */
public class Ball extends Rectangle implements Controllable {

	private static final long serialVersionUID = 3929278993084690115L;
	private Synthesizer soundTrack = new Synthesizer();
	public boolean debug = false;
	private PongGame game;
	private List<Paddle> paddles = new ArrayList<>();
	private int scW;
	private int scH;
	private int xDirection = 1;
	private int yDirection = 1;
	private int velocity = 1;
	public static int maxVelocity = 10;

	public Ball(Point point, Dimension dimension) {
		super(point, dimension);
	}

	public Ball(Point point) {
		super(point, new Dimension(10, 10));
	}

	@Override
	public void update() {
		x += velocity * xDirection;
		y += velocity * yDirection;
		collide();
		correctBounds();
	}

	/**
	 * 
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void collide() {
		for (Paddle paddle : paddles) {
			if (this.intersects(paddle)) {
				xDirection *= -1;
				blip();
				accelerate();
			} else {
				goSilent();
			}
		}
	}

	private void pop() {
		double f = soundTrack.osc.getFrequency();
		if (f != 10) {
			soundTrack.osc.setFrequency(10);
			soundTrack.osc.setModulationDepth(0.5);
		}
	}

	private void blip() {
		double f = soundTrack.osc.getFrequency();
		if (f < 40) {
			soundTrack.osc.setFrequency(40);
			soundTrack.osc.setModulationDepth(1.0);
		}
	}

	private void goSilent() {
		double f = soundTrack.osc.getFrequency();
		if (f > 0) {
			soundTrack.osc.setFrequency(0);
			soundTrack.osc.setModulationDepth(0);
		}
	}

	private void accelerate() {
		velocity++;
		if (velocity > maxVelocity)
			velocity = maxVelocity;
	}

	/**
	 * @param x
	 * @param y
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void correctBounds() {
		boolean popped = false;
		boolean touchedWall = false;
		if (x > scW) {
			xDirection = -1;
			pop();
			popped = true;
			touchedWall = true;
		} else if (x < 0) {
			xDirection = 1;
			pop();
			popped = true;
			touchedWall = true;
		}

		if (y > scH) {
			yDirection = -1;
			pop();
			popped = true;
		} else if (y < yDirection) {
			yDirection = 1;
			pop();
			popped = true;
		}

		if (!popped) {
			goSilent();
		}

		if (touchedWall) {
			givePoint();
		}
	}

	/**
	 * @author wonka
	 * @since 27/09/2013
	 */
	private void givePoint() {
		Paddle player1 = paddles.get(0);
		Paddle player2 = paddles.get(1);

		int p1Diff = player1.x - x;
		if (p1Diff < 100 && p1Diff > -100) {
			player2.score++;
		} else {
			player1.score++;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.draw(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e, int screenW, int screenH) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e, int x, int y) {

	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}

	@Override
	public void setSource(Game game) {
		this.game = (PongGame) game;
		for (Controllable controllable : game.getControllables()) {
			if (controllable instanceof Paddle) {
				paddles.add((Paddle) controllable);
			}
		}
		scW = game.screen.width;
		scH = game.screen.height;
	}
}
