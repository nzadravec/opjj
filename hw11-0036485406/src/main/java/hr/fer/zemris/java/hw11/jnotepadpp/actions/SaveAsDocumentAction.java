package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for saving-as document.
 * 
 * @author nikola
 *
 */
public class SaveAsDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public SaveAsDocumentAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
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

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As");
		int retVal = fc.showSaveDialog(notepadPP);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(notepadPP, "Nothing was recorded.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path filePath = fc.getSelectedFile().toPath();
		if (Files.exists(filePath)) {
			Object[] options = { "Replace", "Cancel" };

			int decision = JOptionPane.showOptionDialog(notepadPP,
					"The file already exists in \""+ filePath.getName(filePath.getNameCount()-2) +"\". Replacing it will overwrite its contents.",
					"A file named \""+ filePath.getFileName()+ "\" already exits. Do you want to replace it?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (decision == JOptionPane.NO_OPTION) {
				return;
			}
		}

		try {
			model.saveDocument(currentDocument, filePath);
		} catch (Exception exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(notepadPP, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
