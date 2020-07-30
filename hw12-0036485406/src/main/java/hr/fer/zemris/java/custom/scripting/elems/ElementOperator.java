package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Reprezentacija operatora u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementOperator extends Element {

	/**
	 * Simbol operatora.
	 */
	private String symbol;

	/**
	 * Stvara reprezentacija operatora.
	 * 
	 * @param symbol simbol operatora.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Vraća simbol operatora.
	 * 
	 * @return simbol operatora.
	 */
	public String getValue() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementOperator other = (ElementOperator) obj;
		if(!this.symbol.equals(other.symbol)) {
			return false;
		}
		return true;
	}
	
}
