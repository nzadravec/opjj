package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This is an abstraction which will be passed to each defined command
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

	/**
	 * Returns current directory. When the program is started, the call of this
	 * method returns an absolute normalized path corresponding to the current
	 * directory of the running java process.
	 * 
	 * @return current directory
	 */
	Path getCurrentDirectory();

	/**
	 * Sets current directory. Allows to use given directory as the shells current
	 * directory, if it exists; Otherwise, attempting to set up such a directory
	 * should cast an exception.
	 * 
	 * @param path
	 *            given directory
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Returns shared data for given key.
	 * 
	 * @param key
	 *            key
	 * @return shared data for given key
	 */
	Object getSharedData(String key);

	/**
	 * Sets shared data for given key.
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            shared data
	 */
	void setSharedData(String key, Object value);

}
