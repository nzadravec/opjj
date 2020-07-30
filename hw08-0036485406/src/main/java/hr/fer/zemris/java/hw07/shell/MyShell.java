package hr.fer.zemris.java.hw07.shell;

import java.util.Scanner;
import java.util.SortedMap;

/**
 * This class represents command-line program. It's like command prompt in
 * Windows or terminal in Linux.
 * 
 * To list built-in commands use <code>help</code> command. To get description
 * of specific command use <code>help <specific_command></code>.
 * 
 * The command can span across multiple lines. However, each line that is not
 * the last line of command must end with a special symbol that is used to
 * inform the shell that more lines as expected. This special symbol is called
 * MORELINESSYMBOL. User is able to change this symbol using <code>symbol</code>
 * command. There are two additional special symbols, look more with
 * <code>help symbol</code>.
 * 
 * @author nikola
 *
 */
public class MyShell {

	/**
	 * Main function.
	 * 
	 * @param args
	 *            arguments from command line, not used here
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0.");

		Environment environment = new EnvironmentImpl();
		SortedMap<String, ShellCommand> commands = environment.commands();

		Scanner sc = new Scanner(System.in);
		ShellStatus status = null;
		do {
			try {
				environment.write(environment.getPromptSymbol() + " ");
				String l = readLineOrLines(sc, environment);
				if (l.length() == 0) {
					environment.writeln("Missing input.");
					continue;
				}
				String commandName = extractCommandName(l);
				String arguments = l.substring(commandName.length());
				ShellCommand command = commands.get(commandName);
				if (command == null) {
					environment.writeln(commandName + ": command not found.");
					continue;
				}
				status = command.executeCommand(environment, arguments);
			} catch (ShellIOException e) {
				System.out.println("No communication is possible with the user. Terminating shell.");
				break;
			}
		} while (status != ShellStatus.TERMINATE);

		sc.close();
	}

	/**
	 * Returns single string which represents command name.
	 * 
	 * @param l
	 *            single string which represents everything that user entered
	 * @return single string which represents command name
	 */
	private static String extractCommandName(String l) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < l.length(); i++) {
			char c = l.charAt(i);
			if (Character.isWhitespace(c)) {
				break;
			}
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Returns single string which represents everything that user entered to run
	 * single command. If command spans across multiple lines it concatenates all
	 * lines into a single line without MORELINES symbol.
	 * 
	 * @param sc
	 *            object for getting user input
	 * @param env
	 *            environment object
	 * @return single string which represents everything that user entered
	 */
	private static String readLineOrLines(Scanner sc, Environment env) {
		StringBuilder sb = new StringBuilder();
		while (true) {
			String s = sc.nextLine();
			if (!s.endsWith(env.getMorelinesSymbol().toString())) {
				sb.append(s);
				break;
			}
			sb.append(s.substring(0, s.length() - 1));
			env.write(env.getMultilineSymbol() + " ");
		}

		return sb.toString();
	}

}
