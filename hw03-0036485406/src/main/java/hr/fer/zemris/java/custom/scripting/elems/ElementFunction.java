package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Reprezentacija funkcije u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementFunction extends Element {

	/**
	 * Naziv funkcije.
	 */
	private String name;

	/**
	 * Stvara reprezentaciju funkcije.
	 * 
	 * @param name naziv funkcije
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Vraća naziv funkcije.
	 * 
	 * @return naziv funkcije.
	 */
	public String getValue() {
		return name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementFunction other = (ElementFunction) obj;
		if(!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
