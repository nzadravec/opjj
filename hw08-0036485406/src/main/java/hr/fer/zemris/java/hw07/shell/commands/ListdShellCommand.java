package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents listd command. Command prints to the terminal all the paths that
 * are on the stack starting from the last one added.
 * 
 * @author nikola
 *
 */
public class ListdShellCommand extends AbstractShellCommand {

	{
		commandName = "listd";
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
			env.writeln("There are no stored directories.");
			return ShellStatus.CONTINUE;
		}

		for (int i = pathStack.size() - 1; i >= 0; i--) {
			env.writeln(pathStack.get(i).toString());
		}

		return ShellStatus.CONTINUE;
	}

}
