package com.dvlcube.gaming.sound;

import com.craigl.softsynth.BasicOscillator;
import com.craigl.softsynth.BasicOscillator.WAVESHAPE;
import com.craigl.softsynth.SamplePlayer;

/**
 * From: http://www.drdobbs.com/jvm/music-components-in-java-creating-oscill/
 * 230500178
 * 
 * @since 22/09/2013
 */
public class TestBasicOscillator {

	public static void main(String[] args) {
		// Create an oscillator sample producer
		BasicOscillator osc = new BasicOscillator();

		// Set the frequency
		osc.setFrequency(500);

		// Set the waveashape
		osc.setWaveshape(WAVESHAPE.SIN);

		// Create a sample player
		SamplePlayer player = new SamplePlayer();

		// Sets the player's sample provider
		player.setSampleProvider(osc);

		// Start the player
		player.startPlayer();

		// Delay so oscillator can be heard
		delay(1000 * 4);

		// Stop the player
		player.stopPlayer();
	}

	/**
	 * @param i
	 * @author wonka
	 * @since 22/09/2013
	 */
	private static void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
