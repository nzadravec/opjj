package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Color provider interface.
 * 
 * @author nikola
 *
 */
public interface IColorProvider {
	/**
	 * Gets current color.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds {@link IColorProvider} listener.
	 * 
	 * @param l {@link ColorChangeListener} object
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes {@link IColorProvider} listener.
	 * 
	 * @param l {@link ColorChangeListener} object
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
