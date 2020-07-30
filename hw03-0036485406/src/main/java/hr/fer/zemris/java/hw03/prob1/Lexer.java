package hr.fer.zemris.java.hw03.prob1;

/**
 * 
 * @author nikola
 *
 */
public class Lexer {

	/**
	 * Polje znakova koji čine izvorni kod programa koji se obrađuje.
	 */
	private char[] data;
	/**
	 * Posljednji token koji je stvoren analizom izvornog koda programa.
	 */
	private Token token;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int currentIndex;

	private LexerState state;

	/**
	 * Konstruktor. Prima izvorni tekst programa kao <code>String</code>.
	 * 
	 * @param text
	 *            izvorni tekst programa
	 * @throws VLangTokenizerException
	 *             ako dođe do pogreške pri tokenizaciji
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException("Izvorni tekst programa ne smije biti null.");
		}

		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
		// nextToken();
	}

	/**
	 * Metoda dohvaća trenutni token. Metoda se može zvati više puta i uvijek će
	 * vratiti isti token, sve dok se ne zatraži izlučivanje sljedećeg tokena.
	 * 
	 * @return trenutni token
	 */
	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Stanje leksera ne smije biti null.");
		}
		
		this.state = state;
	}

	/**
	 * Metoda izlučuje sljedeći token, postavlja ga kao trenutnog i odmah ga i
	 * vraća.
	 * 
	 * @throws LexerException
	 *             ako dođe do problema pri tokenizaciji
	 */
	public Token nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Metoda izlučuje sljedeći token iz izvornog koda.
	 * 
	 * @throws LexerException
	 *             ako dođe do pogreške pri tokenizaciji
	 * 
	 */
	private void extractNextToken() {
		// Ako je već prije utvrđen kraj, ponovni poziv metode je greška:
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Tokeni više nisu dostupni.");
		}

		// Inače preskoči praznine:
		skipBlanks();

		// Ako više nema znakova, generiraj token za kraj izvornog
		// koda programa
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}

		if (state == LexerState.BASIC) {
			basicMode();
		} else {
			extendedMode();
		}
	}

	private void extendedMode() {
		if (data[currentIndex] == '#') {
			Character value = Character.valueOf(data[currentIndex]);
			currentIndex++;
			token = new Token(TokenType.SYMBOL, value);
			return;
		}

		int startIndex = currentIndex;
		currentIndex++;
		while(currentIndex < data.length && data[currentIndex] != '#') {
			char c = data[currentIndex];
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				break;
			}
			currentIndex++;
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		token = new Token(TokenType.WORD, value);
		return;
	}

	private void basicMode() {
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			StringBuilder sb = new StringBuilder();

			while (currentIndex < data.length
					&& (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {

				if (data[currentIndex] == '\\') {
					currentIndex++;
					if (checkEscapeSequence()) {
						sb.append(data[currentIndex]);
						currentIndex++;
					} else {
						throw new LexerException("Ulaz ne valja!");
					}
				} else {
					int startIndex = currentIndex;
					currentIndex++;
					while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
						currentIndex++;
					}
					int endIndex = currentIndex;
					sb.append(new String(data, startIndex, endIndex - startIndex));
				}
			}

			String value = sb.toString();
			token = new Token(TokenType.WORD, value);
			return;
		}

		if (Character.isDigit(data[currentIndex])) {
			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
			int endIndex = currentIndex;
			long value = 0;
			try {
				value = Long.parseLong(new String(data, startIndex, endIndex - startIndex));
			} catch (NumberFormatException e) {
				throw new LexerException("Ulaz ne valja!");
			}
			token = new Token(TokenType.NUMBER, value);
			return;
		}

		Character value = Character.valueOf(data[currentIndex]);
		currentIndex++;
		token = new Token(TokenType.SYMBOL, value);
		return;
	}

	private boolean checkEscapeSequence() {
		if (currentIndex >= data.length) {
			return false;
		}

		if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\') {
			return true;
		}

		return false;
	}

	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
