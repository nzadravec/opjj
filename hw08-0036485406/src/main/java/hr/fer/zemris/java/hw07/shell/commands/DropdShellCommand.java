package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents dropd command. Command removes the top directory from stack; the
 * current directory does not change.
 * 
 * @author nikola
 *
 */
public class DropdShellCommand extends AbstractShellCommand {

	{
		commandName = "dropd";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Util.noArgs(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> pathStack = (Stack<Path>) env.getSharedData("cdstack");
		if (pathStack == null || pathStack.isEmpty()) {
			env.writeln("Stack is empty.");
			return ShellStatus.CONTINUE;
		}

		pathStack.pop();

		return ShellStatus.CONTINUE;
	}

}
