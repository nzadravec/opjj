package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

public class PutNameAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	public PutNameAction(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
