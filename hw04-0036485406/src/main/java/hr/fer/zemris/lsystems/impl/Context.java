package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Primjerci ovog razreda omogućavaju izvođenje postupka prikazivanja fraktala.
 * Razred nudi stog na koji je moguće stavljati i dohvaćati stanja kornjače.
 * 
 * @author nikola
 *
 */
public class Context {

	private ObjectStack stack;

	public Context() {
		stack = new ObjectStack();
	}

	/**
	 * Vraća stanje s vrha stoga bez uklanjanja.
	 * 
	 * @return stanje s vrha stoga
	 */
	public TurtleState getCurrentState() {
		if (stack.isEmpty()) {
			throw new RuntimeException();
		}

		return (TurtleState) stack.peek();
	}

	/**
	 * Na vrh stoga stavlja predano stanje.
	 * 
	 * @param state
	 *            predano stanje
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Briše jedno stanje s vrha stoga.
	 */
	public void popState() {
		if (stack.isEmpty()) {
			throw new RuntimeException();
		}

		stack.pop();
	}

}
