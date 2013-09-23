package com.dvlcube.gaming;

import com.craigl.softsynth.BasicOscillator;
import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.SamplePlayer;
import com.craigl.softsynth.VCF;

/**
 * @author wonka
 * @since 22/09/2013
 */
public class SoundTrack {

	public final BasicOscillator osc;
	public final VCF vcf;
	private SamplePlayer player;

	public SoundTrack() {
		osc = new BasicOscillator();

		// Set the frequency
		osc.setFrequency(100);

		// Set the waveshape
		osc.setWaveshape(WAVESHAPE.SQU);

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

		// Initiate note on event
		vcf.noteOn();
	}

	public void stop() {
		player.stopPlayer();
	}

	public void noteOn() {
		vcf.noteOn();
	}

	public void noteOff() {
		vcf.noteOff();
	}
}
