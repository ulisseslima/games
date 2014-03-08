package com.dvlcube.game.flappy;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.dvlcube.gaming.physics.PhysicalObject2D;

/**
 * @author wonka
 * @since 01/03/2014
 */
public class Bird extends PhysicalObject2D {
	{
		physics.weight = 4;
		physics.speed = 17;
	}

	public Bird() {
		super(new Dimension(25, 15), new Point(50, 200));
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			flap();
		}
	}

	private void flap() {
		physics.acceleration += physics.speed / physics.weight;

		if (physics.acceleration < physics.speed / physics.weight)
			physics.acceleration = physics.speed / physics.weight;

		if (physics.acceleration > (physics.speed / physics.weight) * 2)
			physics.acceleration = (physics.speed / physics.weight) * 2;

		keepFalling();
	}
}
