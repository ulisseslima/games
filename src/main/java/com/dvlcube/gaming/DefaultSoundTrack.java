package com.dvlcube.gaming;

import static com.dvlcube.gaming.util.Cuber.map;
import static com.dvlcube.gaming.util.Cuber.mapd;
import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

import com.craigl.softsynth.AdvancedOscillator;
import com.craigl.softsynth.AdvancedOscillator.MOD_TYPE;
import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.EnvelopeGenerator;
import com.craigl.softsynth.SamplePlayer;
import com.craigl.softsynth.VCF;
import com.dvlcube.gaming.util.Range;

/**
 * @author wonka
 * @since 22/09/2013
 */
public class DefaultSoundTrack {

	public final AdvancedOscillator osc;
	public final VCF vcf;
	private SamplePlayer player;

	public static final Range<Integer> millisRange = r(
			EnvelopeGenerator.MS_MIN, EnvelopeGenerator.MS_MAX);
	public static final Range<Double> cutRange = r(VCF.MIN_CUTOFF,
			VCF.MAX_CUTOFF);
	public static final Range<Double> depthRange = r(VCF.MIN_DEPTH,
			VCF.MAX_DEPTH);
	public static final Range<Double> modRange = r(
			AdvancedOscillator.MOD_DEPTH_MIN, AdvancedOscillator.MOD_DEPTH_MAX);
	public static final Range<Double> sustainRange = r(
			EnvelopeGenerator.SUSTAIN_MIN, EnvelopeGenerator.SUSTAIN_MAX);
	public static final Range<Integer> defaultRange = r(-5000, 5000);
	public static final Range<Integer> detuneRange = r(
			AdvancedOscillator.CENTS_DETUNE_MIN,
			AdvancedOscillator.CENTS_DETUNE_MAX);

	public DefaultSoundTrack() {
		osc = new AdvancedOscillator();

		// Set the frequency
		osc.setFrequency(100);

		// Set the waveshape
		osc.setWaveshape(WAVESHAPE.SQU);
		osc.setModulationType(MOD_TYPE.AM);
		osc.setModulationDepth(1.0);
		osc.setDetuneInCents(0);

		vcf = new VCF();

		// Set the VCF's sample provider
		vcf.setSampleProvider(osc);

		// Parameterize the envelope generator
		vcf.setAttackTimeInMS(2000);
		vcf.setDecayTimeInMS(1);
		vcf.setSustainLevel(1.0);
		vcf.setReleaseTimeInMS(2000);

		// Parameterize the filter
		vcf.setCutoffFrequencyInHz(1000);
		vcf.setResonance(0.85);
		vcf.setDepth(2.0);

		player = new SamplePlayer();

		// Sets the sample player's sample provider
		player.setSampleProvider(vcf);

		// Start the player
		player.startPlayer();
	}

	public void stop() {
		player.stopPlayer();
	}

	/**
	 * 
	 * @author wonka
	 * @since 23/09/2013
	 */
	public void toggleNote() {
		if (vcf.isNoteOn()) {
			vcf.noteOff();
		} else {
			vcf.noteOn();
		}
	}

	/**
	 * @param g
	 *            graphics 2D
	 * @author wonka
	 * @param screenH
	 * @param screenW
	 * @since 24/09/2013
	 */
	public void drawOverlay(Graphics2D g, int screenW, int screenH) {
		int offset = 10, y = 50;

		Range<Integer> indicatorRange = r(-10, 10);
		int controllers = 10;
		int[] ys = new int[controllers];
		int vSpacing = 15;

		g.drawString(String.format("wave (1 saw, 2 sine, 3 square): %s", osc
				.getWaveshape().name()), offset, y);

		y += vSpacing;
		g.drawString(String.format("mod (4 am, 5 fm, 6 none): %s", osc
				.getModulationType().name()), offset, y);

		y += vSpacing;
		g.drawString(String.format("note %s", vcf.isNoteOn() ? "on" : "off"),
				offset, y);
		/**/
		y += vSpacing;
		double frequency = osc.getFrequency();
		g.drawString(String.format("frequency (q-/w+/mouse x): %f", frequency),
				offset, y);
		ys[0] = map(frequency, r(0, screenW), indicatorRange);

		y += vSpacing;
		int attackTime = vcf.getAttackTime();
		g.drawString(String.format("attack time (a-/s+/mouse x + mb 1): %d",
				attackTime), offset, y);
		ys[1] = map(attackTime, r(0, screenW), indicatorRange);

		y += vSpacing;
		int decayMS = vcf.getDecayMS();
		g.drawString(String.format("decay time (z-/x+): %d", decayMS), offset,
				y);
		ys[2] = map(decayMS, millisRange, indicatorRange);

		y += vSpacing;
		double sustainLevel = vcf.getSustainLevel();
		g.drawString(String.format("sustain level (e-/r+): %f", sustainLevel),
				offset, y);
		ys[3] = map(sustainLevel, sustainRange, indicatorRange);

		y += vSpacing;
		int releaseMS = vcf.getReleaseMS();
		g.drawString(String.format("release time (d-/f+/mouse y + mb 1): %d",
				releaseMS), offset, y);
		ys[4] = map(releaseMS, millisRange, indicatorRange);

		y += vSpacing;
		double cutoffFrequencyInHz = vcf.getCutoffFrequencyInHz();
		g.drawString(String.format(
				"cutoff frequency (c-/v+/mouse x + mb 2): %f",
				cutoffFrequencyInHz), offset, y);
		ys[5] = map(cutoffFrequencyInHz, cutRange, indicatorRange);

		y += vSpacing;
		double resonance = vcf.getResonance();
		g.drawString(String.format("resonance (t-/y+): %f", resonance), offset,
				y);
		ys[6] = map(resonance, defaultRange, indicatorRange);

		y += vSpacing;
		double depth = vcf.getDepth();
		g.drawString(
				String.format("depth range (g-/h+/mouse y + mb 2): %f", depth),
				offset, y);
		ys[7] = map(depth, depthRange, indicatorRange);

		y += vSpacing;
		double modDepth = osc.getModulationDepth();
		g.drawString(String.format("mod depth (b-/n+/mouse y): %f", modDepth),
				offset, y);
		ys[8] = map(modDepth, modRange, indicatorRange);

		y += vSpacing;
		int detune = osc.getDetuneCents();
		g.drawString(String.format("detune (u-/i+/mouse wheel): %d", detune),
				offset, y);
		ys[9] = map(detune, detuneRange, indicatorRange);

		y += vSpacing * 2;
		int[] xs = new int[controllers];
		int cOffset = 0;
		int cOffsetFactor = (screenW / 2) / (controllers - 1);
		for (int i = 0; i < controllers; i++) {
			xs[i] = cOffset;
			cOffset += cOffsetFactor;
			ys[i] = ys[i] + y;
		}
		g.drawPolyline(xs, ys, controllers);
	}

