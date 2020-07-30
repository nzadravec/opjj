package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents pwd command. Prints to the terminal the current directory as
 * written in {@link Environment}.
 * 
 * @author nikola
 *
 */
public class PwdShellCommand extends AbstractShellCommand {

	{
		commandName = "pwd";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Util.noArgs(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		env.writeln(env.getCurrentDirectory().toString());

		return ShellStatus.CONTINUE;
	}

}
