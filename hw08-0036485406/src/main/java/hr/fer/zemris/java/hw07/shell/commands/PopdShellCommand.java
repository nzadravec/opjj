package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents popd command. Command removes the top path from stack that is in
 * shared data under the key "cdstack" and sets this path as the current
 * directory.
 * 
 * @author nikola
 *
 */
public class PopdShellCommand extends AbstractShellCommand {

	{
		commandName = "popd";
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

		Path dir = pathStack.pop();
		if (!Files.isDirectory(dir)) {
			env.writeln("Directory " + dir + " has been deleted.");
			env.writeln("Current directory is still " + env.getCurrentDirectory() + ".");
			return ShellStatus.CONTINUE;
		}

		env.setCurrentDirectory(dir);
		env.writeln("New current directory is " + dir + ".");

		return ShellStatus.CONTINUE;
	}

}
