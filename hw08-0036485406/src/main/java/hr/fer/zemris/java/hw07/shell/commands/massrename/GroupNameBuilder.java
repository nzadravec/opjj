package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Writes the given group with a given set of minimum width.
 * 
 * @author nikola
 *
 */
public class GroupNameBuilder implements NameBuilder {
	
	/**
	 * Default fill character and minimal width.
	 */
	public static final Character DEFAULT_FILL_CHAR = ' ';
	public static final int DEFAULT_MIN_CHARS = 0;
	
	/**
	 * Group index.
	 */
	private int groupIndex;
	/**
	 * Fill character.
	 */
	private Character fillChar;
	/**
	 * Minimal width.
	 */
	private int minWidth;
	
	/**
	 * Initializes object with default values for fill character and minimal width.
	 * 
	 * @param groupIndex group index
	 */
	public GroupNameBuilder(int groupIndex) {
		this(groupIndex, DEFAULT_FILL_CHAR, DEFAULT_MIN_CHARS);
	}

	/**
	 * Default constructor.
	 * 
	 * @param groupIndex group index
	 * @param fillChar fill character
	 * @param minWidth minimal width
	 */
	public GroupNameBuilder(int groupIndex, Character fillChar, int minWidth) {
		super();
		this.groupIndex = groupIndex;
		this.fillChar = fillChar;
		this.minWidth = minWidth;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		StringBuilder sb = info.getStringBuilder();
		String s = info.getGroup(groupIndex);
		for(int i = s.length(); i < minWidth; i++) {
			sb.append(fillChar);
		}
		
		sb.append(s);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("GroupNameBuilder: ");
		sb.append("group " + groupIndex + ", ");
		sb.append("fill char '" + fillChar + "', ");
		sb.append("min chars " + minWidth);
		return sb.toString();
	}

}
