package com.dvlcube.game.flappy;

import static com.dvlcube.gaming.util.Cuber.mapf;
import static com.dvlcube.gaming.util.Range.$range;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.dvlcube.gaming.Terminatable;
import com.dvlcube.gaming.physics.PhysicalObject2D;
import com.dvlcube.gaming.sound.Sfx;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class Bird extends PhysicalObject2D implements Terminatable {
	{
		physics.weight = 4;
		physics.speed = 17;
		physics.maxAcceleration = (physics.speed / physics.weight) * 2;
	}

	private Sfx bleep = new Sfx("0002.wav");

	public Bird() {
		super(new Dimension(25, 15), new Point(50, 200));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			flap();
		}
	}

	@Override
	public void update() {
		if (!game.ended) {
			angle = mapf(physics.acceleration, $range(physics.minAcceleration, physics.maxAcceleration),
					$range(Math.toRadians(90), Math.toRadians(-45)));

			super.update();
		}
	}

	private void flap() {
		physics.acceleration += physics.speed / physics.weight;

		bleep.play();
		keepFalling();
	}

	@Override
	public void terminate() {
		bleep.terminate();
	}
}
