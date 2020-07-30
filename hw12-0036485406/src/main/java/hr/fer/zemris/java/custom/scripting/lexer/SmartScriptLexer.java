package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer teksta dokumenta.
 * 
 * @author nikola
 *
 */
public class SmartScriptLexer {

	/**
	 * Polje znakova koji čine teksta dokumenta koji se obrađuje.
	 */
	private char[] data;
	/**
	 * Posljednji token koji je stvoren analizom teksta dokumenta.
	 */
	private Token token;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int currentIndex;
	/**
	 * Trenutno stanje lexer-a.
	 */
	private LexerState state;

	/**
	 * Znakovi pomoću kojih se utvrđuje token.
	 */
	private static final String QUOTES = "\"";
	private static final String AT_SIGN = "@";
	private static final String MINUS_SIGN = "-";
	private static final String ASSIGN = "=";
	private static final String OPEN_TAG = "{$";
	private static final String CLOSED_TAG = "$}";

	/**
	 * Konstruktor. Prima tekst dokumenta kao <code>String</code>.
	 * 
	 * @param text
	 *            tekst dokumenta
	 * @throws NullPointerException ako je tekst dokumenta <code>null</code>
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new NullPointerException("Tekst dokumenta ne smije biti null.");
		}

		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT;
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

	/**
	 * Vraća indeks na prvi neobrađeni znak u polju.
	 * 
	 * @return indeks na prvi neobrađeni znak u polju
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * Vraća trenutno stanje lexer-a.
	 * 
	 * @return trenutno stanje lexer-a
	 */
	public LexerState getState() {
		return state;
	}

	/**
	 * Postavlja novo stanje lexer-a.
	 * 
	 * @param state
	 *            novo stanje lexer-a
	 * @throws NullPointerException
	 *             ako je novo stanje <code>null</code>
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("Stanje leksera ne smije biti null.");
		}

		this.state = state;
	}

	/**
	 * Metoda izlučuje sljedeći token, postavlja ga kao trenutnog i odmah ga vraća.
	 * 
	 * @throws LexerException
	 *             ako dođe do problema pri tokenizaciji
	 */
	public Token nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Metoda izlučuje sljedeći token iz teksta dokumenta.
	 * 
	 * @throws LexerException
	 *             ako dođe do pogreške pri tokenizaciji
	 */
	private void extractNextToken() {
		// Ako je već prije utvrđen kraj, ponovni poziv metode je greška:
		if (token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptLexerException("Tokeni više nisu dostupni.");
		}

		// Ako više nema znakova, generiraj token za kraj
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}

