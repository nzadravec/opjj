package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Reprezentacija konstante cijelog broja u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Vrijednost cijelog broja.
	 */
	private int value;

	/**
	 * Stvara reprezentaciju konstante cijelog broja.
	 * 
	 * @param value cijeli broj
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Vraća vrijednost cijelog broja.
	 * 
	 * @return cijeli broj
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return "" + value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if(this.value != other.value) {
			return false;
		}
		return true;
	}

}
