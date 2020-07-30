package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

public class InvertCaseAction extends ToolsAction {

	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;

	public InvertCaseAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp, model);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();

		int len = Math.abs(caret.getDot() - caret.getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(caret.getDot(), caret.getMark());
		} else {
			len = doc.getLength();
		}

		try {
			// sačuvaj stare pozicije koje ćemo ponovno postaviti
			int dotPosition = caret.getDot();
			int markPosition = caret.getMark();

			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);

			caret.setDot(markPosition);
			caret.moveDot(dotPosition);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();

		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if (Character.isLowerCase(c)) {
				znakovi[i] = Character.toUpperCase(c);
			} else {
				znakovi[i] = Character.toLowerCase(c);
			}
		}

		return new String(znakovi);
	}
	
}
