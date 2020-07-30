package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	protected List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	protected void fire() {
		for(ILocalizationListener l : new ArrayList<>(listeners)) {
			l.localizationChanged();
		}
	}

}
