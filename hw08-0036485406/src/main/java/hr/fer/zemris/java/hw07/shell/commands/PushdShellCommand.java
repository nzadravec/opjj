package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents pushd command. Command pushes the current directory on the stack
 * and then as the current directory sets given single argument (PATH). The
 * command in shared data under the key "cdstack" creates a stack and then
 * writes the current directory on the stack before it changes current
 * directory.
 * 
 * @author nikola
 *
 */
public class PushdShellCommand extends AbstractShellCommand {

	{
		commandName = "pushd";
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

		@SuppressWarnings("unchecked")
		Stack<Path> pathStack = (Stack<Path>) env.getSharedData("cdstack");
		if (pathStack == null) {
			pathStack = new Stack<>();
			env.setSharedData("cdstack", pathStack);
		}

		pathStack.push(env.getCurrentDirectory());
		env.setCurrentDirectory(dir);

		return ShellStatus.CONTINUE;
	}

}
