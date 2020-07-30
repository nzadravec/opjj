package hr.fer.zemris.java.hw05.db;

/**
 * Razred predstavlja uvjetni izraz upita.
 * 
 * @author nikola
 *
 */
public class ConditionalExpression {
	
	/**
	 * Strategija dohvata vrijednosti atributa razred {@link StudentRecord}.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String literal.
	 */
	private String stringLiteral;
	/**
	 * Strategija relacije dvaju string literala.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Defaultni konstruktor.
	 * 
	 * @param fieldGetter strategija dohvata vrijednosti atributa razred {@link StudentRecord}
	 * @param stringLiteral string literal
	 * @param comparisonOperator strategija relacije dvaju string literala
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		
		if(fieldGetter == null || stringLiteral == null || comparisonOperator == null) {
			throw new NullPointerException();
		}
		
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Vraća strategije dohvata vrijednosti atributa razred {@link StudentRecord}.
	 * 
	 * @return strategija dohvata vrijednosti atributa razred {@link StudentRecord}
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Vraća string literal.
	 * 
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Vraća strategiju relacije dvaju string literala.
	 * 
	 * @return strategija relacije dvaju string literala
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Evalutira uvjetni izraz.
	 * 
	 * @param record studentski zapis
	 * @return <code>true</code> ako je uvjetni izraz istinit za dani zapis, inače <code>false</code>
	 */
	public boolean evaluate(StudentRecord record) {
		return comparisonOperator.satisfied(fieldGetter.get(record), stringLiteral);
	}
	
}
