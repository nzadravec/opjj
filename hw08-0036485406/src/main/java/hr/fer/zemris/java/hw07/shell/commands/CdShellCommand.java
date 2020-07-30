package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents cd command. It's used for taking one argument (PATH): the new
 * current directory and it's written in {@link Environment}.
 * 
 * @author nikola
 *
 */
public class CdShellCommand extends AbstractShellCommand {

	{
		commandName = "cd";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 1);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path dir = Paths.get(args.get(0));
		if (!dir.isAbsolute()) {
			dir = env.getCurrentDirectory().resolve(dir);
		}

		if (!Files.isDirectory(dir)) {
			env.writeln("Command takes directory as argument.");
			return ShellStatus.CONTINUE;
		}

		env.setCurrentDirectory(dir);

		return ShellStatus.CONTINUE;
	}

}
