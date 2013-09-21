package com.dvlcube.game;

import sun.misc.Perf;

public class StopWatch {
	private final Perf hiResTimer;
	private final long freq;
	private long startTime;

	public StopWatch() {
		hiResTimer = Perf.getPerf();
		freq = hiResTimer.highResFrequency();
	}

	public void start() {
		startTime = hiResTimer.highResCounter();
	}

	public long stop()
	// return the elapsed time in nanoseconds
	{
		return (hiResTimer.highResCounter() - startTime) * 1000000000L / freq;
	}

	public long getResolution()
	// return counter resolution in nanoseconds
	{
		long diff, count1, count2;

		count1 = hiResTimer.highResCounter();
		count2 = hiResTimer.highResCounter();
		while (count1 == count2)
			count2 = hiResTimer.highResCounter();
		diff = (count2 - count1);

		count1 = hiResTimer.highResCounter();
		count2 = hiResTimer.highResCounter();
		while (count1 == count2)
			count2 = hiResTimer.highResCounter();
		diff += (count2 - count1);

		count1 = hiResTimer.highResCounter();
		count2 = hiResTimer.highResCounter();
		while (count1 == count2)
			count2 = hiResTimer.highResCounter();
		diff += (count2 - count1);

		count1 = hiResTimer.highResCounter();
		count2 = hiResTimer.highResCounter();
		while (count1 == count2)
			count2 = hiResTimer.highResCounter();
		diff += (count2 - count1);

		return (diff * 1000000000L) / (4 * freq);
	} // end of getResolution( )

}