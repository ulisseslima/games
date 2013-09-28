/*
  This file is part of Cuber.

    Cuber is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Cuber is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cuber.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dvlcube.gaming.sound;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.dvlcube.gaming.Checkbox;
import com.dvlcube.gaming.DefaultController;

/**
 * Adapted from an example from Head First Java.
 * 
 * @author Wonka
 */
public class DrumSequencer extends DefaultController {

	/**
	 * These represent the actual drum "keys". the drum channel is like a piano
	 * except each "key" on the piano is a different drum. So the number 35 is
	 * the key for the Bass drum, 42 is closed hi-hat, etc.
	 */
	public enum Instrument {
		BASS_DRUM("Bass Drum", 35), //
		CLOSED_HI_HAT("Closed Hi-Hat", 42), //
		OPEN_HI_HAT("Open Hi-Hat", 46), //
		ACOUSTIC_SNARE("Acoustic Snare", 38), //
		CRASH_CYMBAL("Crash Cymbal", 49), //
		HAND_CLAP("Hand Clap", 39), //
		HIGH_TOM("High Tom", 50), //
		HI_BONGO("Hi Bongo", 60), //
		MARACAS("Maracas", 70), //
		WHISTLE("Whistle", 72), //
		LOW_CONGA("Low Conga", 64), //
		COWBELL("Cowbell", 56), //
		VIBRASLAP("Vibraslap", 58), //
		LOW_MID_TOM("Low-mid Tom", 47), //
		HIGH_AGOGO("High Agogo", 67), //
		OPEN_HI_CONGA("Open Hi Conga", 63);
		public String name;
		public int key;
		public List<Checkbox> ticks = Checkbox.list(16, 100, this.ordinal());

		Instrument(String name, int note) {
			this.name = name;
			this.key = note;
		}

		/**
		 * @param e
		 * @param mx
		 * @param my
		 * @author wonka
		 * @since 28/09/2013
		 */
		public static void mousePressed(MouseEvent e, int mx, int my) {
			for (Instrument instrument : Instrument.values()) {
				for (Checkbox checkbox : instrument.ticks) {
					checkbox.mousePressed(e, mx, my);
				}
			}
		}
	}

	private Track track;
	private Sequence sequence;
	private Sequencer sequencer;
	private boolean playing;

	public DrumSequencer() {
		setUpMidi();
	}

	public float downTempo() {
		float tempoFactor = sequencer.getTempoFactor();
		float factor = (float) (tempoFactor * .97);
		sequencer.setTempoFactor(factor);
		return factor;
	}

	/**
	 * The Tempo Factor scales the sequencer's tempo by the factor provided. The
	 * default is 1.0, so we're adjusting +/-3% per click.
	 */
	public float upTempo() {
		float tempoFactor = sequencer.getTempoFactor();
		float factor = (float) (tempoFactor * 1.03);
		sequencer.setTempoFactor(factor);
		return factor;
	}

	@Override
	public void draw(Graphics2D g) {
		int xOffset = 10, yOffset = 10, ySpacing = 10;
		for (Instrument instrument : Instrument.values()) {
			g.drawString(instrument.name, xOffset, yOffset);
			for (Checkbox tick : instrument.ticks) {
				tick.draw(g);
			}
			yOffset += ySpacing;
		}
	}

	/**
	 * we'll make a 16-element array to hold the values for one instrument,
	 * across all 16 beats. If the instrument is supposed to play on that beat,
	 * the value at that element will be the key. If that instrument is NOT
	 * supposed to play on that beat, put in a zero
	 */
	public void buildTrackAndStart() {

		sequence.deleteTrack(track); // get rid of the old track,
		track = sequence.createTrack(); // make a fresh one

		int waitBeforeLooping = 16;
		for (Instrument instrument : Instrument.values()) {
			int t = 0;
			for (Checkbox tick : instrument.ticks) {
				if (tick.checked) {
					track.add(makeEvent(144, 9, instrument.key, 100, t));
					track.add(makeEvent(128, 9, instrument.key, 100, t + 1));
				}
				t++;
			}
			track.add(makeEvent(ShortMessage.CONTROL_CHANGE, 1, 127, 0,
					waitBeforeLooping));
		}

		/**
		 * We always want to make sure that there IS an event at beat 16 (it
		 * goes 0 to 15). Otherwise, the Sequencer might not go the full 16
		 * beats before it starts over.
		 */
		track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 9, 1, 0, 15));
		try {
			sequencer.setSequence(sequence);
			/**
			 * Let's you specify the number of loop iterations, or in this case,
			 * continuous looping.
			 */
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage msg = new ShortMessage();
			msg.setMessage(comd, chan, one, two);
			event = new MidiEvent(msg, tick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

	/**
	 * The usual MIDI setup stuff for getting the sequencer, the Sequence, and
	 * the Track. Again. nothing special.
	 */
	public final void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		Instrument.mousePressed(e, mx, my);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			togglePlay();
			break;
		}
	}

	/**
	 * 
	 * @author wonka
	 * @since 28/09/2013
	 */
	private void togglePlay() {
		if (playing) {
			sequencer.stop();
			playing = false;
		} else {
			buildTrackAndStart();
			playing = true;
		}
	}
}
