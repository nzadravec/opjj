package hr.fer.zemris.java.hw02;

/**
 * Bačen da ukaže da su dijelila dva broja, no da je djelitelj bio nula.
 * 
 * @author nikola
 *
 */
public class DivideByZeroException extends ArithmeticException {

	private String message;
	
	public DivideByZeroException() {
		
	}

	public DivideByZeroException(String message) {
		this.message = message;
	}

	public String toString() {
		return (message);
	}

}
