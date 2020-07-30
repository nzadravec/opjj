package hr.fer.zemris.java.custom.collections;

/**
 * Bačen da ukaže da je na stog pozvana metoda pop ili peek, ali da je taj stog
 * prazan.
 * 
 * @author nikola
 *
 */
public class EmptyStackException extends RuntimeException {
	
	public EmptyStackException(String message) {
		super(message);
	}

}
