package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Inherited class from {@link java.awt.Color} with few added method.
 * 
 * @author nikola
 *
 */
public class Color extends java.awt.Color {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param c color
	 */
	public Color(java.awt.Color c) {
		this(c.getRed(), c.getGreen(), c.getBlue());
	}

	/**
	 * Constructor.
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public Color(int r, int g, int b) {
		super(r, g, b);
	}
	
	@Override
	public String toString() {
		return getRed() + "," + getGreen() + "," + getBlue();
	}
	
	/**
	 * Returns color in hex format.
	 * 
	 * @return color hex format
	 */
	public String toHex() {
		return String.format("#%02x%02x%02x", getRed(), getGreen(), getBlue());
	}
	
	/**
	 * Parses color from given string.
	 * 
	 * @param s given string
	 * @return {@link Color} object
	 */
	public static Color parseColor(String s) {
		String[] rgb = s.split(",");
		if(rgb.length != 3) {
			throw new IllegalArgumentException(s + " is not valid color string");
		}
		
		int r = Integer.parseInt(rgb[0]);
		int g = Integer.parseInt(rgb[1]);
		int b = Integer.parseInt(rgb[2]);
		
		return new Color(r, g, b);
	}
	
}
