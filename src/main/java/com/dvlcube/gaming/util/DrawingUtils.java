package com.dvlcube.gaming.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.dvlcube.gaming.GamePanel;

/**
 * @author wonka
 * @since 08/03/2014
 */
public class DrawingUtils {

	public Graphics2D o;
	public String string;
	private Font font;

	/**
	 * @param g
	 * @author wonka
	 * @since 08/03/2014
	 */
	public DrawingUtils(Graphics2D g) {
		o = g;
	}

	/**
	 * @param g
	 * @param string
	 * @author wonka
	 * @since 08/03/2014
	 */
	public DrawingUtils(Graphics2D g, String string) {
		this(g);
		this.string = string;
	}

	/**
	 * @param g
	 * @param string
	 * @param font
	 * @author wonka
	 * @since 08/03/2014
	 */
	public DrawingUtils(Graphics2D g, String string, Font font) {
		this(g, string);
		this.font = font;
	}

	/**
	 * Writes a string.
	 * 
	 * @param x
	 * @param y
	 * @author wonka
	 * @since 08/03/2014
	 */
	public void write(int x, int y) {
		Font originalFont = null;
		if (font != null) {
			originalFont = o.getFont();
			o.setFont(font);
		}

		Color color = o.getColor();
		o.setColor(GamePanel.BG_COLOR);
		o.drawString(string, x + 1, y + 1);
		o.setColor(GamePanel.FG_COLOR);
		o.drawString(string, x, y);
		o.setColor(color);

		if (originalFont != null) {
			o.setFont(originalFont);
		}
	}
}
