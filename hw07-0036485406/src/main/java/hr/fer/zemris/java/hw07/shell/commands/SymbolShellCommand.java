package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellSymbol;

/**
 * Represents symbol command. It's used for writing and changing shells
 * symbols.
 * 
 * @author nikola
 *
 */
public class SymbolShellCommand extends AbstractShellCommand {

	{
		commandName = "mkdir";

		/*
		 * Command symbol changes shells symbols. First argument represents symbol name
		 * and second argument represents new symbol. If command is called with only
		 * first argument, it prints current symbol.
		 * 
		 * Shell symbols:
		 * 
		 * PROMPTSYMBOL - symbol on a display screen indicating that the shell is
		 * waiting for input
		 * 
		 * MORELINESSYMBOL - symbol that is used to inform the shell that more lines as
		 * expected
		 * 
		 * MULTILINESYMBOL - shell writes this symbol at the beginning followed by a
		 * single whitespace for each line that is part of multi-line command (except
		 * for the first one)
		 */
		commandDescription.add("Command symbol changes shells symbols. First argument represents symbol name");
		commandDescription.add("and second argument represents new symbol. If command is called with only");
		commandDescription.add("first argument, it prints current symbol.");
		commandDescription.add("Shell symbols:");
		commandDescription.add("PROMPTSYMBOL - symbol on a display screen indicating that the shell is");
		commandDescription.add("waiting for input");
		commandDescription.add("MORELINESSYMBOL - symbol that is used to inform the shell that more lines as");
		commandDescription.add("expected");
		commandDescription.add("MULTILINESYMBOL - shell writes this symbol at the beginning followed by a");
		commandDescription.add("single whitespace for each line that is part of multi-line command (except");
		commandDescription.add("for the first one)");
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
