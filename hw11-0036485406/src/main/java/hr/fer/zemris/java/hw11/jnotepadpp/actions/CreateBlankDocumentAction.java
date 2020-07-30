package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action for creating a new blank document.
 * 
 * @author nikola
 *
 */
public class CreateBlankDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;

	public CreateBlankDocumentAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.createNewDocument();
	}
}
