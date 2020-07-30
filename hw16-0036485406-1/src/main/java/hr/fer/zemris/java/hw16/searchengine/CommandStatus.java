package hr.fer.zemris.java.hw16.searchengine;

/**
 * Represents possible command status after performing executeCommand method on
 * {@link Command} object that {@link Console} program uses to determine should
 * program stop or not.
 * 
 * @author nikola
 *
 */
public enum CommandStatus {
	/**
	 * Program continues
	 */
	CONTINUE, 
	/**
	 * Program stops
	 */
	TERMINATE
}
