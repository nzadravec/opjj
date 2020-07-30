package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementacija prilagodljivog stoga objekata s potporom kolekcije
 * {@link ArrayIndexedCollection}. Dopušteni su duplikati objekata.
 * Pohranjivanje <code>null</code> reference nije dopušteno.
 * 
 * @author nikola
 *
 */
public class ObjectStack {

	/**
	 * Interna kolekcija u kojoj se čuvaju objekti.
	 */
	private ArrayIndexedCollection coll;

	/**
	 * Pretpostavljeni konstruktor.
	 */
	public ObjectStack() {
		coll = new ArrayIndexedCollection();
	}

	/**
	 * Ispituje je li stog prazan.
	 * 
	 * @return <code>true</code> ako kolekcija ne sadrži objekte, inače
	 *         <code>false</code>.
	 */
	public boolean isEmpty() {
		return coll.isEmpty();
	}

	/**
	 * Vraća broj trenutno pohranjenih objekata na stogu.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	public int size() {
		return coll.size();
	}

	/**
	 * Stavlja objekt na vrh stoga. <code>null</code> vrijednost nisu dozvoljene.
	 * 
	 * @param value
	 *            objekt koji se stavlja na vrh stoga.
	 * @throws NullPointerException
	 *             ako je objekt <code>null</code>.
	 */
	public void push(Object value) {
		Objects.requireNonNull(value, "Nije dopušteno spremanje null reference.");

		coll.add(value);
	}

	/**
	 * Uklanja zadnji objekt postavljen na stog sa stoga i vraća ga. Ako je stog
	 * prazan, metoda baca iznimku {@link EmptyStackException}.
	 * 
	 * @return zadnji objekt postavljen na stog.
	 * @throws EmptyStackException
	 *             ako je stog prazan.
	 */
	public Object pop() {
		Object value = peek();
		coll.remove(coll.size() - 1);
		return value;
	}

	/**
	 * Vraća zadnji objekt postavljen na stog.
	 * 
	 * Slično kao {@link #pop}; vraća posljednji element postavljen na stog, ali ga
	 * ne briše iz stogova. Ako je stog prazan, metoda baca iznimku
	 * {@link EmptyStackException}.
	 * 
	 * @return zadnji objekt postavljen na stog.
	 * @throws EmptyStackException
	 *             ako je stog prazan.
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}

		return coll.get(coll.size() - 1);
	}

	/**
	 * Uklanja sve objekte iz ove kolekcije.
	 */
	public void clear() {
		coll.clear();
	}

}
