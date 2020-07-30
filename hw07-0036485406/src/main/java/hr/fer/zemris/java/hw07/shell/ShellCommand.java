package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * This interface is an apstraction of commands in {@link MyShell}.
 * 
 * @author nikola
 *
 */
public interface ShellCommand {

	/**
	 * Executes command.
	 * 
	 * @param env
	 *            environment object
	 * @param arguments
	 *            string which represents everything that user entered after the
	 *            command name without MORELINES symbol
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	String getCommandName();

	List<String> getCommandDescription();

}
