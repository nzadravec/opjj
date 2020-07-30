package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

public class UniqueAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;

	public UniqueAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel currentDocument = model.getCurrentDocument();
		if (currentDocument == null) {
			return;
		}

		JTextArea editor = currentDocument.getTextComponent();
		Caret caret = editor.getCaret();
		int len = Math.abs(caret.getDot() - caret.getMark());
		if (len == 0) {
			return;
		}

		List<String> selectedLines = new ArrayList<>();
		int offset = Math.min(caret.getDot(), caret.getMark());

		int firstLine = 0;
		int lastLine = 0;
		try {
			firstLine = editor.getLineOfOffset(offset);
			lastLine = editor.getLineOfOffset(offset + len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		Document doc = editor.getDocument();
		int numberOfLines = lastLine - firstLine + 1;
		for (int line = firstLine, i = 0; i < numberOfLines; i++) {
			try {
				int lineStart = editor.getLineStartOffset(line);
				int lineEnd = editor.getLineEndOffset(line);
				String text = doc.getText(lineStart, lineEnd - lineStart);
				if (selectedLines.contains(text)) {
					doc.remove(lineStart, lineEnd - lineStart);
				} else {
					line++;
					selectedLines.add(text);
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}
}
