package hr.fer.zemris.java.hw16.searchengine.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw16.searchengine.Command;
import hr.fer.zemris.java.hw16.searchengine.CommandStatus;
import hr.fer.zemris.java.hw16.searchengine.DocumentVector;
import hr.fer.zemris.java.hw16.searchengine.Pair;
import hr.fer.zemris.java.hw16.searchengine.QueryResult;
import hr.fer.zemris.java.hw16.searchengine.SearchEngine;

/**
 * Takes result index and prints the document on the screen. The command makes
 * sense to run only after a query is made. The command prints the full file
 * name and the text stored in the document.
 * 
 * @author nikola
 *
 */
public class TypeCommand implements Command {

	@Override
	public CommandStatus executeCommand(SearchEngine searchEngine, String arguments) {
		List<String> argList = Util.splitArgs(arguments);
		if (argList.size() != 1) {
			System.out.println("Očekivan 1 argument - indeks dokumenta.");
			return CommandStatus.CONTINUE;
		}

		int index;
		try {
			index = Integer.parseInt(arguments);
		} catch (Exception e) {
			System.out.println(arguments + " se ne može parsirati u int.");
			return CommandStatus.CONTINUE;
		}

		QueryResult result = searchEngine.getResult();
		if (result == null) {
			System.out.println("Naredba se može izvršiti tek nakon što je zadan neki upit.");
			return CommandStatus.CONTINUE;
		}

		List<Pair<DocumentVector, Double>> tenBestResults = result.getTenBestResults();
		if (tenBestResults.isEmpty()) {
			System.out.println("Svi dokumenti imaju sličnost 0 za dani upit.");
			return CommandStatus.CONTINUE;
		}

		if (index < 0 || index >= tenBestResults.size()) {
			System.out.println("Valjani indeks je između 0 i " + (tenBestResults.size() - 1) + ".");
			return CommandStatus.CONTINUE;
		}

		Path documentPath = result.getTenBestResults().get(index).getFirst().getDocumentPath();
		List<String> lines;
		try {
			lines = Files.readAllLines(documentPath);
		} catch (IOException e) {
			System.out.println("Greška se dogodila tijekom čitanja dokumenta '" + documentPath + "'.");
			return CommandStatus.CONTINUE;
		}

		System.out.println("Dokument: " + documentPath);
		System.out.println("----------------------------------------------------------------");
		lines.forEach(l -> System.out.println(l));
		System.out.println("----------------------------------------------------------------");

		return CommandStatus.CONTINUE;
	}

}
