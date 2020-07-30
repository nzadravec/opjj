package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents cat command. It's used for writing content to given file name to
 * console.
 * 
 * @author nikola
 *
 */
public class CatShellCommand extends AbstractShellCommand {

	{
		commandName = "cat";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 1, 2);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path file = Paths.get(args.get(0));
		if (!file.isAbsolute()) {
			file = env.getCurrentDirectory().resolve(file);
		}
		
		if (!Files.isRegularFile(file)) {
			env.writeln("Command takes file as first argument.");
			return ShellStatus.CONTINUE;
		}

		Charset charset;
		if (args.size() != 2) {
			charset = Charset.defaultCharset();
		} else {
			try {
				charset = Charset.forName(args.get(1));
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
				env.writeln(e.getMessage());
				return ShellStatus.CONTINUE;
			}
		}

		try {
			Files.lines(file, charset).forEach(l -> env.writeln(l));
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

}
