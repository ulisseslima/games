package com.dvlcube.game;

import java.util.concurrent.TimeUnit;

/**
 * @author wonka
 */
public class Time {
	public static void sleep(long n, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
