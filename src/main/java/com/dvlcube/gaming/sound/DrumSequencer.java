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

import static com.dvlcube.gaming.util.Cuber.mapf;
import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.SwingUtilities;

import com.dvlcube.gaming.Checkbox;
import com.dvlcube.gaming.ControllableObject;
import com.dvlcube.gaming.Knob;
import com.dvlcube.gaming.Terminatable;
import com.dvlcube.gaming.util.Range;

/**
 * Adapted from an example from Head First Java.
 * 
 * @author Wonka
 */
public class DrumSequencer extends ControllableObject implements Terminatable {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_TICKS = 16;
	public static final int DEFAULT_CHECKBOX_X_OFFSET = 100;

	/**
	 * These represent the actual drum "keys". the drum channel is like a piano except each "key" on the piano
	 * is a different drum. So the number 35 is the key for the Bass drum, 42 is closed hi-hat, etc.
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
		public List<Checkbox> ticks = Checkbox.list(DEFAULT_TICKS, DEFAULT_CHECKBOX_X_OFFSET, this.ordinal());

		Instrument(String name, int note) {
			this.name = name;
			this.key = note;
		}

		/**
		 * @param e
		 * @param mx
		 *            mouse x
		 * @param my
		 *            mouse y
		 * @return whether this element was toggled as a result of a click.
		 * @author wonka
		 * @since 28/09/2013
		 */
		public static boolean tickChanged(MouseEvent e, int mx, int my) {
			for (Instrument instrument : Instrument.values()) {
				for (Checkbox checkbox : instrument.ticks) {
					if (checkbox.toggled(e, mx, my)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private Track track;
	private Sequence sequence;
	private Sequencer sequencer;
	private boolean playing;
	private float tempoFactor;
	private List<Knob> knobs = new ArrayList<>();
	private Knob tempoKnob = new TempoKnob();
	private Integer mouseYStartDrag = null;

	public DrumSequencer() {
		setUpMidi();
		knobs.add(tempoKnob);
	}

	public float downTempo() {
		float tempoFac = sequencer.getTempoFactor();
		float factor = (float) (tempoFac * .97);
		sequencer.setTempoFactor(factor);
		return tempoFactor = factor;
	}

	/**
	 * The Tempo Factor scales the sequencer's tempo by the factor provided. The default is 1.0, so we're
	 * adjusting +/-3% per click.
	 */
	public float upTempo() {
		float tempoFac = sequencer.getTempoFactor();
		float factor = (float) (tempoFac * 1.03);
		sequencer.setTempoFactor(factor);
		return tempoFactor = factor;
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

		for (Knob knob : knobs) {
			knob.draw(g, xOffset, yOffset);
		}
	}

	/**
	 * we'll make a 16-element array to hold the values for one instrument, across all 16 beats. If the
	 * instrument is supposed to play on that beat, the value at that element will be the key. If that
	 * instrument is NOT supposed to play on that beat, put in a zero
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
			track.add(makeEvent(ShortMessage.CONTROL_CHANGE, 1, 127, 0, waitBeforeLooping));
		}

		/**
		 * We always want to make sure that there IS an event at beat 16 (it goes 0 to 15). Otherwise, the
		 * Sequencer might not go the full 16 beats before it starts over.
		 */
		track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 9, 1, 0, 15));
		try {
			sequencer.setSequence(sequence);
			/**
			 * Let's you specify the number of loop iterations, or in this case, continuous looping.
			 */
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			if (!sequencer.isOpen())
				sequencer.open();
			sequencer.start();
			playing = true;
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
	 * The usual MIDI setup stuff for getting the sequencer, the Sequence, and the Track. Again. nothing
	 * special.
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
	}

	@Override
	public void mousePressed(MouseEvent e, int mx, int my) {
		if (Instrument.tickChanged(e, mx, my)) {
			buildTrackAndStart();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e, int mx, int my) {
		int y = e.getY();
		if (mouseYStartDrag == null)
			mouseYStartDrag = y;
		int yDiff = y - mouseYStartDrag;

		boolean left = SwingUtilities.isLeftMouseButton(e);
		if (left) {
			tempoKnob.setValue(yDiff, game.vRange);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e, int mx, int my) {
		mouseYStartDrag = null;
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
			stop();
		} else {
			buildTrackAndStart();
		}
	}

	private void stop() {
		if (sequencer.isOpen())
			sequencer.stop();
		playing = false;
	}

	private class TempoKnob implements Knob {
		private DrumSequencer $ = DrumSequencer.this;
		private Range<java.lang.Float> range = r(0f, 5f);

		@Override
		public String getName() {
			return TempoKnob.class.getSimpleName();
		}

		@Override
		public Number getValue() {
			return $.tempoFactor;
		}

		@Override
		public Number setValue(Number number) {
			$.sequencer.setTempoFactor(number.floatValue());
			return $.tempoFactor = $.sequencer.getTempoFactor();
		}

		@Override
		public Number setValue(Number number, Range<? extends Number> range) {
			float mappedValue = mapf(number, range, this.range);
			return setValue(mappedValue);
		}

		@Override
		public Number increase() {
			return $.upTempo();
		}

		@Override
		public Number decrease() {
			return $.downTempo();
		}

		@Override
		public Range<java.lang.Float> getRange() {
			return range;
		}

		@Override
		public void draw(Graphics2D g, int x, int y) {
			g.drawString(getName() + ": " + getValue(), x, y);
		}
	}

	@Override
	public void terminate() {
		sequencer.close();
		sequencer = null;
	}
}