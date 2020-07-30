package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private static final String DEFAULT_LANGUAGE = "en";
	private static final String BASE_NAME = "hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi";
	
	private static LocalizationProvider instance = new LocalizationProvider();

	private String language;
	private ResourceBundle bundle;

	private LocalizationProvider() {
		language = DEFAULT_LANGUAGE;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, locale);
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, locale);
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
