package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.regex.Matcher;

/**
 * {@link NameBuilderInfo} implementation. Uses {@link Matcher} for getting
 * parts of the current name by index.
 * 
 * @author nikola
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

	/**
	 * Object for appending parts of the name.
	 */
	private StringBuilder sb = new StringBuilder();
	/**
	 * Object for getting parts of the current name by index.
	 */
	private Matcher matcher;

	/**
	 * Default constructor.
	 * 
	 * @param matcher
	 *            name matcher
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		super();
		this.matcher = matcher;
	}

	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	public String getGroup(int index) {
		return matcher.group(index);
	}

}
