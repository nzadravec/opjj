package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class LanguageAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private String language;
	
	public LanguageAction(String key, ILocalizationProvider lp, String language) {
		super(key, lp);
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}
	
}
