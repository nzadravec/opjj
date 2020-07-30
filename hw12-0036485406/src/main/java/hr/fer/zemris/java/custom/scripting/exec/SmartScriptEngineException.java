package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.SmartScriptException;

/**
 * Used to indicate that some exception happen in {@link SmartScriptEngine}.
 * 
 * @author nikola
 *
 */
public class SmartScriptEngineException extends SmartScriptException {
	
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public SmartScriptEngineException() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message exception message
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}

}
