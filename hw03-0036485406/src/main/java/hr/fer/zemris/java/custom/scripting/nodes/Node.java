package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Osnovni razred za reprezentaciju strukturiranih dokumenata.
 * 
 * @author nikola
 *
 */
public class Node {

	/**
	 * Kolekcija djece.
	 */
	private ArrayIndexedCollection childsColl;

	/**
	 * Dodaje čvor dijete u kolekciju djece ovog čvora.
	 * 
	 * @param child
	 *            vrh dijete.
	 */
	public void addChildNode(Node child) {
		if (childsColl == null) {
			childsColl = new ArrayIndexedCollection();
		}

		childsColl.add(child);
	}

	/**
	 * Vraća broj djece ovog čvora.
	 * 
	 * @return broj djece
	 */
	public int numberOfChildren() {
		if (childsColl == null) {
			return 0;
		}

		return childsColl.size();
	}

	/**
	 * Vraća dijete na poziciji index kolekcije djece ovog čvora.
	 * 
	 * @param index
	 *            indeks djeteta
	 * @return dijete na poziciji index
	 * @throws IllegalArgumentException
	 *             ako je indeks manji od <code>0</code> i veći ili jednak broju djece u ovom vrhu
	 */
	public Node getChild(int index) {
		if (childsColl == null || (index < 0 || index >= childsColl.size())) {
			throw new IllegalArgumentException();
		}

		return (Node) childsColl.get(index);
	}

}
