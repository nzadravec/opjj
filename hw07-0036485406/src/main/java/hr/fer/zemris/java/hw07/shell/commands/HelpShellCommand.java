package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents help command. It's used for listing available commands and its
 * writing description of specific command to console.
 * 
 * @author nikola
 *
 */
public class HelpShellCommand extends AbstractShellCommand {

	{
		commandName = "help";

		/*
		 * If started with no arguments, it lists all supported commands. If started
		 * with single argument, it prints name and the description of selected command
		 * (or prints appropriate error message if no such command exists).
		 */
		commandDescription.add("If started with no arguments, it lists all supported commands. If started");
		commandDescription.add("with single argument, it prints name and the description of selected command");
		commandDescription.add("(or prints appropriate error message if no such command exists).");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 0, 1);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.size() == 0) {
			env.writeln("Supported commands:");
			env.commands().forEach((k, v) -> env.writeln(k));
		} else {
			String commandName = args.get(0);
			ShellCommand command = env.commands().get(commandName);
			if (command == null) {
				env.writeln("Command " + commandName + " does not exists.");
			} else {
				env.writeln("Command name:");
				env.writeln(command.getCommandName());
				env.writeln("");
				env.writeln("Command description:");
				command.getCommandDescription().forEach(l -> env.writeln(l));
			}
		}

		return ShellStatus.CONTINUE;
	}

}
