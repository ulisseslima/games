package com.dvlcube.gaming;

/**
 * A function.
 * 
 * @author wonka
 * @since 09/03/2014
 */
public abstract class F {
	/**
	 * @return function result.
	 * @author wonka
	 * @since 09/03/2014
	 */
	public abstract Float x();

	@Override
	public String toString() {
		String string = "";
		if (x() != null)
			string = x().toString();
		return string;
	}
}
