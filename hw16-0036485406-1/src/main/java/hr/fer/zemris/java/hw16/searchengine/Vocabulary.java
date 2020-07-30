package hr.fer.zemris.java.hw16.searchengine;

/**
 * Vocabulary consists of all the words contained in all documents in the
 * collection of documents for which the grouping should be carried out. Every
 * word in vocabulary has it's unique index.
 * 
 * @author nikola
 *
 */
public interface Vocabulary extends Iterable<String> {

	/**
	 * Add given word only if it doesn't already exist in vocabulary.
	 * 
	 * @param word
	 */
	void addWord(String word);

	/**
	 * Gets index of given word or <code>-1</code> if word doesn't exist in
	 * vocabulary.
	 * 
	 * @param word
	 * @return
	 */
	int getIndexOf(String word);

	/**
	 * Returns number of words in vocabulary.
	 * 
	 * @return
	 */
	int numberOfWords();

}
