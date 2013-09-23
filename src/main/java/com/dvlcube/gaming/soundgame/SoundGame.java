package com.dvlcube.gaming.soundgame;

import static com.dvlcube.gaming.util.Cuber.map;
import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.EnvelopeGenerator;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.SoundTrack;
import com.dvlcube.gaming.util.Range;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SoundGame implements Game {
	public boolean debug = false;
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private SoundTrack sound = new SoundTrack();

	/**
	 * Screen
	 */
	private final int SC_W = 1024;
	private final int SC_H = 768;

	@Override
	public void doLogic() {
	}

	@Override
	public void doGraphics(Graphics2D g) {
		int offset = 10, y = 50;
		g.drawString(String.format("wave (1 saw, 2 sine, 3 square): %s",
				sound.osc.getWaveshape().name()), offset, y);
		y += 15;
		g.drawString(String.format("frequency (q-/w+): %f",
				sound.osc.getFrequency()), offset, y);
		y += 15;
		g.drawString(
				String.format("attack time (a-/s+/mouse x): %d",
						sound.vcf.getAttackTime()), offset, y);
		y += 15;
		g.drawString(
				String.format("decay time (z-/x+): %d", sound.vcf.getDecayMS()),
				offset, y);
		y += 15;
		g.drawString(
				String.format("sustain level (e-/r+): %f",
						sound.vcf.getSustainLevel()), offset, y);
		y += 15;
		g.drawString(
				String.format("release time (d-/f+/mouse y): %d",
						sound.vcf.getReleaseMS()), offset, y);
		y += 15;
		g.drawString(
				String.format("cutoff frequency (c-/v+): %f",
						sound.vcf.getCutoffFrequencyInHz()), offset, y);
		y += 15;
		g.drawString(String.format("resonance (t-/y+): %f",
				sound.vcf.getResonance()), offset, y);
		y += 15;
		g.drawString(String.format("depth (g-/h+): %f", sound.vcf.getDepth()),
				offset, y);
		y += 15;
		g.drawString(
				String.format("note %s", sound.vcf.isNoteOn() ? "on" : "off"),
				offset, y);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX(), y = e.getY();

			Range<Integer> millisRange = r(EnvelopeGenerator.MS_MIN,
					EnvelopeGenerator.MS_MAX);

			sound.vcf.setAttackTimeInMS(map(x, r(0, SC_W), millisRange));
			sound.vcf.setReleaseTimeInMS(map(y, r(0, SC_H), millisRange));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			sound.toggleNote();
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {
			case KeyEvent.VK_Q:
				sound.osc.addFrequency(-0.9);
				break;
			case KeyEvent.VK_W:
				sound.osc.addFrequency(0.9);
				break;
			case KeyEvent.VK_A:
				sound.vcf.addAttackTime(-10);
				break;
			case KeyEvent.VK_S:
				sound.vcf.addAttackTime(10);
				break;
			case KeyEvent.VK_Z:
				sound.vcf.addDecayTime(-1);
				break;
			case KeyEvent.VK_X:
				sound.vcf.addDecayTime(1);
				break;
			case KeyEvent.VK_E:
				sound.vcf.addSustainLevel(-0.01);
				break;
			case KeyEvent.VK_R:
				sound.vcf.addSustainLevel(0.01);
				break;
			case KeyEvent.VK_D:
				sound.vcf.addReleaseTime(-10);
				break;
			case KeyEvent.VK_F:
				sound.vcf.addReleaseTime(10);
				break;
			case KeyEvent.VK_C:
				sound.vcf.addCutoffFrequency(-10.1);
				break;
			case KeyEvent.VK_V:
				sound.vcf.addCutoffFrequency(10.1);
				break;
			case KeyEvent.VK_T:
				sound.vcf.addResonance(-0.01);
				break;
			case KeyEvent.VK_Y:
				sound.vcf.addResonance(0.01);
				break;
			case KeyEvent.VK_G:
				sound.vcf.addDepth(-0.01);
				break;
			case KeyEvent.VK_H:
				sound.vcf.addDepth(0.01);
				break;
			case KeyEvent.VK_1:
				sound.osc.setWaveshape(WAVESHAPE.SAW);
				break;
			case KeyEvent.VK_2:
				sound.osc.setWaveshape(WAVESHAPE.SIN);
				break;
			case KeyEvent.VK_3:
				sound.osc.setWaveshape(WAVESHAPE.SQU);
				break;
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
}