package hr.fer.zemris.java.hw05.db.lexer;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Lexer teksta upita.
 * 
 * @author nikola
 *
 */
public class Lexer {

	/**
	 * Polje znakova koji čine tekst upisa koji se obrađuje.
	 */
	private char[] data;
	/**
	 * Posljednji token koji je stvoren analizom teksta upita.
	 */
	private Token token;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int currentIndex;

	/**
	 * Lista operatora.
	 */
	private static final List<String> operators;
	/**
	 * Lista imena atributa u razredu {@link StudentRecord}.
	 */
	private static final List<String> attributes;

	static {
		operators = new ArrayList<>();
		operators.add(">");
		operators.add(">=");
		operators.add("<");
		operators.add("<=");
		operators.add("=");
		operators.add("!=");
		operators.add("LIKE");

		attributes = new ArrayList<>();
		attributes.add("firstName");
		attributes.add("lastName");
		attributes.add("jmbag");
	}

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param queryString
	 *            tekst upita
	 */
	public Lexer(String queryString) {
		if (queryString == null) {
			throw new NullPointerException();
		}

		data = queryString.toCharArray();
		currentIndex = 0;
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

	public Token nextToken() {
		extractNextToken();
		return getToken();
	}

	/**
	 * Metoda dohvaća trenutni token. Metoda se može zvati više puta i uvijek će
	 * vratiti isti token, sve dok se ne zatraži izlučivanje sljedećeg tokena.
	 * 
	 * @return trenutni token
	 */
	private void extractNextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Tokeni više nisu dostupni.");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}

		for (String attribute : attributes) {
			if (startsWith(attribute)) {
				currentIndex += attribute.length();
				token = new Token(TokenType.ATTRIBUTE_NAME, attribute);
				return;
			}
		}

		for (String operator : operators) {
			if (startsWith(operator)) {
				currentIndex += operator.length();
				token = new Token(TokenType.OPERATOR, operator);
				return;
			}
		}

		if (data[currentIndex] == 'a' || data[currentIndex] == 'A') {
			passAND();
			token = new Token(TokenType.AND, null);
			return;
		}

		if (data[currentIndex] == '\"') {
			String stringLiteral = extractStringLiteral();
			token = new Token(TokenType.STRING_LITERAL, stringLiteral);
			return;
		}

		throw new LexerException("Pronađen je nevažeći znak: '" + data[currentIndex] + "'.");
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
	 * Metoda prolazi niz znakova "and" iz polja data od indeksa currentIndex, neovisno o velikom ili malom
	 * slovu.
	 * 
	 * @throws LexerException
	 *             ako se u polju data od indeksa currentIndex ne nalazi niz znakova
	 *             "and"
	 */
	private void passAND() {
		if (currentIndex >= data.length || (data[currentIndex] != 'a' && data[currentIndex] != 'A')) {
			throw new LexerException("Ulaz ne valja!");
		}
		currentIndex++;
		if (currentIndex >= data.length || (data[currentIndex] != 'n' && data[currentIndex] != 'N')) {
			throw new LexerException("Ulaz ne valja!");
		}
		currentIndex++;
		if (currentIndex >= data.length || (data[currentIndex] != 'd' && data[currentIndex] != 'D')) {
			throw new LexerException("Ulaz ne valja!");
		}
		currentIndex++;
	}

	/**
	 * Metoda izvlaći niz znakova unutar navodnika iz polja data od indeksa currentIndex.
	 * 
	 * @return niz znakova
	 * @throws LexerException ako ne polje data od indeksa ne sadrži navodnike
	 */
	private String extractStringLiteral() {
		if (currentIndex >= data.length || data[currentIndex] != '"') {
			throw new LexerException();
		}
		currentIndex++;
		StringBuilder sb = new StringBuilder();
		boolean quotes = false;
		while (currentIndex < data.length) {
			if (startsWith("\"")) {
				quotes = true;
				currentIndex++;
				break;
			}

			sb.append(data[currentIndex]);
			currentIndex++;
		}

		if (!quotes) {
			throw new LexerException("Ulaz ne valja!");
		}

		return sb.toString();
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
