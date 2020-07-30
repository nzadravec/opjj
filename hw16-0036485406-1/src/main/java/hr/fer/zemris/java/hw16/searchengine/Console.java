package hr.fer.zemris.java.hw16.searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.java.hw16.searchengine.commands.ExitCommand;
import hr.fer.zemris.java.hw16.searchengine.commands.QueryCommand;
import hr.fer.zemris.java.hw16.searchengine.commands.ResultsCommand;
import hr.fer.zemris.java.hw16.searchengine.commands.TypeCommand;

/**
 * Main program.
 * 
 * @author nikola
 *
 */
public class Console {

	private static Path stopwordsFile = Paths.get("src/main/resources/hrvatski_stoprijeci.txt");

	private static Map<String, Command> nameToCommandObjMap;
	static {
		nameToCommandObjMap = new HashMap<>();
		nameToCommandObjMap.put("query", new QueryCommand());
		nameToCommandObjMap.put("exit", new ExitCommand());
		nameToCommandObjMap.put("results", new ResultsCommand());
		nameToCommandObjMap.put("type", new TypeCommand());
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command line arguments - used for single argument - path to
	 *            documents
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("program expects single command line argument - path to documents");
			System.exit(1);
		}

		Path rootPath = Paths.get(args[0]);
		if (!Files.isDirectory(rootPath)) {
			System.out.println("given input " + args[0] + " is not path to directory");
			System.exit(1);
		}

		Set<String> stopwords = null;
		try {
			stopwords = readStopWordsFromFile(stopwordsFile);
		} catch (IOException e) {
			System.out.println(
					"IOException occurred while trying to read stopwords file " + stopwordsFile.toAbsolutePath());
			System.exit(1);
		}

		SearchEngine searchEngine = new SearchEngineImpl(stopwords);
		try {
			searchEngine.processTextDatabase(rootPath);
		} catch (IOException e) {
			System.out.println(
					"IOException occurred while trying to process documents from root " + rootPath.toAbsolutePath());
			System.exit(1);
		}

		System.out.println("Veličina riječnika je " + searchEngine.numberOfWords() + " riječi.");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Enter command > ");
			String line = sc.nextLine();
			int indexOfFstSpace = line.indexOf(" ");
			String commandName;
			String commandArgs;
			if (indexOfFstSpace == -1) {
				commandName = line;
				commandArgs = "";
			} else {
				commandName = line.substring(0, indexOfFstSpace);
				commandArgs = line.substring(indexOfFstSpace + 1);
			}

			Command command = nameToCommandObjMap.get(commandName);
			if (command == null) {
				System.out.println("Nepoznata naredba.");
				continue;
			}

			CommandStatus status = command.executeCommand(searchEngine, commandArgs);
			if (status == CommandStatus.TERMINATE) {
				break;
			}
		}

		sc.close();
	}

	private static Set<String> readStopWordsFromFile(Path file) throws IOException {
		Set<String> stopwords = new HashSet<>();

		List<String> lines = Files.readAllLines(file);
		lines.forEach(line -> stopwords.add(line.toLowerCase()));

		return stopwords;
	}

}
