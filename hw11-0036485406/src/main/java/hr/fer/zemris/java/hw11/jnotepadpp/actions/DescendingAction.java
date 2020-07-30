package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.text.Collator;
import java.util.Locale;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class DescendingAction extends SortAction {
	
	private static final long serialVersionUID = 1L;

	public DescendingAction(String key, ILocalizationProvider lp, MultipleDocumentModel model) {
		super(key, lp, model, Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage())).reversed());
	}

}
