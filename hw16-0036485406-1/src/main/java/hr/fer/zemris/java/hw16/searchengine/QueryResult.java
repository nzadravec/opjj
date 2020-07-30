package hr.fer.zemris.java.hw16.searchengine;

import java.util.List;
import java.util.Objects;

/**
 * Represents result of given query to {@link SearchEngine}. It contains words
 * used for searching and at most ten {@link DocumentVector} that are the most
 * simular to given query.
 * 
 * @author nikola
 *
 */
public class QueryResult {

	/**
	 * Words used from query input
	 */
	private List<String> usedWords;

	/**
	 * Ten best documents (the most similar to given query according to the term
	 * frequency-inverse document frequency criterion), or less if documents with
	 * similarity <code>0</code> appear beforehand.
	 */
	private List<Pair<DocumentVector, Double>> tenBestResults;

	/**
	 * Constructor.
	 * 
	 * @param usedWords
	 *            used words
	 * @param tenBestResults
	 *            ten best results
	 */
	public QueryResult(List<String> usedWords, List<Pair<DocumentVector, Double>> tenBestResults) {
		Objects.requireNonNull(usedWords, "usedWords must not be null");
		Objects.requireNonNull(tenBestResults, "tenBestResults must not be null");

		this.usedWords = usedWords;
		this.tenBestResults = tenBestResults;
	}

	/**
	 * Gets used words.
	 * 
	 * @return used words
	 */
	public List<String> getUsedWords() {
		return usedWords;
	}

	/**
	 * Gets ten best results.
	 * 
	 * @return ten best results
	 */
	public List<Pair<DocumentVector, Double>> getTenBestResults() {
		return tenBestResults;
	}

}
