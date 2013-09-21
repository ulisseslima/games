package com.dvlcube.game;

import com.sun.j3d.utils.timer.J3DTimer;

public class TimerRes {
	public static void main(String args[]) {
		j3dTimeResolution();
		sysTimeResolution();
		perfTimeResolution();
	}

	private static void j3dTimeResolution() {
		System.out.println("Java 3D Timer Resolution: "
				+ J3DTimer.getResolution() + " nsecs");
	}

	private static void sysTimeResolution() {
		long total, count1, count2;

		count1 = System.currentTimeMillis();
		count2 = System.currentTimeMillis();
		while (count1 == count2)
			count2 = System.currentTimeMillis();
		total = 1000L * (count2 - count1);

		count1 = System.currentTimeMillis();
		count2 = System.currentTimeMillis();
		while (count1 == count2)
			count2 = System.currentTimeMillis();
		total += 1000L * (count2 - count1);

		count1 = System.currentTimeMillis();
		count2 = System.currentTimeMillis();
		while (count1 == count2)
			count2 = System.currentTimeMillis();
		total += 1000L * (count2 - count1);

		count1 = System.currentTimeMillis();
		count2 = System.currentTimeMillis();
		while (count1 == count2)
			count2 = System.currentTimeMillis();
		total += 1000L * (count2 - count1);

		System.out.println("System Time resolution: " + total / 4
				+ " microsecs");
	}

	private static void perfTimeResolution() {
		StopWatch sw = new StopWatch();
		System.out.println("Perf Resolution: " + sw.getResolution() + " nsecs");

		sw.start();
		long time = sw.stop();
		System.out.println("Perf Time " + time + " nsecs");
	}

}