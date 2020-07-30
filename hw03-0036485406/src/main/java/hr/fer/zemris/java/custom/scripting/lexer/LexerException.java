package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Bačen da ukaže da se tijekom leksične analize izvornog teksta dokumenta
 * pojavila greška, tj. da tekst dokumenta nije dobro napisan.
 * 
 * @author nikola
 *
 */
public class LexerException extends RuntimeException {

	public LexerException(String message) {
		super(message);
	}

}
