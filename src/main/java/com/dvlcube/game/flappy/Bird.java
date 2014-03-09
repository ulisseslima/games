package com.dvlcube.game.flappy;

import static com.dvlcube.gaming.util.Cuber.mapf;
import static com.dvlcube.gaming.util.Range.$range;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.dvlcube.gaming.F;
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
		physics.maxAcceleration = new F() {

			@Override
			public Float x() {
				return (physics.speed / physics.weight) * 2;
			}
		};
		physics.aerodynamic = 1.4f;
	}

	private Sfx bleep = new Sfx("0002.wav");

	public Bird() {
		super(new Dimension(25, 15), new Point(50, 200));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		flap();
	}

	@Override
	public void update() {
		if (!game.ended) {
			int max = 2;
			int min = -10;
			float adjustedAcceleration = physics.acceleration < min ? min : physics.acceleration;
			adjustedAcceleration = adjustedAcceleration > max ? max : adjustedAcceleration;
			angle = mapf(adjustedAcceleration, $range(min, max), $range(90, -25));

			super.update();
		}
	}

	private void flap() {
		physics.acceleration += (physics.speed / physics.weight) * physics.aerodynamic;

		bleep.play();
		keepFalling();
	}

	@Override
	public void terminate() {
		bleep.terminate();
	}
}
