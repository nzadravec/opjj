package hr.fer.zemris.java.gui.calc;

/**
 * Observers are modeled with the {@link CalcValueListener} interface. Each time
 * any of the model methods causes a change in the current value, the
 * implementation of the model must inform all interested observers about it.
 * 
 * @author nikola
 *
 */
public interface CalcValueListener {

	/**
	 * Method which is called by calculator to notify registered listeners that its
	 * value has changed.
	 * 
	 * @param model
	 */
	void valueChanged(CalcModel model);
}
