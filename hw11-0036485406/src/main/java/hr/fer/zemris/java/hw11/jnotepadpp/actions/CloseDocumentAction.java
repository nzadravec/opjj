package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for closing document shown it a tab.
 * 
 * @author nikola
 *
 */
public class CloseDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	
	public CloseDocumentAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SingleDocumentModel currentDocument = model.getCurrentDocument();
		if (currentDocument == null) {
			return;
		}

		model.closeDocument(currentDocument);
	}

}
