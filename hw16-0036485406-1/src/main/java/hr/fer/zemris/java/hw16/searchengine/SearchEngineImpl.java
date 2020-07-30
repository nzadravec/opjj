package hr.fer.zemris.java.hw16.searchengine;

import static java.lang.Math.log10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * {@link SearchEngine} implementation.
 * 
 * @author nikola
 *
 */
public class SearchEngineImpl implements SearchEngine {

	/**
	 * Semantically poor words such as nouns, suggestions, conjugates, auxiliary
	 * verbs, particles, etc.
	 */
	private Set<String> stopwords;

	/**
	 * Vocabulary
	 */
	private Vocabulary vocabulary;
	/**
	 * List of {@link DocumentVector}
	 */
	private List<DocumentVector> documentVectors;
	/**
	 * Auxiliary vector that stores idf value of certain word at index in which it
	 * was saved in the {@link Vocabulary}
	 */
	private NDVector idfVector;

	/**
	 * Result from last query
	 */
	private QueryResult queryResult;

	/**
	 * Constructor.
	 * 
	 * @param stopwords
	 *            stopwords
	 */
	public SearchEngineImpl(Set<String> stopwords) {
		Objects.requireNonNull(stopwords, "stopwords object must not be null");
		this.stopwords = stopwords;
	}

	@Override
	public void processTextDatabase(Path rootPath) throws IOException {
		if (!Files.isDirectory(rootPath)) {
			throw new IllegalArgumentException("given path " + rootPath.toAbsolutePath() + " is not path to directory");
		}

		vocabulary = new VocabularyImpl();
		documentVectors = new ArrayList<>();

		Files.walkFileTree(rootPath, new VocabularyBuilder());
		Files.walkFileTree(rootPath, new DocumentVectorsTermFrequencyCalculator());

		idfVector = new NDVector(vocabulary.numberOfWords());
		int numberOfDocuments = documentVectors.size();
		for (String word : vocabulary) {
			int index = vocabulary.getIndexOf(word);
			int numberOfDocumentsWithWord = 0;
			for (DocumentVector docVector : documentVectors) {
				if (docVector.getVector().get(index) > 0.0) {
					numberOfDocumentsWithWord++;
				}
			}
			idfVector.set(index, log10(numberOfDocuments / (double) numberOfDocumentsWithWord));
		}

		for (DocumentVector docVector : documentVectors) {
			docVector.getVector().multiply(idfVector);
		}
	}

	/**
	 * Reads words from document and fills {@link Vocabulary}. If some word is
	 * stopword, it passes it.
	 * 
	 * @author nikola
	 *
	 */
	private class VocabularyBuilder extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Objects.requireNonNull(file);

			String document = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
			StringBuilder wordBuilder = new StringBuilder();
			for (int i = 0; i < document.length(); i++) {
				char c = document.charAt(i);
				if (Character.isAlphabetic(c)) {
					wordBuilder.append(c);
				} else {
					String word = wordBuilder.toString().toLowerCase();
					wordBuilder = new StringBuilder();
					if (!stopwords.contains(word)) {
						vocabulary.addWord(word);
					}
				}
			}

			if (wordBuilder.length() != 0) {
				String word = wordBuilder.toString().toLowerCase();
				if (!stopwords.contains(word)) {
					vocabulary.addWord(word);
				}
			}

			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Builds {@link DocumentVector} for each document. It only store term frequency
	 * value for each word.
	 * 
	 * @author nikola
	 *
	 */
	private class DocumentVectorsTermFrequencyCalculator extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Objects.requireNonNull(file);

			DocumentVector documentVector = new DocumentVector(file, vocabulary.numberOfWords());
			documentVectors.add(documentVector);

			NDVector vector = documentVector.getVector();

			String document = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
			StringBuilder wordBuilder = new StringBuilder();
			for (int i = 0; i < document.length(); i++) {
				char c = document.charAt(i);
				if (Character.isAlphabetic(c)) {
					wordBuilder.append(c);
				} else {
					String word = wordBuilder.toString().toLowerCase();
					wordBuilder = new StringBuilder();
					int index = vocabulary.getIndexOf(word);
					if (index != -1) {
						vector.set(index, vector.get(index) + 1);
					}
				}
			}

			if (wordBuilder.length() != 0) {
				String word = wordBuilder.toString().toLowerCase();
				int index = vocabulary.getIndexOf(word);
				if (index != -1) {
					vector.set(index, vector.get(index) + 1);
				}
			}

			return FileVisitResult.CONTINUE;
		}

	}

	@Override
	public int numberOfWords() {
		return vocabulary.numberOfWords();
	}

	@Override
	public void query(List<String> words) {
		List<String> usedWords = new ArrayList<>();
		NDVector queryVector = new NDVector(vocabulary.numberOfWords());
		for (String word : words) {
			word = word.toLowerCase();
			int index = vocabulary.getIndexOf(word);
			if (index != -1) {
				usedWords.add(word);
				queryVector.set(index, queryVector.get(index) + 1);
			}
		}

		queryVector.multiply(idfVector);
		TreeSet<Pair<DocumentVector, Double>> best10results = new TreeSet<>(new DocumentVectorComparator());
		int best10 = 10;
		for (DocumentVector documentVector : documentVectors) {

			double similarity = documentVector.getVector().cosAngle(queryVector);
			if (similarity == 0.0) {
				continue;
			}

			if (best10results.size() < best10) {
				best10results.add(new Pair<>(documentVector, similarity));
			} else {
				Pair<DocumentVector, Double> first = best10results.first();
				if (first.getSecond() < similarity) {
					best10results.pollFirst();
					best10results.add(new Pair<>(documentVector, similarity));
				}
			}
		}

		List<Pair<DocumentVector, Double>> resultList = new ArrayList<>(best10results);
		Collections.reverse(resultList);

		queryResult = new QueryResult(usedWords, resultList);
	}

	/**
	 * Compares two {@link DocumentVector} considering how similar are each to given
	 * query. Similarity value is stored as second element in {@link Pair} object.
	 * 
	 * @author nikola
	 *
	 */
	private static class DocumentVectorComparator implements Comparator<Pair<DocumentVector, Double>> {
		@Override
		public int compare(Pair<DocumentVector, Double> p1, Pair<DocumentVector, Double> p2) {
			return Double.compare(p1.getSecond(), p2.getSecond());
		}
	}

	@Override
	public QueryResult getResult() {
		return queryResult;
	}

}
