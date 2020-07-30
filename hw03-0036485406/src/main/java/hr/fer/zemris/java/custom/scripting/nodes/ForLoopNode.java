package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Reprezentacija jednog konstrukta for petlje u dokumentu.
 * 
 * @author nikola
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Pomoćna varijabla u konstruktu for petlje.
	 */
	private ElementVariable variable;
	/**
	 * Početni izraz koji poprima pomoćna varijabla.
	 */
	private Element startExpression;
	/**
	 * Posljednji izraz koji poprima pomoćna varijabla.
	 */
	private Element endExpression;
	/**
	 * Koračni izraz koji poprima pomoćna varijabla u svakom prolazu for pretlje.
	 */
	private Element stepExpression;

	/**
	 * Stvara reprezentaciju jednog konstrukta for petlje.
	 * 
	 * @param variable pomoćna varijabla
	 * @param startExpression početni izraz
	 * @param endExpression posljedni izraz
	 * @param stepExpression koračni izraz
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Vraća pomoćnu varijablu.
	 * 
	 * @return pomoćna varijabla.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Vraća početni izraz.
	 * 
	 * @return početni izraz
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Vraća posljednji izraz.
	 * 
	 * @return posljednji izraz
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Vraća koračni izraz.
	 * 
	 * @return koračni izraz
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(variable.asText() + " ");
		sb.append(startExpression.asText() + " ");
		sb.append(endExpression.asText() + " ");
		if (stepExpression.asText() != null) {
			sb.append(stepExpression.asText() + " ");
		}
		sb.append("$}");

		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if(!this.variable.equals(other.variable)) {
			return false;
		}
		if(!this.startExpression.equals(other.startExpression)) {
			return false;
		}
		if(!this.endExpression.equals(other.endExpression)) {
			return false;
		}
		if(!this.stepExpression.equals(other.stepExpression)) {
			return false;
		}
		return true;
	}

}
