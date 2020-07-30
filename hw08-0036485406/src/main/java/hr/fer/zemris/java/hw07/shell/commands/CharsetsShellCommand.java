package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents charsets command. It's used for lists names of supported charsets.
 * 
 * @author nikola
 *
 */
public class CharsetsShellCommand extends AbstractShellCommand {

	{
		commandName = "charsets";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Util.noArgs(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		env.writeln("Supported charsets:");
		Charset.availableCharsets().forEach((k, v) -> env.writeln(k));

		return ShellStatus.CONTINUE;
	}

}
