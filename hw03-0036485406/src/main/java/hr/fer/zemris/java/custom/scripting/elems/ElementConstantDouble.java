package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Reprezentacija konstante realnog broja u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Vrijednost realnog broja.
	 */
	private double value;
	
	/**
	 * Stvara reprezentaciju konstante realnog broja.
	 * 
	 * @param value realni broj
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/**
	 * Vraća vrijednost realnog broja.
	 * 
	 * @return realni broj
	 */
	public double getValue() {
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
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if(this.value != other.value) {
			return false;
		}
		return true;
	}
	
}
