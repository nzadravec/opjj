package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.ShellCommand;

/**
 * Abstract class of {@link ShellCommand}. It's used to implement
 * {@link #getCommandName} and {@link #getCommandDescription} methods. Classes
 * that extend this class must define name and description of the command it
 * represents using <code>commandName</code> and <code>commandDescription</code>
 * member variables of this class.
 * 
 * @author nikola
 *
 */
public abstract class AbstractShellCommand implements ShellCommand {

	/**
	 * 
	 */
	String commandName;

	List<String> commandDescription = new ArrayList<>();

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
