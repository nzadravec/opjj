package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Reprezentacija cijelog dokumenta.
 * 
 * @author nikola
 *
 */
public class DocumentNode extends Node {
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentNode other = (DocumentNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren())
			return false;
		for(int i = 0; i < this.numberOfChildren(); i++) {
			if(!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}
	
}
