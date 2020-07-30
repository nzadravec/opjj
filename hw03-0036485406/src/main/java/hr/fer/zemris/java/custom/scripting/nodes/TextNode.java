package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Reprezentacija tekstualnog podatka u dokumentu.
 * 
 * @author nikola
 *
 */
public class TextNode extends Node {

	/**
	 * Tekstualni podatak.
	 */
	private String text;

	/**
	 * Stvara reprezentacija tekstualnog podatka.
	 * 
	 * @param text tekstualni podatak.
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextNode other = (TextNode) obj;
		if(!this.text.equals(other.text)) {
			return false;
		}
		return true;
	}
	
}
