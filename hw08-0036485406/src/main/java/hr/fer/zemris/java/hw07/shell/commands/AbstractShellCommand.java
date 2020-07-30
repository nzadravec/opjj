package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.ShellCommand;

/**
 * Abstract class of {@link ShellCommand}. It's used to implement
 * {@link #getCommandName} and {@link #getCommandDescription} methods. Classes
 * that extend this class must define name of the command it represents using
 * <code>commandName</code> member variable of this class and add
 * commandName.txt file in package
 * hr.fer.zemris.java.hw07.shell.commands.descriptions with command description
 * in that file.
 * 
 * @author nikola
 *
 */
public abstract class AbstractShellCommand implements ShellCommand {

	private final String descriptionFilesPath = "./src/main/java/hr/fer/zemris/java/hw07/shell/commands/descriptions/";

	/**
	 * Command name.
	 */
	protected String commandName;

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		try {
			String name = commandName.contains(" ") ? commandName.substring(0, commandName.indexOf(" ")) : commandName;
			return Files.readAllLines(Paths.get(descriptionFilesPath + name + ".txt"));
		} catch (IOException e) {
			List<String> l = new ArrayList<>();
			l.add("There's no description for this command.");
			return l;
		}
	}

}
