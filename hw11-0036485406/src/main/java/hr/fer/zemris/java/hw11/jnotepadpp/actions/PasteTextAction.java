package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Segment;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for pasting text.
 * 
 * @author nikola
 *
 */
public class PasteTextAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public PasteTextAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
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

		Segment segment = notepadPP.getSegment();
		if (segment == null) {
			return;
		}

		JTextArea editor = currentDocument.getTextComponent();
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();
		int offset = Math.min(caret.getDot(), caret.getMark());
		try {
			doc.insertString(offset, segment.toString(), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}
