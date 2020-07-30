package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public LJLabel(String key, ILocalizationProvider lp) {
		super();
		
		setText(lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}

}
