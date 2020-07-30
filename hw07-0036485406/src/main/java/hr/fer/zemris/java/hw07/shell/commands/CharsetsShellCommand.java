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

		/*
		 * Command charsets takes no arguments and lists names of supported charsets. A
		 * single charset name is written per line.
		 */
		commandDescription.add("Command charsets takes no arguments and lists names of supported charsets. A");
		commandDescription.add("single charset name is written per line.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Util.noArgs(arguments);
		} catch (IllegalArgumentException ignorable) {
		}

		env.writeln("Supported charsets:");
		Charset.availableCharsets().forEach((k, v) -> env.writeln(k));

		return ShellStatus.CONTINUE;
	}

}