		if (state == LexerState.TEXT) {
			textMode();
		} else {
			tagMode();
		}
	}

	/**
	 * Metoda izlučuje sljedeći token kada je lexer u načinu rada <code>TAG</code>.
	 * 
	 * @throws LexerException
	 *             ako dođe do pogreške pri tokenizaciji
	 */
	private void tagMode() {
		// preskoči praznine:
		skipBlanks();

		// Ako više nema znakova, generiraj token za kraj
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}

		if (startsWith(ASSIGN)) {
			currentIndex += ASSIGN.length();
			String value = String.valueOf(ASSIGN);
			token = new Token(TokenType.IDENT, value);
			return;
		}

		if (startsWith(CLOSED_TAG)) {
			currentIndex += CLOSED_TAG.length();
			token = new Token(TokenType.CLOSED_TAG, null);
			return;
		}

		if (Character.isLetter(data[currentIndex])) {
			String value = extractVariableName();
			token = new Token(TokenType.IDENT, value);
			return;
		}

		if (Character.isDigit(data[currentIndex])) {
			Number value = extractNumber();
			token = new Token(TokenType.NUMBER, value);
			return;
		}

		if (startsWith(QUOTES)) {
			currentIndex += QUOTES.length();
			String value = extractString();
			if (value.length() == 0) {
				throw new SmartScriptLexerException("Između para '\"' mora biti barem jedan znak.");
			}
			currentIndex += QUOTES.length();
			token = new Token(TokenType.STRING, value);
			return;
		}

		if (startsWith(AT_SIGN)) {
			currentIndex += AT_SIGN.length();
			String value = extractVariableName();
			if (value.length() == 0) {
				throw new SmartScriptLexerException("Poslije '@' mora ići naziv funkcije.");
			}
			token = new Token(TokenType.FUNCTION, value);
			return;
		}

		if (startsWith(MINUS_SIGN)) {
			if ((currentIndex + 1) < data.length && Character.isDigit(data[currentIndex + 1])) {
				Number value = extractNumber();
				token = new Token(TokenType.NUMBER, value);
				return;
			}

			// onda je '-' operator
		}

		if (isValidOperator(data[currentIndex])) {
			String value = String.valueOf(data[currentIndex]);
			currentIndex++;
			token = new Token(TokenType.OPERATOR, value);
			return;
		}

		throw new SmartScriptLexerException("Pronađen je nevažeći znak: '" + data[currentIndex] + "'.");
	}

	/**
	 * Metoda izlučuje niz znakova. Izlučuje se niz znakova do nailaska na znak
	 * <code>""'<code>. Dopušta se escape sekvenca <code>"\\"<code> () i
	 * <code>"\""<code>.
	 * 
	 * @return niz znakova
	 */
	private String extractString() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {

			if (startsWith(QUOTES)) {
				// naišli smo na kraj stringa
				break;
			} else if (currentIndex < data.length && data[currentIndex] == '\\') {
				// početak escape sekvence, preskačem '\'
				currentIndex++;
				// je li sekvenca valjana?
				if (isValidEscapeSequenceInString()) {
					sb.append(data[currentIndex]);
					currentIndex++;
				} else {
					throw new SmartScriptLexerException("Ulaz ne valja!");
				}
			}

			// ako nije kraj stringa ili početak escape sekvence samo dodaj u string builder
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		;

		return sb.toString();
	}

	/**
	 * Provjerava je li valjana sekvenca niza znakova u načinu rada
	 * <code>TAG</code>.
	 * 
	 * @return <code>true</code> ako je valjana, inače <code>false</code>
	 */
	private boolean isValidEscapeSequenceInString() {
		if (currentIndex >= data.length) {
			return false;
		}

		if (data[currentIndex] != '\\' && data[currentIndex] != '"') {
			return false;
		}

		return true;
	}

	/**
	 * Metoda izlučuje broj. Broj može biti cijeli ili decimalni.
	 * 
	 * @return broj zapisan u dokumentu na trenutnoj poziciji
	 */
	private Number extractNumber() {

		// Zapamti početak:
		int startIndex = currentIndex;

		// Je li negativan broj?
		if (data[currentIndex] == '-') {
			currentIndex++;
		}

		// Pređi preko cijelobrojnog dijela:
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}

		// Ako smo došli do decimalne točke:
		if (currentIndex < data.length && data[currentIndex] == '.') {
			currentIndex++;
			// Pređi preko decimalnog dijela:
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
		}

		// Zapamti kraj i dohvati prihvaćeni dio kao string:
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		try {
			if (value.contains(".")) {
				return Double.parseDouble(value);
			} else {
				return Integer.parseInt(value);
			}
		} catch (NumberFormatException e) {
			throw new SmartScriptLexerException("Broj '" + value + "'nije valjan.");
		}
	}

	/**
	 * Metoda izlučuje ime varijable. Varijabla može biti bilo koji niz znakova koji
	 * započinje slovom i nakon toga slijedi nula ili više slova, znamenki ili
	 * podvlake.
	 * 
	 * @return ime varijable zapisane u dokumentu na trenutnoj poziciji
	 */
	private String extractVariableName() {
		int startIndex = currentIndex;
		if (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			currentIndex++;
		}
		while (currentIndex < data.length && isLetterDigitOrUnderscore(data[currentIndex])) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		return new String(data, startIndex, endIndex - startIndex);
	}

	/**
	 * Provjerava je li dani znak slovo, broj ili podvlaka (underscore).
	 * 
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	private boolean isLetterDigitOrUnderscore(char c) {
		if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
			return true;
		}
		;

		return false;
	}

	/**
	 * Dopušteni operatori.
	 */
	private static final Character PLUS = '+';
	private static final Character MINUS = '-';
	private static final Character MUL = '*';
	private static final Character DIV = '/';
	private static final Character POW = '^';

	/**
	 * Provjerava je li dani znak jedan od dozvoljenih operatora.
	 * 
	 * @param operator
	 * @return <code>true</code> ako je dozvoljen, inače <code>false</code>
	 */
	private boolean isValidOperator(char operator) {
		if (data[currentIndex] == PLUS)
			return true;
		if (data[currentIndex] == MINUS)
			return true;
		if (data[currentIndex] == MUL)
			return true;
		if (data[currentIndex] == DIV)
			return true;
		if (data[currentIndex] == POW)
			return true;

		return false;
	}

	/**
	 * Metoda izlučuje sljedeći token kada je lexer u načinu rada <code>TEXT</code>.
	 * 
	 * @throws LexerException
	 *             ako dođe do problema pri tokenizaciji
	 */
	private void textMode() {

		if (startsWith(OPEN_TAG)) {
			currentIndex += OPEN_TAG.length();
			token = new Token(TokenType.OPEN_TAG, null);
			return;
		}

		StringBuilder sb = new StringBuilder();
		do {
			char c = data[currentIndex];
			if (c == '\\') {
				currentIndex++;
				if (!isValidEscapeSequenceInText()) {
					throw new SmartScriptLexerException("Ulaz ne valja!");
				}
			} else if (c == '{') {
				if (startsWith(OPEN_TAG)) {
					break;
				}
			}

			sb.append(data[currentIndex]);
			currentIndex++;
		} while (currentIndex < data.length);

		String value = sb.toString();
		token = new Token(TokenType.STRING, value);
		return;
	}

	/**
	 * Provjerava je li valjana sekvenca niza znakova u načinu rada
	 * <code>TEXT</code>.
	 * 
	 * @return <code>true</code> ako je valjana, inače <code>false</code>
	 */
	private boolean isValidEscapeSequenceInText() {
		if (currentIndex >= data.length) {
			return false;
		}

		if (data[currentIndex] != '\\' && data[currentIndex] != '{') {
			return false;
		}

		return true;
	}

	/**
	 * Provjerava je li podniz polja
	 * <code>data<code> od neobrađenog dijela jednak nizu prefiks.
	 * 
	 * &#64;param prefix prefiks
	 * @return <code>true</code> ako je jednak, inače <code>false</code>
	 */
	private boolean startsWith(String prefix) {
		for (int i = 0, n = prefix.length(); i < n; i++) {
			if ((currentIndex + i) >= data.length || data[currentIndex + i] != prefix.charAt(i)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Metoda pomiče kazaljku trenutnog znaka tako da preskače sve prazne znakove
	 * (razmaci, prelasci u novi red, tabulatori).
	 */
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
