package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Inherited class from {@link java.awt.Point} with few added method.
 * 
 * @author nikola
 *
 */
public class Point extends java.awt.Point {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Point(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param p point
	 */
	public Point(java.awt.Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	@Override
	public String toString() {
		return x + "," + y;
	}

	/**
	 * Parses point from given string.
	 * 
	 * @param s given string
	 * @return {@link Point} object
	 */
	public static Point parsePoint(String s) {
		String[] xy = s.split(",");
		if(xy.length != 2) {
			throw new IllegalArgumentException(s + " is not valid point string");
		}
		
		int x = Integer.parseInt(xy[0]);
		int y = Integer.parseInt(xy[1]);
		
		return new Point(x, y);
	}
}