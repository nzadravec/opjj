package hr.fer.zemris.java.hw07.shell;

public enum ShellSymbol {

	PROMPT("PROMPT"),
	MORELINES("MORELINES"),
	MULTILINE("MULTILINE");
	
	private final String symbolName;

    /**
     * @param text
     */
	ShellSymbol(final String symbolName) {
        this.symbolName = symbolName;
    }
	
	@Override
	public String toString() {
		return symbolName;
	}

}
