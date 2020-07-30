package hr.fer.zemris.java.hw16.searchengine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * {@link Vocabulary} implementation.
 * 
 * @author nikola
 *
 */
public class VocabularyImpl implements Vocabulary {
	
	/**
	 * Word to its unique index map
	 */
	private Map<String, Integer> wordToIndexMap = new HashMap<>();
	/**
	 * Number of words in vocabulary
	 */
	private int numberOfWords;
	
	@Override
	public void addWord(String word) {
		Objects.requireNonNull(word, "word object must not be null");
		if(word.isEmpty()) {
			throw new IllegalArgumentException("word object must not be empty");
		}
		
		if(!wordToIndexMap.containsKey(word)) {
			wordToIndexMap.put(word, numberOfWords);
			numberOfWords++;
		}
	}
	
	@Override
	public int getIndexOf(String word) {
		Integer index = wordToIndexMap.get(word);
		if(index != null) {
			return index;
		} else {
			return -1;
		}
	}

	@Override
	public int numberOfWords() {
		return numberOfWords;
	}

	@Override
	public Iterator<String> iterator() {
		return wordToIndexMap.keySet().iterator();
	}
		
}
