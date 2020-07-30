package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Reprezentacija naredbe koja dinamički stvara tekstualni izlaz u dokumentu.
 * 
 * @author nikola
 *
 */
public class EchoNode extends Node {

	/**
	 * Izrazi koje objekt dinamički stvara kao tekstualni izlaz.
	 */
	private Element[] elements;

	/**
	 * Stvara reprezentaciju naredbe koja dinamički stvara tekstualni izlaz.
	 * 
	 * @param elements izrazi
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 * Vraća izraze.
	 * 
	 * @return izrazi
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EchoNode other = (EchoNode) obj;
		if(this.elements.length != other.elements.length)
			return false;
		for(int i = 0; i < this.elements.length; i++) {
			if(!this.elements[i].equals(other.elements[i])) {
				return false;
			}
		}
		return true;
	}

}
