package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Reprezentacija varijable u izrazu.
 * 
 * @author nikola
 *
 */
public class ElementVariable extends Element {

	/**
	 * Ime varijable.
	 */
	private String name;

	/**
	 * Stvara reprezentaciju varijable.
	 * 
	 * @param name ime varijable
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	/**
	 * Vraća ime varijable.
	 * 
	 * @return ime varijable
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementVariable other = (ElementVariable) obj;
		if(!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
