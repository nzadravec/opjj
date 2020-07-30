package hr.fer.zemris.java.hw16.jvdraw;

/**
 * {@link GeometricalObject} listener.
 * 
 * @author nikola
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Method called by {@link GeometricalObject} object when it's changed.
	 * 
	 * @param o {@link GeometricalObject} object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
