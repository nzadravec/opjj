package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Bačen da ukaže da se tijekom leksične analize teksta upita
 * pojavila greška, tj. da tekst upita nije dobro napisan.
 * 
 * @author nikola
 *
 */
public class LexerException extends RuntimeException {

	public LexerException() {
		
	}
	
	public LexerException(String message) {
		super(message);
	}

}
