package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.SmartScriptException;

/**
 * Bačen da ukaže da se tijekom leksične analize izvornog teksta dokumenta
 * pojavila greška, tj. da tekst dokumenta nije dobro napisan.
 * 
 * @author nikola
 *
 */
public class SmartScriptLexerException extends SmartScriptException {
	
	private static final long serialVersionUID = 1L;

	public SmartScriptLexerException() {
	}
	
	public SmartScriptLexerException(String message) {
		super(message);
	}

}
