package hr.fer.zemris.java.hw16.searchengine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Search engine interface.
 * 
 * @author nikola
 *
 */
public interface SearchEngine {

	/**
	 * Searches this directory and analyzes all documents found in that directory
	 * (and any subdirectory).
	 * 
	 * @param documentsPath root directory
	 * @throws IOException if exception occurs while reading documents
	 */
	void processTextDatabase(Path documentsPath) throws IOException;

	/**
	 * Returns number of words in vocabulary.
	 * 
	 * @return number of words in vocabulary
	 */
	int numberOfWords();

	/**
	 * Performs search. From the received query, it deletes all the words that are
	 * not in the vocabulary built by method {@link #processTextDatabase(Path)}}. It
	 * treats remained words as {@link DocumentVector} and searches for top ten most
	 * similar documents to it. To obtain search result call
	 * {@link #getResult(Path)}} after calling this method.
	 * 
	 * @param words
	 */
	void query(List<String> words);

	/**
	 * Returns result from last query. Returns <code>null</code> if
	 * {@link #query(Path)}} wasn't called before.
	 * 
	 * @return last query result
	 */
	QueryResult getResult();

}
