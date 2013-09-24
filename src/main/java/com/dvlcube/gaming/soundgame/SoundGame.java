package com.dvlcube.gaming.soundgame;

import static com.dvlcube.gaming.util.Cuber.map;
import static com.dvlcube.gaming.util.Cuber.mapi;
import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

import com.craigl.softsynth.AdvancedOscillator;
import com.craigl.softsynth.AdvancedOscillator.MOD_TYPE;
import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.EnvelopeGenerator;
import com.craigl.softsynth.VCF;
import com.dvlcube.gaming.Game;
import com.dvlcube.gaming.SoundTrack;
import com.dvlcube.gaming.util.Range;

/**
 * @author wonka
 * @since 20/09/2013
 */
public class SoundGame implements Game {
	public boolean debug = true;
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
		g.drawString(String.format("mod (4 am, 5 fm, 6 none): %s", sound.osc
				.getModulationType().name()), offset, y);
		y += 15;
		g.drawString(
				String.format("frequency (q-/w+/mouse x): %f",
						sound.osc.getFrequency()), offset, y);
		y += 15;
		g.drawString(String.format("attack time (a-/s+/mouse x + mb 1): %d",
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
		g.drawString(String.format("release time (d-/f+/mouse y + mb 1): %d",
				sound.vcf.getReleaseMS()), offset, y);
		y += 15;
		g.drawString(String.format(
				"cutoff frequency (c-/v+/mouse x + mb 2): %f",
				sound.vcf.getCutoffFrequencyInHz()), offset, y);
		y += 15;
		g.drawString(String.format("resonance (t-/y+): %f",
				sound.vcf.getResonance()), offset, y);
		y += 15;
		g.drawString(String.format("depth range (g-/h+/mouse y + mb 2): %f",
				sound.vcf.getDepth()), offset, y);
		y += 15;
		g.drawString(
				String.format("mod depth (b-/n+/mouse y): %f",
						sound.osc.getModulationDepth()), offset, y);
		y += 15;
		g.drawString(
				String.format("note %s", sound.vcf.isNoteOn() ? "on" : "off"),
				offset, y);
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX(), y = e.getY();

			Range<Double> modRange = r(AdvancedOscillator.MOD_DEPTH_MIN,
					AdvancedOscillator.MOD_DEPTH_MAX);
			double depth = mapi(y, r(0, SC_H), modRange);
			sound.osc.setModulationDepth(depth);

			sound.osc.setFrequency(x);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			sound.toggleNote();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int wheelRotation = e.getWheelRotation();
			if (wheelRotation < 0) {
				// up
			} else if (wheelRotation > 0) {
				// down
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int x = e.getX(), y = e.getY();
			if (SwingUtilities.isLeftMouseButton(e)) {
				Range<Integer> millisRange = r(EnvelopeGenerator.MS_MIN,
						EnvelopeGenerator.MS_MAX);
				sound.vcf.setAttackTimeInMS(map(x, r(0, SC_W), millisRange));
				sound.vcf.setReleaseTimeInMS(map(y, r(0, SC_H), millisRange));
			} else if (SwingUtilities.isRightMouseButton(e)) {
				Range<Double> cutRange = r(VCF.MIN_CUTOFF, VCF.MAX_CUTOFF);
				sound.vcf.setCutoffFrequencyInHz(mapi(x, r(0, SC_H), cutRange));

				Range<Double> depthRange = r(VCF.MIN_DEPTH, VCF.MAX_DEPTH);
				sound.vcf.setDepth(mapi(y, r(0, SC_H), depthRange));
			} else if (SwingUtilities.isMiddleMouseButton(e)) {

			}
		}
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
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
			case KeyEvent.VK_B:
				sound.osc.addModulationDepth(-0.01);
				break;
			case KeyEvent.VK_N:
				sound.osc.addModulationDepth(0.01);
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
			case KeyEvent.VK_4:
				sound.osc.setModulationType(MOD_TYPE.AM);
				break;
			case KeyEvent.VK_5:
				sound.osc.setModulationType(MOD_TYPE.FM);
				break;
			case KeyEvent.VK_6:
				sound.osc.setModulationType(MOD_TYPE.NONE);
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