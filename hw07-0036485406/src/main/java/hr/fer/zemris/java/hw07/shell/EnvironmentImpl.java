package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Implementation of interface {@link Environment}.
 * 
 * @author nikola
 *
 */
public class EnvironmentImpl implements Environment {
	
	/**
	 * Object for reading user input.
	 */
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Object for writing response to user.
	 */
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	/**
	 * Map of command name to {@link ShellCommand} object.
	 */
	private static SortedMap<String, ShellCommand> commands;
	static {
		commands = new TreeMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
	}
	
	/**
	 * Default values for prompt symbol, moreLines symbol and multiLine symbol.
	 */
	private static final Character DEFAULT_PROMPTSYMBOL = '>';
	private static final Character DEFAULT_MORELINESSYMBOL = '\\';
	private static final Character DEFAULT_MULTILINESYMBOL = '|';
	
	/**
	 * Prompt symbol, moreLines symbol and multiLine symbol objects.
	 */
	private static Character promptSymbol = DEFAULT_PROMPTSYMBOL;
	private static Character moreLinesSymbol = DEFAULT_MORELINESSYMBOL;
	private static Character multiLineSymbol = DEFAULT_MULTILINESYMBOL;

	@Override
	public String readLine() throws ShellIOException {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

}
