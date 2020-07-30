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
 * Action for cutting text.
 * 
 * @author nikola
 *
 */
public class CutTextAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private JNotepadPP notepadPP;
	private MultipleDocumentModel model;

	public CutTextAction(String key, ILocalizationProvider lp, JNotepadPP notepadPP, MultipleDocumentModel model) {
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

		JTextArea editor = currentDocument.getTextComponent();
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();
		int len = Math.abs(caret.getDot() - caret.getMark());
		int offset = Math.min(caret.getDot(), caret.getMark());
		Segment segment = new Segment();
		try {
			doc.getText(offset, len, segment);
			doc.remove(offset, len);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		notepadPP.setSegment(segment);
	}

}
