package hr.fer.zemris.java.hw07.shell;

/**
 * This exception is thrown if reading or writing fails in {@link Environment}.
 * 
 * @author nikola
 *
 */
public class ShellIOException extends RuntimeException {

	public ShellIOException() {

	}

	public ShellIOException(String message) {
		super(message);
	}

}
