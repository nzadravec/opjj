package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node visitor interface.
 * 
 * @author nikola
 *
 */
public interface INodeVisitor {
	
	/**
	 * Visiting node {@link TextNode} operation.
	 * 
	 * @param node
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Visiting node {@link ForLoopNode} operation.
	 * 
	 * @param node
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Visiting node {@link DocumentNode} operation.
	 * 
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Visiting node {@link DocumentNode} operation.
	 * 
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);
}
