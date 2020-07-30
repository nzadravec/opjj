package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Indicates that exception occured while calling methods of {@link DAO}.
 * 
 * @author nikola
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message exception message
	 * @param cause cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message exception message
	 */
	public DAOException(String message) {
		super(message);
	}
}