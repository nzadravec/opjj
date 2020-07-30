package hr.fer.zemris.java.gui.charts;

/**
 * Represents 2D data.
 * 
 * @author nikola
 *
 */
public class XYValue {
	
	/**
	 * Component x of 2D data
	 */
	private int x;
	/**
	 * Component y of 2D data
	 */
	private int y;
	
	/**
	 * Creates arbitrary 2D data.
	 * 
	 * @param x component x of 2D data
	 * @param y component y of 2D data
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns component x of 2D data
	 * 
	 * @return x component
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns component y of 2D data
	 * 
	 * @return y component
	 */
	public int getY() {
		return y;
	}

}
