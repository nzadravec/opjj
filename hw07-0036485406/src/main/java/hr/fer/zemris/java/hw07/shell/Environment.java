package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;

/**
 * This is an abstraction which will be passed to each defined
 * {@link ShellCommand}. The each implemented command communicates with user
 * (reads user input and writes response) only through this interface.
 * 
 * @author nikola
 *
 */
public interface Environment {

	/**
	 * Reads user input.
	 * 
	 * @return user input
	 * @throws ShellIOException
	 *             if reading fails
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes response to user.
	 * 
	 * @param text
	 *            response to user
	 * @throws ShellIOException
	 *             if writing fails
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes response to user and adds new line.
	 * 
	 * @param text
	 *            response to user
	 * @throws ShellIOException
	 *             if writing fails
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns unmodifiable map of command name to {@link ShellCommand} object.
	 * 
	 * @return unmodifiable map
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns multiline symbol.
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol.
	 * 
	 * @return multiline symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns morelines symbol.
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol.
	 * 
	 */
	void setMorelinesSymbol(Character symbol);

}
