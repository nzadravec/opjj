package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for saving document.
 * 
 * @author nikola
 *
 */
public class SaveDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public SaveDocumentAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
		super(key, lp);
		this.notepadPP = notepadPP;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel currentDocument = model.getCurrentDocument();
		if (currentDocument == null) {
			return;
		}

		Path filePath = currentDocument.getFilePath();
		if (filePath == null) {
			JOptionPane.showMessageDialog(notepadPP, "File doesn't have path to save. Use Save As..", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			model.saveDocument(currentDocument, null);
		} catch (Exception exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(notepadPP, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
