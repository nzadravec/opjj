package hr.fer.zemris.java.gui.layouts;

/**
 * Represents exception which is thrown in {@link CalcLayout} when
 * user trys to add component at unauthorized positions.
 * 
 * @author nikola
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CalcLayoutException() {
		super();
	}

	public CalcLayoutException(String message) {
		super(message);
	}

}
