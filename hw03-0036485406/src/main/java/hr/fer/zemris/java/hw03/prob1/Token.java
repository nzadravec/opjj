package hr.fer.zemris.java.hw03.prob1;

/**
 * Razred modelira jedan token ulaznog programa.
 * 
 * @author nikola
 *
 */
public class Token {

	/**
	 * Vrsta tokena.
	 */
	private TokenType type;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;

	/**
	 * Konstruktor.
	 * 
	 * @param tokenType
	 *            vrsta tokena
	 * @param value
	 *            vrijednost tokena
	 */
	public Token(TokenType type, Object value) {
		super();
		if (type == null)
			throw new NullPointerException("Vrsta tokena ne smije biti null.");
		this.type = type;
		this.value = value;
	}

	/**
	 * Dohvat vrste tokena.
	 * 
	 * @return vrsta tokena
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Dohvat vrijednosti tokena.
	 * 
	 * @return vrijednost tokena ili <code>null</code> ako token ove vrste nema
	 *         pridruženu vrijednost
	 */
	public Object getValue() {
		return value;
	}

	public String toString() {
		return type + " " + value;
	}

}
