package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Reprezentacija niza znakova u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementString extends Element {

	/**
	 * Niz znakova.
	 */
	private String value;

	/**
	 * Stvara reprezentaciju niza znakova.
	 * 
	 * @param value niz znakova
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Vraća niz znakova.
	 * 
	 * @return niz znakova
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementString other = (ElementString) obj;
		if(!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
