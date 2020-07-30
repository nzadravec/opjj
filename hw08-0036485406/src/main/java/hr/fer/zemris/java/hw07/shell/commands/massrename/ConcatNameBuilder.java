package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.List;

/**
 * Stores list of {@link NameBuilder} objects and calles their execute method when it's execute is called.
 * 
 * @author nikola
 *
 */
public class ConcatNameBuilder implements NameBuilder {
	
	/**
	 * List of {@link NameBuilder} objects.
	 */
	private List<NameBuilder> nameBuilders;

	/**
	 * Defalut constructor.
	 * 
	 * @param nameBuilders list of {@link NameBuilder} objects
	 */
	public ConcatNameBuilder(List<NameBuilder> nameBuilders) {
		super();
		this.nameBuilders = nameBuilders;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		nameBuilders.forEach(e -> e.execute(info));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ConcatNameBuilder:");
		sb.append(System.getProperty("line.separator"));
		for(NameBuilder nb : nameBuilders) {
			sb.append("\t" + nb);
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

}
