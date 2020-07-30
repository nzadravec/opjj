package hr.fer.zemris.java.hw16.searchengine;

/**
 * This interface is an apstraction of commands in {@link Console}.
 * 
 * @author nikola
 *
 */
public interface Command {

	/**
	 * Executes command.
	 * 
	 * @param searchEngine
	 *            {@link SearchEngine} object
	 * @param arguments
	 *            string which represents everything that user entered after the
	 *            command name
	 * @return command status
	 */
	CommandStatus executeCommand(SearchEngine searchEngine, String arguments);

}
