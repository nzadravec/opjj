package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents exit command. It's used for exiting the shell.
 * 
 * @author nikola
 *
 */
public class ExitShellCommand extends AbstractShellCommand {
	
	{
		commandName = "exit";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Util.noArgs(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

}
