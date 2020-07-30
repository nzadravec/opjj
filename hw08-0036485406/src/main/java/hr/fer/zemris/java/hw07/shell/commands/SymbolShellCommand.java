package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellSymbol;

/**
 * Represents symbol command. It's used for writing and changing shells symbols.
 * 
 * @author nikola
 *
 */
public class SymbolShellCommand extends AbstractShellCommand {

	{
		commandName = "symbol";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 1, 2);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		ShellSymbol symbolName;
		try {
			symbolName = ShellSymbol.valueOf(args.get(0));
		} catch (IllegalArgumentException e) {
			env.writeln(args.get(0) + " is not valid symbol name.");
			return ShellStatus.CONTINUE;
		}

		if (args.size() == 1) {
			Character symbol = null;
			switch (symbolName) {
			case PROMPT:
				symbol = env.getPromptSymbol();
				break;

			case MORELINES:
				symbol = env.getMorelinesSymbol();
				break;

			case MULTILINE:
				symbol = env.getMultilineSymbol();
				break;
			}
			env.writeln("Symbol for " + symbolName + " is '" + symbol + "'");

		} else {
			Character newSymbol = Character.valueOf(args.get(1).charAt(0));
			Character oldSymbol = null;
			switch (symbolName) {
			case PROMPT:
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(newSymbol);
				break;

			case MORELINES:
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(newSymbol);
				break;

			case MULTILINE:
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(newSymbol);
				break;
			}
			env.writeln("Symbol for " + symbolName + " changed from " + oldSymbol + " to " + newSymbol);
		}

		return ShellStatus.CONTINUE;
	}

}
