package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		super();
		
		putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
			}
		});
	}

}
