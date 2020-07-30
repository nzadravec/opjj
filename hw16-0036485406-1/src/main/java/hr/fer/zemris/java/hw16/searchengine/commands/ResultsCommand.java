package hr.fer.zemris.java.hw16.searchengine.commands;

import java.util.List;

import hr.fer.zemris.java.hw16.searchengine.Command;
import hr.fer.zemris.java.hw16.searchengine.CommandStatus;
import hr.fer.zemris.java.hw16.searchengine.DocumentVector;
import hr.fer.zemris.java.hw16.searchengine.Pair;
import hr.fer.zemris.java.hw16.searchengine.QueryResult;
import hr.fer.zemris.java.hw16.searchengine.SearchEngine;

/**
 * It returns to the screen a list of results found by the last-executed "query"
 * command.
 * 
 * @author nikola
 *
 */
public class ResultsCommand implements Command {

	@Override
	public CommandStatus executeCommand(SearchEngine searchEngine, String arguments) {
		List<String> argList = Util.splitArgs(arguments);
		if (!argList.isEmpty()) {
			System.out.println("Očekivano 0 argumenata.");
			return CommandStatus.CONTINUE;
		}

		QueryResult result = searchEngine.getResult();
		if (result == null) {
			System.out.println("Naredba se može izvršiti tek nakon što je zadan neki upit.");
			return CommandStatus.CONTINUE;
		}

		int index = 0;
		for (Pair<DocumentVector, Double> pair : result.getTenBestResults()) {
			System.out.println(String.format("[%2d] (%.4f) ", index, pair.getSecond())
					+ pair.getFirst().getDocumentPath().toString());
			index++;
		}

		return CommandStatus.CONTINUE;
	}

}
