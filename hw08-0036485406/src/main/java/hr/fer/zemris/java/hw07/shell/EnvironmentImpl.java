package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.massrename.MassrenameShellCommand;

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
		
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("rmtree", new RmtreeShellCommand());
		commands.put("cptree", new CptreeShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
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
	private Character promptSymbol = DEFAULT_PROMPTSYMBOL;
	private Character moreLinesSymbol = DEFAULT_MORELINESSYMBOL;
	private Character multiLineSymbol = DEFAULT_MULTILINESYMBOL;

	/**
	 * Default values for current directory.
	 */
	private static final Path DEFAULT_CURRENT_DIRECTORY = Paths.get(".").toAbsolutePath().normalize();
	
	/**
	 * Current directory.
	 */
	private Path currentDirectory = DEFAULT_CURRENT_DIRECTORY;

	/**
	 * Map of string keys to shared data.
	 */
	private Map<String, Object> sharedDataMap = new HashMap<>();

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

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		path = path.toAbsolutePath().normalize();
		if(!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Given directory path doesn't exists.");
		}
		
		this.currentDirectory = path;
	}

	@Override
	public Object getSharedData(String key) {
		return sharedDataMap.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedDataMap.put(key, value);
	}

}
