package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Represents a model capable of holding zero, one or more documents, where each
 * document and having a concept of current document – the one which is shown to
 * the user and on which user works.
 * 
 * @author nikola
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new document.
	 * 
	 * @return new document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Gets current document.
	 * 
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document from <code>path</code>.
	 * 
	 * @param path
	 *            given path
	 * @return document from <code>path</code>
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves specified document. <code>newPath</code> can be <code>null</code> ; if
	 * <code>null</code> , document should be saved using path associated from
	 * document, otherwise, new path should be used and after saving is completed,
	 * document’s path should be updated to <code>newPath</code>.
	 * 
	 * @param model
	 *            specified document
	 * @param newPath
	 *            new path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Removes specified document.
	 * 
	 * @param model
	 *            specified document
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds {@link MultipleDocumentListener}.
	 * 
	 * @param l {@link MultipleDocumentListener}
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes {@link MultipleDocumentListener}.
	 * 
	 * @param l {@link MultipleDocumentListener}
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of documents.
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Gets document at <code>index</code>.
	 * 
	 * @param index index
	 * @return document at <code>index</code>
	 */
	SingleDocumentModel getDocument(int index);
}
