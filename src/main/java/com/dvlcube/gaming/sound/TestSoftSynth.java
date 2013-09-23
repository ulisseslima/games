package com.dvlcube.gaming.sound;

import com.craigl.softsynth.BasicOscillator;
import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.SamplePlayer;
import com.craigl.softsynth.VCF;
import com.dvlcube.gaming.util.Cuber;

/**
 * 
 * @author wonka
 * @since 22/09/2013
 */
public class TestSoftSynth {
	public static void main(String[] args) {
		// SoftSynth.main(new String[] { "4" });
		exampleFour();
	}

	public static void exampleFour() {

		// Create an oscillator
		BasicOscillator osc = new BasicOscillator();

		// Set the frequency
		osc.setFrequency(100);

		// Set the waveshape
		osc.setWaveshape(WAVESHAPE.SQU);

		// Create a VCF
		VCF vcf = new VCF();

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

		// Create a sample player
		SamplePlayer player = new SamplePlayer();

		// Sets the sample player's sample provider
		player.setSampleProvider(vcf);

		// Start the player
		player.startPlayer();

		// Initiate note on event
		vcf.noteOn();

		Cuber.delay(1000 * 4);

		// Release note event
		vcf.noteOff();

		Cuber.delay(1000 * 4);

		osc.setFrequency(69);

		for (int i = 0; i < 10; i++) {
			osc.setFrequency(68 - i);
			Cuber.delay(60);
		}

		for (int i = 0; i < 100; i++) {
			osc.setFrequency(58 + i);
			Cuber.delay(5);
		}

		vcf.setAttackTimeInMS(500);
		vcf.setReleaseTimeInMS(500);
		vcf.setCutoffFrequencyInHz(200);
	}
}
