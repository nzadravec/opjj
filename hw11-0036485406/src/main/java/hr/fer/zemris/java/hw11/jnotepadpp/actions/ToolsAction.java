package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

public abstract class ToolsAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	public ToolsAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp);
		setEnabled(false);

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel == null) {
					setEnabled(false);
				}

				JTextArea editor = currentModel.getTextComponent();
				editor.addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						int dot = e.getDot();
						int mark = e.getMark();
						if (dot != mark) {
							setEnabled(true);
						} else {
							setEnabled(false);
						}
					}
				});
			}
		});
	}

}
