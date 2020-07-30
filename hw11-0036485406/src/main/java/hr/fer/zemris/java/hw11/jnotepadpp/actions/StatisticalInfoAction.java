package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for statistical info.
 * 
 * @author nikola
 *
 */
public class StatisticalInfoAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public StatisticalInfoAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
		super(key, lp);
		this.notepadPP = notepadPP;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel currentDocument = model.getCurrentDocument();
		if (currentDocument == null) {
			return;
		}

		JTextArea editor = currentDocument.getTextComponent();
		Document doc = editor.getDocument();
		String text = null;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}

		int numberOfChars = text.length();
		int numberOfNonBlankChars = 0;
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				numberOfNonBlankChars++;
			}
		}
		int numberOfLines = text.split(System.getProperty("line.separator")).length;

		JOptionPane.showMessageDialog(notepadPP,
				"Your document has " + numberOfChars + " characters, " + numberOfNonBlankChars
						+ " non-blank characters and " + numberOfLines + " lines.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
