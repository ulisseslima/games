package com.dvlcube.game.cookieclicker;

import static com.dvlcube.gaming.util.Cuber.df;
import static com.dvlcube.gaming.util.Cuber.staticToString;

/**
 * @author wonka
 * @since 29/09/2013
 */
public enum CookieProducer {
	CURSOR, GRANDMA, FARM, FACTORY, MINE, SHIPMENT, ALCHEMIY_LAB, PORTAL, TIME_MACHINE, ANTIMATTER_CONDENSER;
	private long cookies = 0;
	private double productionRate = 1;
	private long multiplier = 0;
	private double price = 15;
	public final String name;

	/**
	 * @author wonka
	 * @since 29/09/2013
	 */
	CookieProducer() {
		this.name = staticToString(name());
	}

	public long getCookies() {
		return cookies;
	}

	public double getProductionRate() {
		return pow(productionRate);
	}

	public long getMultiplier() {
		return multiplier;
	}

	public double getPrice() {
		double o = (ordinal() + 1);
		return Math.pow(price, o) * (multiplier + 1);
	}

	public double pow(double n) {
		double o = (ordinal() + 1);
		return Math.pow(n, o) * multiplier;
	}

	/**
	 * ups the multiplier.
	 * 
	 * @return the cost of adding a new producer.
	 * @author wonka
	 * @since 29/09/2013
	 */
	public double add() {
		double chargePrice = getPrice();
		price += (price * 50) / 100;
		multiplier++;
		productionRate += (pow(productionRate) * (15 + (ordinal()))) / 100;
		return chargePrice;
	}

	public double upgradeCost() {
		return getPrice();
	}

	public long produce() {
		cookies += (int) getProductionRate();
		return cookies;
	}

	@Override
	public String toString() {
		return String.format("%s: %d cookies, %s*%s/s, $%s", name, cookies, df(getProductionRate()), df(multiplier),
				df(getPrice()));
	}

	public static void printlns() {
		for (CookieProducer producer : CookieProducer.values()) {
			System.out.println(producer);
		}
	}

	public static long doProduce() {
		long totalProduced = 0;
		for (CookieProducer producer : CookieProducer.values()) {
			totalProduced += producer.produce();
		}
		return totalProduced;
	}
}