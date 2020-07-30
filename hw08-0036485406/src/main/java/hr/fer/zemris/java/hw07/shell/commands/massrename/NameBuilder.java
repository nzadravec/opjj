package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Represents objects that generate part of the new name by writing to
 * {@link StringBuilder} that gets over the argument info in the execute method.
 * 
 * @author nikola
 *
 */
public interface NameBuilder {

	/**
	 * Generates part of the new name by writing to {@link StringBuilder} that gets
	 * over the argument info.
	 * 
	 * @param info
	 *            object which contains {@link StringBuilder} object
	 */
	void execute(NameBuilderInfo info);

}
