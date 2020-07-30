package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of {@link SingleDocumentModel} interface.
 * 
 * @author nikola
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private JTextArea textComponent;
	private boolean modified;

	private List<SingleDocumentListener> listeners = new ArrayList<>();

	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		textComponent = new JTextArea(text);
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				notifySingleDocumentListeners(l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				notifySingleDocumentListeners(l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.filePath = path;
		notifySingleDocumentListeners(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifySingleDocumentListeners(l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	private void notifySingleDocumentListeners(Consumer<SingleDocumentListener> consumer) {
		List<SingleDocumentListener> listenersCopy = new ArrayList<>(listeners);
		for (SingleDocumentListener l : listenersCopy) {
			consumer.accept(l);
		}
	}

}
