package com.dvlcube.gaming.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.dvlcube.gaming.Terminatable;

/**
 * Represents a sound clip for a sound effect.
 * 
 * @author wonka
 * @since 08/03/2014
 */
public class Sfx implements Terminatable {
	Clip clip;
	AudioInputStream sound;

	public Sfx(String wav) {
		try {
			clip = AudioSystem.getClip();
			sound = AudioSystem.getAudioInputStream(Synthesizer.class.getResource(wav));
			clip.open(sound);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void terminate() {
		try {
			if (clip.isOpen()) {
				clip.close();
			}
			sound.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
}
