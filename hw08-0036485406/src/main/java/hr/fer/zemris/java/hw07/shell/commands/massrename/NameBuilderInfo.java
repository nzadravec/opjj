package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Represents objects that contain {@link StringBuilder} and are sent to
 * {@link NameBuilder} objects which append part of the new name inside it.
 * 
 * @author nikola
 *
 */
public interface NameBuilderInfo {

	/**
	 * Returns {@link StringBuilder} object.
	 * 
	 * @return {@link StringBuilder} object
	 */
	StringBuilder getStringBuilder();

	/**
	 * Returns index-th part of the current name.
	 * 
	 * @param index of part of the current name
	 * @return index-th part of the current name
	 */
	String getGroup(int index);

}
