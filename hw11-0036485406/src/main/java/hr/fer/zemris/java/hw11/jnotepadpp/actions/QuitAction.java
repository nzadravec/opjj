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
 * Action for exiting application.
 * 
 * @author nikola
 *
 */
public class QuitAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public QuitAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
		super(key, lp);
		this.notepadPP = notepadPP;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (SingleDocumentModel m : model) {
			if (m.isModified()) {

				Object[] options = { null, "Close without saving", "Cancel" };

				Path filePath = m.getFilePath();
				String fileName;
				if (filePath != null) {
					fileName = filePath.getFileName().toString();
					options[0] = "Save";
				} else {
					fileName = "Untitled Document"; // unnamed
					options[0] = "Save As...";
				}

				int decision = JOptionPane.showOptionDialog(notepadPP,
						"If you don't save, changes will be permanently lost.",
						"Save changes to document \"" + fileName + "\" before closing?",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (decision == JOptionPane.YES_OPTION) {

					if (filePath == null) {
						
						JFileChooser fc = new JFileChooser();
						fc.setDialogTitle("Save As");
						int retVal = fc.showSaveDialog(notepadPP);
						if (retVal != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(notepadPP, "Nothing was recorded.", "Warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						filePath = fc.getSelectedFile().toPath();
						if (Files.exists(filePath)) {
							Object[] options2 = { "Replace", "Cancel" };

							int decision2 = JOptionPane.showOptionDialog(notepadPP,
									"The file already exists in \""+ filePath.getName(filePath.getNameCount()-2) +"\". Replacing it will overwrite its contents.",
									"A file named \""+ filePath.getFileName()+ "\" already exits. Do you want to replace it?",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options[0]);

							if (decision2 == JOptionPane.NO_OPTION) {
								return;
							}
						}
						
					} 

					try {
						model.saveDocument(m, filePath);
					} catch (Exception exc) {
						exc.printStackTrace();
						JOptionPane.showMessageDialog(notepadPP, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

				} else if (decision == JOptionPane.NO_OPTION) {
					// do nothing
				} else {
					return;
				}
			}
		}

		notepadPP.dispose();
	}
}
