package hr.fer.zemris.java.hw16.jvdraw;

/**
 * {@link IColorProvider} listener interface.
 * 
 * @author nikola
 *
 */
public interface ColorChangeListener {
	/**
	 * Method is called by {@link IColorProvider} when color is changed.
	 * 
	 * @param source {@link IColorProvider} object
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