	/**
	 * @param e
	 *            mouse event.
	 * @param sC_W
	 * @param sC_H
	 * @author wonka
	 * @since 24/09/2013
	 */
	public void mouseMoved(MouseEvent e, int screenW, int screenH) {
		int x = e.getX(), y = e.getY();

		double depth = mapd(y, r(screenH, 0), modRange);
		osc.setModulationDepth(depth);
		osc.setFrequency(x);
	}

	/**
	 * @param e
	 * @author wonka
	 * @since 24/09/2013
	 */
	public void mousePressed(MouseEvent e) {
		toggleNote();
	}

	/**
	 * @param e
	 * @author wonka
	 * @since 24/09/2013
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		if (wheelRotation < 0) {
			osc.addDetuneCents(10);
		} else if (wheelRotation > 0) {
			osc.addDetuneCents(-5);
		}
	}

	/**
	 * @param e
	 * @param sC_W
	 * @param sC_H
	 * @author wonka
	 * @since 24/09/2013
	 */
	public void mouseDragged(MouseEvent e, int screenW, int screenH) {
		int x = e.getX(), y = e.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			vcf.setAttackTimeInMS(map(x, r(0, screenW), millisRange));
			vcf.setReleaseTimeInMS(map(y, r(0, screenH), millisRange));
		} else if (SwingUtilities.isRightMouseButton(e)) {
			vcf.setCutoffFrequencyInHz(mapd(x, r(0, screenH), cutRange));
			vcf.setDepth(mapd(y, r(0, screenH), depthRange));
		} else if (SwingUtilities.isMiddleMouseButton(e)) {

		}
	}

	/**
	 * @param e
	 * @author wonka
	 * @since 25/09/2013
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			osc.addFrequency(-0.9);
			break;
		case KeyEvent.VK_W:
			osc.addFrequency(0.9);
			break;
		case KeyEvent.VK_A:
			vcf.addAttackTime(-10);
			break;
		case KeyEvent.VK_S:
			vcf.addAttackTime(10);
			break;
		case KeyEvent.VK_Z:
			vcf.addDecayTime(-10);
			break;
		case KeyEvent.VK_X:
			vcf.addDecayTime(10);
			break;
		case KeyEvent.VK_E:
			vcf.addSustainLevel(-0.01);
			break;
		case KeyEvent.VK_R:
			vcf.addSustainLevel(0.01);
			break;
		case KeyEvent.VK_D:
			vcf.addReleaseTime(-10);
			break;
		case KeyEvent.VK_F:
			vcf.addReleaseTime(10);
			break;
		case KeyEvent.VK_C:
			vcf.addCutoffFrequency(-10.1);
			break;
		case KeyEvent.VK_V:
			vcf.addCutoffFrequency(10.1);
			break;
		case KeyEvent.VK_T:
			vcf.addResonance(-0.01);
			break;
		case KeyEvent.VK_Y:
			vcf.addResonance(0.01);
			break;
		case KeyEvent.VK_G:
			vcf.addDepth(-0.01);
			break;
		case KeyEvent.VK_H:
			vcf.addDepth(0.01);
			break;
		case KeyEvent.VK_B:
			osc.addModulationDepth(-0.01);
			break;
		case KeyEvent.VK_N:
			osc.addModulationDepth(0.01);
			break;
		case KeyEvent.VK_U:
			osc.addDetuneCents(-100);
			break;
		case KeyEvent.VK_I:
			osc.addDetuneCents(100);
			break;
		case KeyEvent.VK_1:
			osc.setWaveshape(WAVESHAPE.SAW);
			break;
		case KeyEvent.VK_2:
			osc.setWaveshape(WAVESHAPE.SIN);
			break;
		case KeyEvent.VK_3:
			osc.setWaveshape(WAVESHAPE.SQU);
			break;
		case KeyEvent.VK_4:
			osc.setModulationType(MOD_TYPE.AM);
			break;
		case KeyEvent.VK_5:
			osc.setModulationType(MOD_TYPE.FM);
			break;
		case KeyEvent.VK_6:
			osc.setModulationType(MOD_TYPE.NONE);
			break;
		}
	}
}
