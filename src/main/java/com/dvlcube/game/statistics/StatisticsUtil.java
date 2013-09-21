package com.dvlcube.game.statistics;

import java.text.DecimalFormat;

import com.sun.j3d.utils.timer.J3DTimer;

public class StatisticsUtil {
	private static long MAX_STATS_INTERVAL = 1000L;
	// record stats every 1 second (roughly)

	private static int NUM_FPS = 10;
	// number of FPS values stored to get an average

	// used for gathering statistics
	private long statsInterval = 0L; // in ms
	private long prevStatsTime;
	private long totalElapsedTime = 0L;

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;

	private final DecimalFormat df = new DecimalFormat("0.##"); // 2 dp
	private final DecimalFormat timedf = new DecimalFormat("0.####"); // 4 dp

	private int period; // period between drawing in ms

	private void reportStats() {
		frameCount++;
		statsInterval += period;

		if (statsInterval >= MAX_STATS_INTERVAL) {
			long timeNow = J3DTimer.getValue();

			long realElapsedTime = timeNow - prevStatsTime;
			// time since last stats collection
			totalElapsedTime += realElapsedTime;

			long sInterval = statsInterval * 1000000L; // ms --> ns
			double timingError = ((double) (realElapsedTime - sInterval))
					/ sInterval * 100.0;

			double actualFPS = 0; // calculate the latest FPS
			if (totalElapsedTime > 0)
				actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
			// store the latest FPS
			fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
			statsCount = statsCount + 1;

			double totalFPS = 0.0; // total the stored FPSs
			for (int i = 0; i < NUM_FPS; i++)
				totalFPS += fpsStore[i];

			if (statsCount < NUM_FPS) // obtain the average FPS
				averageFPS = totalFPS / statsCount;
			else
				averageFPS = totalFPS / NUM_FPS;

			System.out.println(timedf.format((double) statsInterval / 1000)
					+ " "
					+ timedf.format((double) realElapsedTime / 1000000000L)
					+ "s " + df.format(timingError) + "% " + frameCount + "c "
					+ df.format(actualFPS) + " " + df.format(averageFPS)
					+ " afps");

			prevStatsTime = timeNow;
			statsInterval = 0L; // reset
		}
	}
}
