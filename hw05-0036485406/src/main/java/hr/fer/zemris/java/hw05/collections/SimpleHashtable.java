package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.abs;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja tablicu raspršenog adresiranja koja omogućava pohranu
 * uređenih parova (ključ, vrijednost). Implementacija ovog razreda ne
 * dozvoljava da ključevi budu null reference; vrijednosti, s druge strane, mogu
 * biti null reference.
 * 
 * @author nikola
 *
 * @param <K>
 *            ključ
 * @param <V>
 *            vrijednost
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Polje slotova tablice.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Broj parova koji su pohranjeni u tablici.
	 */
	private int size;

	/**
	 * Popunjenost tablice.
	 */
	private int occupancy;

	private int modificationCount;

	/**
	 * Defaultni kapacitet kolekcije.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Granična popunjenost tablice.
	 */
	private static final double MAX_OCCUPANCY_RATE = 0.75;

	/**
	 * Razred predstavlja jedan slot tablice.
	 * 
	 * @author nikola
	 *
	 * @param <K>
	 *            ključ
	 * @param <V>
	 *            vrijednost
	 */
	public static class TableEntry<K, V> {
		/**
		 * Ključ.
		 */
		private K key;
		/**
		 * Vrijednost.
		 */
		private V value;
		/**
		 * Referenca na sljedeći primjerak razreda u istom slotu tablice.
		 */
		private TableEntry<K, V> next;

		/**
		 * Defaultni konstruktor.
		 * 
		 * @param key
		 *            ključ
		 * @param value
		 *            vrijednost
		 */
		public TableEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		/**
		 * Vraća vrijednost.
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Postavlja novu vrijednost.
		 * 
		 * @param value
		 *            nova vrijednost
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Vraća ključ.
		 * 
		 * @return ključ
		 */
		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

	}

	/**
	 * Defaultni koji stvara tablicu veličine 16 slotova.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Stvara tablicu veličine predane vrijednosti <code>capacity</code>.
	 * 
	 * @param capacity
	 *            vrijednost koja predstavlja željenu veličinu tablice
	 * @throws IllegalArgumentException
	 *             ako je <code>capacity</code> manji od <code>1</code>
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		int slots = 1;
		while (slots < capacity) {
			slots *= 2;
		}

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * Dodaje novi par (ključ, vrijednost) u tablicu. Ako je predan ključ koji u
	 * tablici već postoji, ažurira se postojeći par novom vrijednošću. Za potrebe
	 * usporedbe jesu li dva ključa ista koristi se metoda <code>equals</code> nad
	 * ključevima.
	 * 
	 * @param key
	 *            ključ
	 * @param value
	 *            vrijednost
	 * @throws NullPointerException
	 *             ako je ključ <code>null</code>
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}

		int keyHash = abs(key.hashCode()) % table.length;
		if (table[keyHash] == null) {
			table[keyHash] = new TableEntry<K, V>(key, value);
			size++;
			modificationCount++;
			return;
		}

		if (table[keyHash].key.equals(key)) {
			table[keyHash].value = value;
			return;
		}

		TableEntry<K, V> prev = table[keyHash];
		TableEntry<K, V> curr = prev.next;
		while (curr != null) {
			if (curr.key.equals(key)) {
				curr.value = value;
				return;
			}
			prev = curr;
			curr = curr.next;
		}

		prev.next = new TableEntry<K, V>(key, value);
		size++;
		modificationCount++;
		occupancy++;
		if ((occupancy / table.length) >= MAX_OCCUPANCY_RATE) {
			doubleCapacity();
		}
	}

	/**
	 * Povećava kapacitet tablice na dvostruki. Ova metoda se poziva kada
	 * popunjenost tablice postane jednaka ili veća od 75% broja slotova.
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		TableEntry<K, V>[] tmpTable = table;

		table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		size = 0;
		occupancy = 0;

		for (int i = 0; i < tmpTable.length; i++) {
			TableEntry<K, V> head = tmpTable[i];
			while (head != null) {
				put(head.key, head.value);
				head = head.next;
			}
		}
	}

	/**
	 * Vraća pripadnu vrijednost danom ključu. Ako je pozvana s ključem koji u
	 * tablici ne postoji, metoda vraća <code>null</code>. Oprez, kada se kao
	 * povratna vrijednost dobije <code>null</code> nije moguće zaključiti je li
	 * rezultat takav iz razloga što ne postoji traženi par (ključ, vrijednosti) ili
	 * iz razloga što takav postoji, ali je vrijednost <code>null</code>.
	 * 
	 * @param key
	 *            ključ
	 * @return pripadna vrijednost
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int keyHash = abs(key.hashCode()) % table.length;
		TableEntry<K, V> head = table[keyHash];
		while (head != null) {
			if (head.key.equals(key)) {
				return head.value;
			}
			head = head.next;
		}

		return null;
	}

	/**
	 * Vraća broj parova u tablici.
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * Provjerava postoji li par koji ima ključ jednak danom ključu.
	 * 
	 * @param key
	 *            ključ
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int keyHash = abs(key.hashCode()) % table.length;
		TableEntry<K, V> head = table[keyHash];
		while (head != null) {
			if (head.key.equals(key)) {
				return true;
			}
			head = head.next;
		}

		return false;
	}

	/**
	 * Provjerava postoji li par koji ima vrijednost jednaku danoj vrijednosti.
	 * 
	 * @param value
	 *            vrijednost
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean containsValue(Object value) {
		if (value == null) {
			for (int i = 0; i < table.length; i++) {
				TableEntry<K, V> head = table[i];
				while (head != null) {
					if (head.value == null) {
						return true;
					}
					head = head.next;
				}
			}
		} else {
			for (int i = 0; i < table.length; i++) {
				TableEntry<K, V> head = table[i];
				while (head != null) {
					if (value.equals(head.value)) {
						return true;
					}
					head = head.next;
				}
			}
		}

		return false;
	}

	/**
	 * Uklanja iz tablice uređeni par sa zadanim ključem. Ako tablica ne sadrži par
	 * sa zadanim ključem, metoda ne radi ništa.
	 * 
	 * @param key
	 *            ključ
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int keyHash = abs(key.hashCode()) % table.length;
		if (table[keyHash] == null) {
			return;
		}

		if (table[keyHash].key.equals(key)) {
			size--;
			modificationCount++;
			if (table[keyHash].next == null) {
				table[keyHash] = null;
				occupancy--;
			} else {
				table[keyHash] = table[keyHash].next;
			}
			return;
		}

		TableEntry<K, V> prev = table[keyHash];
		TableEntry<K, V> curr = prev.next;
		while (curr != null) {
			if (curr.key.equals(key)) {
				prev.next = curr.next;
				size--;
				modificationCount++;
				return;
			}
			prev = curr;
			curr = curr.next;
		}
	}

	/**
	 * Provjerava je li tablica prazna.
	 * 
	 * @return <code>true</code> ako je, inače <code>false</code>
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Briše sve uređene parove iz kolekcije. Ova metoda ne mijenja kapacitet same
	 * tablice.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		occupancy = 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> head = table[i];
			while (head != null) {
				if (first) {
					first = false;
					sb.append(head);
				} else {
					sb.append(", " + head);
				}
				head = head.next;
			}
		}
		sb.append("]");

		return sb.toString();
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Implementacija sučelja {@link Iterator} za razred {@link SimpleHashtable}.
	 * 
	 * @author nikola
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Indeks slota u kojem se nalazi trenutni par <code>currNode</code>.
		 */
		private int currSlot;
		/**
		 * Broj prođeni parova u tablici.
		 */
		private int numOfPassed;
		/**
		 * Referenca na sljedeći par koji će se dobiti pozivom metode <code>next</code>.
		 */
		private TableEntry<K, V> nextNode;
		/**
		 * Referenca na trenutni par koji se dobio pozivom metode <code>next</code>.
		 */
		private TableEntry<K, V> currNode;
		/**
		 * Zastavica koja naznačuje je li trenutni par <code>currNode</code> izbrisan
		 * metodom <code>remove</code>.
		 */
		private boolean currNodeRemoved;
		/**
		 * Kopija varijable <code>modificationCount</code> kojom se provjerava je li se
		 * između poziva metoda razreda {@link IteratorImpl} dogodila ikakva strukturna
		 * promjena internu podatkovnu strukturu kolekcije {@link SimpleHashtable}.
		 */
		private int modificationCountCopy;

		/**
		 * Defaultni konstruktor.
		 */
		private IteratorImpl() {
			currSlot = 0;
			numOfPassed = 0;
			if (SimpleHashtable.this.size > 0) {
				while (SimpleHashtable.this.table[currSlot] == null) {
					currSlot++;
				}
				nextNode = SimpleHashtable.this.table[currSlot];
			}
			modificationCountCopy = SimpleHashtable.this.modificationCount;
		}

		/**
		 * @throws ConcurrentModificationException ako je kolekcija izvana modificirana.
		 */
		public boolean hasNext() {
			if (modificationCountCopy != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}

			return numOfPassed < SimpleHashtable.this.size;
		}

		/**
		 * @throws ConcurrentModificationException ako je kolekcija izvana modificirana.
		 */
		public TableEntry<K, V> next() {
			if (modificationCountCopy != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			currNodeRemoved = false;
			currNode = nextNode;
			numOfPassed++;

			if (!hasNext()) {
				nextNode = null;
			} else if (nextNode.next == null) {
				currSlot++;
				while (SimpleHashtable.this.table[currSlot] == null) {
					currSlot++;
				}
				nextNode = SimpleHashtable.this.table[currSlot];
			} else {
				nextNode = nextNode.next;
			}

			return currNode;
		}

		/**
		 * @throws ConcurrentModificationException ako je kolekcija izvana modificirana.
		 */
		public void remove() {
			if (modificationCountCopy != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}

			if (currNodeRemoved) {
				throw new IllegalStateException();
			}

			SimpleHashtable.this.remove(currNode.key);
			modificationCountCopy = SimpleHashtable.this.modificationCount;
			currNodeRemoved = true;
			numOfPassed--;
		}

	}

}
