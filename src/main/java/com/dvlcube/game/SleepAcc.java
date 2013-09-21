package com.dvlcube.game;

import java.text.DecimalFormat;

import com.sun.j3d.utils.timer.J3DTimer;

public class SleepAcc {
	private static DecimalFormat df;

	public static void main(String args[]) {
		df = new DecimalFormat("0.##"); // 2 dp

		// test various sleep values
		sleepTest(1000);
		sleepTest(500);
		sleepTest(200);
		sleepTest(100);
		sleepTest(50);
		sleepTest(20);
		sleepTest(10);
		sleepTest(5);
		sleepTest(1);
	} // end of main( )

	private static void sleepTest(int delay) {
		long timeStart = J3DTimer.getValue();

		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}

		double timeDiff = ((double) (J3DTimer.getValue() - timeStart)) / (1000000L);
		double err = ((delay - timeDiff) / timeDiff) * 100;

		System.out.println("Slept: " + delay + " ms\tJ3D: "
				+ df.format(timeDiff) + " ms\terr: " + df.format(err) + " %");
	}
}