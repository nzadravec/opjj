package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Bačen da ukaže da se tijekom sintaksne analize izvornog teksta dokumenta
 * pojavila greška, tj. da tekst dokumenta nije dobro napisan.
 * 
 * @author nikola
 *
 */
public class SmartScriptParserException extends RuntimeException {

	public SmartScriptParserException() {
		
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
	
}
