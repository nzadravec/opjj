package hr.fer.zemris.java.hw16.searchengine.commands;

import java.util.List;

import hr.fer.zemris.java.hw16.searchengine.Command;
import hr.fer.zemris.java.hw16.searchengine.CommandStatus;
import hr.fer.zemris.java.hw16.searchengine.DocumentVector;
import hr.fer.zemris.java.hw16.searchengine.Pair;
import hr.fer.zemris.java.hw16.searchengine.QueryResult;
import hr.fer.zemris.java.hw16.searchengine.SearchEngine;

/**
 * Performs a search. From the received query, it deletes all the words that are
 * not in the vocabulary built on the base document analysis. What's left of the
 * word is printed on the screen. Then it searches using {@link SearchEngine}.
 * Prints the 10 best results (the most similar query documents according to the
 * TF-IDF criterion), or less if similarity documents 0 appear. The printed list
 * is ranked similarly. Each row has a row number, a similarity (to four decimal
 * places), and a path to the document.
 * 
 * @author nikola
 *
 */
public class QueryCommand implements Command {

	@Override
	public CommandStatus executeCommand(SearchEngine searchEngine, String arguments) {
		List<String> words = Util.splitArgs(arguments);

		if (words.isEmpty()) {
			System.out.println("Fale riječi upita.");
			return CommandStatus.CONTINUE;
		}

		searchEngine.query(words);
		QueryResult result = searchEngine.getResult();

		System.out.println("Query is: " + result.getUsedWords().toString());
		List<Pair<DocumentVector, Double>> tenBestResults = result.getTenBestResults();

		if (tenBestResults.isEmpty()) {
			System.out.println("Svi dokumenti imaju sličnost 0 za dani upit.");
			return CommandStatus.CONTINUE;
		}

		System.out.println("Najboljih 10 rezultata:");
		int index = 0;
		for (Pair<DocumentVector, Double> pair : tenBestResults) {
			System.out.println(String.format("[%2d] (%.4f) ", index, pair.getSecond())
					+ pair.getFirst().getDocumentPath().toString());
			index++;
		}

		return CommandStatus.CONTINUE;
	}

}
