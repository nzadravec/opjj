package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for opening existing document.
 * 
 * @author nikola
 *
 */
public class OpenDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public OpenDocumentAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
		super(key, lp);
		this.notepadPP = notepadPP;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open");

		int retVal = fc.showOpenDialog(notepadPP);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(notepadPP, "File " + fileName.getAbsolutePath() + " doesn't exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			model.loadDocument(filePath);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(notepadPP, "Error while reading file " + fileName.getAbsolutePath() + ".",
					"Eror: cannot read file", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
