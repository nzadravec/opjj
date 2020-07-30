package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Implementation of {@link MultipleDocumentModel} interface.
 * 
 * @author nikola
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	private List<SingleDocumentModel> documents = new ArrayList<>();
	private SingleDocumentModel currentDocument;

	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	private static final Icon GREEN_DISKETTE;
	private static final Icon RED_DISKETTE;

	static {
		/*
		 * Icons made by Freepik from www.flaticon.com
		 */
		GREEN_DISKETTE = loadIcon("icons/small_green_diskette.png");
		RED_DISKETTE = loadIcon("icons/small_red_diskette.png");
	}

	private static Icon loadIcon(String name) {
		InputStream is = DefaultMultipleDocumentModel.class.getResourceAsStream(name);
		if (is == null) {
			// System.err.println("error!");
			// System.exit(1);
			return null;
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
		return new ImageIcon(bytes);
	}

	private SingleDocumentListener currentDocumentListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = documents.indexOf(model);

			setIconAt(index, RED_DISKETTE);
			notifyMultipleDocumentListeners(l -> l.currentDocumentChanged(null, currentDocument));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = documents.indexOf(model);
			Path filePath = model.getFilePath();
			setTitleAt(index, filePath.getFileName().toString());
			setToolTipTextAt(index, filePath.toAbsolutePath().normalize().toString());
			notifyMultipleDocumentListeners(l -> l.currentDocumentChanged(null, currentDocument));
		}
	};

	public DefaultMultipleDocumentModel() {
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previousDocument = currentDocument;
				if (currentDocument != null) {
					currentDocument.removeSingleDocumentListener(currentDocumentListener);
				}
				int index = getSelectedIndex();
				if (index == -1) {
					currentDocument = null;
					return;
				}
				currentDocument = documents.get(getSelectedIndex());
				currentDocument.addSingleDocumentListener(currentDocumentListener);

				currentDocument.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						notifyMultipleDocumentListeners(
								l -> l.currentDocumentChanged(previousDocument, currentDocument));
					}
				});
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");

		documents.add(doc);
		addTab("unnamed", new JScrollPane(doc.getTextComponent()));
		setSelectedIndex(documents.size() - 1);

		notifyMultipleDocumentListeners(l -> l.documentAdded(doc));

		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		for (int i = 0; i < documents.size(); i++) {
			SingleDocumentModel doc = documents.get(i);
			if (path.equals(doc.getFilePath())) {
				setSelectedIndex(i);
				return doc;
			}
		}

		List<String> lines;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		StringBuilder sb = new StringBuilder();
		lines.forEach(line -> sb.append(line + System.getProperty("line.separator")));
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, sb.toString());
		documents.add(doc);
		addTab(path.getFileName().toString(), null, new JScrollPane(doc.getTextComponent()),
				path.toAbsolutePath().normalize().toString());
		setSelectedIndex(documents.size() - 1);

		notifyMultipleDocumentListeners(l -> l.documentAdded(doc));

		return doc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		} else {
			for (SingleDocumentModel doc : documents) {
				// if(model.equals(doc)) {
				// continue;
				// }
				if (newPath.equals(doc.getFilePath())) {
					// throw new IllegalArgumentException("specified file is already opened");
				}
			}
		}

		Document doc = model.getTextComponent().getDocument();
		String text = null;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		try {
			Files.write(newPath, text.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		model.setModified(false);
		int index = documents.indexOf(model);
		setIconAt(index, GREEN_DISKETTE);
		model.setFilePath(newPath);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		removeTabAt(index);
		documents.remove(index);
		if (currentDocument == model && documents.size() > 0) {
			setSelectedIndex(documents.size() - 1);
		}
		notifyMultipleDocumentListeners(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	private void notifyMultipleDocumentListeners(Consumer<MultipleDocumentListener> consumer) {
		List<MultipleDocumentListener> listenersCopy = new ArrayList<>(listeners);
		for (MultipleDocumentListener l : listenersCopy) {
			consumer.accept(l);
		}
	}

}
