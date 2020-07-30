package hr.fer.zemris.java.hw16.searchengine;

import java.nio.file.Path;

/**
 * "Bag of words" representation of text documents. {@link NDVector} is used to
 * store information about the words that appear in document. It also contains
 * {@link Path} on disk to the document it represents.
 * 
 * @author nikola
 *
 */
public class DocumentVector {

	/**
	 * Absolute path to the document on the disk
	 */
	private Path documentPath;

	/**
	 * Document vector
	 */
	private NDVector vector;

	/**
	 * Constructor.
	 * 
	 * @param documentPath
	 *            path to the document on the disk
	 * @param n
	 *            number of words in vocabulary
	 */
	public DocumentVector(Path documentPath, int n) {
		this.documentPath = documentPath.toAbsolutePath();
		vector = new NDVector(n);
	}

	/**
	 * Gets document vector.
	 * 
	 * @return document vector
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * Gets document vector.
	 * 
	 * @return document vector
	 */
	public NDVector getVector() {
		return vector;
	}

}
