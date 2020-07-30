package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.SmartScriptException;

/**
 * Bačen da ukaže da se tijekom sintaksne analize izvornog teksta dokumenta
 * pojavila greška, tj. da tekst dokumenta nije dobro napisan.
 * 
 * @author nikola
 *
 */
public class SmartScriptParserException extends SmartScriptException {

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {
		
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
	
}
