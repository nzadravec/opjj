package hr.fer.zemris.java.custom.collections;

/**
 * Implemetacija kolekcije koja pod zadanim ključem pamti predanu vrijednost. Ne
 * podržava više zapisa s istim ključevima; zapis je jedinstven ključem. Ključ
 * ne smije biti null; vrijednost smije.
 * 
 * @author nikola
 *
 */
public class Dictionary {

	/**
	 * Razred predstavlja zapis objekata kolekcije {@link Dictionary}.
	 * 
	 * @author nikola
	 *
	 */
	private class Entry {
		/** ključ */
		private Object key;
		/** vrijednost */
		private Object value;

		/**
		 * Stvara objekt pomoću ključa i vrijednosti.
		 * 
		 * @param key
		 *            ključ
		 * @param value
		 *            vrijednost
		 */
		public Entry(Object key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return key + " : " + value;
		}
	}

	/**
	 * Interna kolekcija u kojoj se čuvaju objekti {@link Entry}.
	 */
	private ArrayIndexedCollection coll;

	/**
	 * Podrazumijevani konstruktor.
	 */
	public Dictionary() {
		coll = new ArrayIndexedCollection();
	}

	/**
	 * Ispituje je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako kolekcija ne sadrži objekte, inače
	 *         <code>false</code>
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Vraća broj trenutno pohranjenih objekata u kolekciji.
	 * 
	 * @return broj trenutno pohranjenih objekata
	 */
	public int size() {
		return coll.size();
	}

	/**
	 * Uklanja sve objekte iz ove kolekcije.
	 * 
	 */
	public void clear() {
		coll.clear();
	}

	/**
	 * Dodaje zadani zapis (ključ, vrijednost) u kolekciju. Ako zapis sa dani ključ
	 * već postoji u kolekciji, vrijednost tog zapisa se mjenja i danu vrijednost.
	 */
	public void put(Object key, Object value) {
		if (key == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < coll.size(); i++) {
			Entry p = (Entry) coll.get(i);
			if (p.key == key) {
				p.value = value;
				return;
			}
		}

		coll.add(new Entry(key, value));
	}

	/**
	 * Vraća vrijednost u zapisu s danim ključem.
	 * Ako ne postoji dani ključ, vraća <code>null</code>.
	 * 
	 * @param key ključ
	 * @return vrijednost u zapisu s danim ključem
	 */
	public Object get(Object key) {
		if (key == null) {
			return null;
			//throw new NullPointerException();
		}

		for (int i = 0; i < coll.size(); i++) {
			Entry p = (Entry) coll.get(i);
			if (p.key == key) {
				return p.value;
			}
		}

		return null;
	}

}
