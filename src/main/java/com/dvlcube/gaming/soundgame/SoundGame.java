package com.dvlcube.gaming.soundgame;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.SoundTrack;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SoundGame implements Game {
	public boolean debug = false;
	private MouseAdapter mouse = new Mouse();
	private KeyAdapter keyboard = new Keyboard();
	private SoundTrack sound = new SoundTrack();

	public void doLogic() {
	}

	public void doGraphics(Graphics2D g) {
		int offset = 10, y = 50;
		g.drawString(String.format("frequency.: %f", sound.osc.getFrequency()),
				offset, y);
		y += 20;
		g.drawString(
				String.format("wave: %s", sound.osc.getWaveshape().name()),
				offset, y);
		y += 20;
		g.drawString(
				String.format("attack time: %d", sound.vcf.getAttackTime()),
				offset, y);
		y += 20;
		g.drawString(String.format("decay time: %d", sound.vcf.getDecayMS()),
				offset, y);
		y += 20;
		g.drawString(
				String.format("sustain level: %f", sound.vcf.getSustainLevel()),
				offset, y);
		y += 20;
		g.drawString(
				String.format("release time: %d", sound.vcf.getReleaseMS()),
				offset, y);
		y += 20;
		g.drawString(
				String.format("cutoff frequency: %f",
						sound.vcf.getCutoffFrequencyInHz()), offset, y);
		y += 20;
		g.drawString(String.format("resonance: %f", sound.vcf.getResonance()),
				offset, y);
		y += 20;
		g.drawString(String.format("depth: %f", sound.vcf.getDepth()), offset,
				y);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
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

	public KeyAdapter getKeyAdapter() {
		return keyboard;
	}

	public MouseAdapter getMouseAdapter() {
		return mouse;
	}

	public void reset() {
	}
}