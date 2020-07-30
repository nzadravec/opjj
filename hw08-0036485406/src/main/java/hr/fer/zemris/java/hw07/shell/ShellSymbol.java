package hr.fer.zemris.java.hw07.shell;

public enum ShellSymbol {

	/**
	 * Symbol on a display screen indicating that the shell is waiting for input.
	 */
	PROMPT("PROMPT"),
	/**
	 * Each line that is not the last line of command must end with this symbol that
	 * is used to inform the shell that more lines is expected.
	 */
	MORELINES("MORELINES"),
	/**
	 * Shell writes this symbol at the beginning followed by a single whitespace for
	 * each line that is part of multi-line command (except for the first one).
	 */
	MULTILINE("MULTILINE");

	private final String name;

	/**
	 * @param symbol
	 *            name
	 */
	ShellSymbol(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
