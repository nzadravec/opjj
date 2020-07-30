package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementacija kolekcije objekata s potporom povezane liste. Ovaj razred
 * nasljeđuje razred {@link Collection}. Dopušteni su duplikati objekata.
 * Pohranjivanje <code>null</code> reference nije dopušteno.
 * 
 * @author nikola
 *
 */
public class LinkedListIndexedCollection extends Collection {

	private static class ListNode {
		private ListNode prev;
		private ListNode next;
		private Object value;

		public ListNode(ListNode prev, ListNode next, Object value) {
			super();
			this.prev = prev;
			this.next = next;
			this.value = value;
		}
	}

	/**
	 * trenutna veličina kolekicje (broj objekata koji su zapravo pohranjeni)
	 */
	private int size;

	/**
	 * referenca na prvi čvor vezane liste
	 */
	private ListNode first;

	/**
	 * referenca na zadnji čvor vezane liste
	 */
	private ListNode last;

	/**
	 * Stvara praznu kolekciju s <code>first=last=null</code>.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Stvara objekt i dodaje u novostvorenu kolekciju sve objekte iz dane
	 * kolekcije.
	 * 
	 * @param other
	 *            kolekcija čiji se objekti dodaju u novostvorenu kolekciju.
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) {
			throw new NullPointerException("Dana kolekcija ne smije biti 'null'.");
		}
		
		addAll(other);
	}

	public int size() {
		return size;
	}

	/**
	 * Dodaje zadani objekt u kolekciju. Referenca se dodaje na kraj kolekcije;
	 * novododani objekt postaje element na najvećem indeksu. Metoda odbija
	 * dodavanje <code>null</code> referenci bacanjem iznimke
	 * {@link NullPointerException}. Prosječna složenost metoda je O(1).
	 */
	public void add(Object value) {
		insert(value, size);
	}

	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Vraća objekt koji je pohranjen u polju na poziciji index. aljane vrijednosti
	 * index su od 0 do size-1. Ako index nije valjan, implementacija baca iznimku
	 * {@link IndexOutOfBoundsException}. Složenost ove metode je uvijek ispod
	 * n/2+1.
	 * 
	 * @param index
	 * @return
	 */
	public Object get(int index) {
		checkIndex(index, 0, size);

		ListNode tmp = nodeAt(index);
		return tmp.value;
	}

	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}

		ListNode tmp = first;
		for (; tmp != null; tmp = tmp.next) {
			if (tmp.value.equals(value)) {
				break;
			}
		}

		if (tmp == null) {
			return false;
		}

		ListNode succ = tmp.next;
		ListNode pred = tmp.prev;
		succ.prev = pred;
		pred.next = succ;
		size--;

		return true;
	}

	public Object[] toArray() {
		Object[] copy = new Object[size];
		ListNode tmp = first;
		for (int i = 0; i < size; i++) {
			copy[i] = tmp.value;
			tmp = tmp.next;
		}

		return copy;
	}

	public void forEach(Processor processor) {
		for (ListNode tmp = first; tmp != null; tmp = tmp.next) {
			processor.process(tmp.value);
		}
	}

	/**
	 * Uklanja sve elemente iz kolekcije. Kolekcija "zaboravi" na trenutnu vezanu
	 * listu.
	 */
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Ubacuje (ne prebrisuje) dani objekt na danu poziciju u polje. Objekti na
	 * danoj poziciji i većim pozicijama se pomiću za jedno mjesto prema kraju.
	 * Legalni poziciju su od <code>0</code> do size (oba su uključena). Ako
	 * pozicija nije važeća, baca se iznimka {@link IndexOutOfBoundsException}. Osim
	 * razlike u položaju na kojem će se umetnuti predmet, sve ostalo bi trebalo
	 * biti u skladu s metodom dodavanja. Prosječna složenost metode je O(n).
	 * 
	 * @param value
	 *            objekt koji se želi ubaciji na danu poziciju.
	 * @param position
	 *            pozicija.
	 * @throws IndexOutOfBoundsException
	 *             ako je pozicija manja od 0 i veća od size.
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "Nije dopušteno spremanje null reference.");
		checkIndex(position, 0, size + 1);

		if (size == 0) {
			first = last = new ListNode(null, null, value);
			size++;
			return;
		}

		if (position == 0) {
			ListNode newNode = new ListNode(null, first, value);
			first.prev = newNode;
			first = newNode;
			size++;
			return;
		}

		if (position == size) {
			ListNode newNode = new ListNode(last, null, value);
			last.next = newNode;
			last = newNode;
			size++;
			return;
		}

		ListNode succ = nodeAt(position);
		ListNode pred = succ.prev;

		ListNode newNode = new ListNode(pred, succ, value);
		pred.next = newNode;
		succ.prev = newNode;
		size++;
	}

	/**
	 * Vraća indeks danog objekta u kolekciji.
	 * 
	 * Pretražuje kolekciju i vraća indeks prvog pojavljivanja dane vrijednosti ili
	 * -1 ako vrijednost nije nađena. Argument može biti <code>null</code> i
	 * rezultat će biti da objekt nije nađen (jer kolekcija ne može sadržavati
	 * <code>null</code>). Jednakost se određuje metodom equals. Prosječna složenost
	 * metode je O(n).
	 * 
	 * @param value
	 *            objekt kolekcije.
	 * @return indeks danog objekta u kolekciji.
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		ListNode tmp = first;
		for (int i = 0; i < size; i++) {
			if (tmp.value.equals(value)) {
				return i;
			}
			tmp = tmp.next;
		}

		return -1;
	}

	/**
	 * Uklanja objekt na danom indeksu iz kolekcije.
	 * 
	 * Objekt koje je prije bio na lokaciji index+1 poslije ove operacije je na
	 * lokaciji index, itd. Valjani indeksi su od 0 do size-1. U slučaju nevažećeg
	 * indeksa baca iznimku {@link IndexOutOfBoundsException}.
	 * 
	 * @param index indeks objekta u kolekciji koji se uklanja.
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od size-1.
	 */
	public void remove(int index) {
		checkIndex(index, 0, size);

		if (index == 0) {
			first.next.prev = null;
			first = first.next;
			size--;
			return;
		}

		if (index == size - 1) {
			last.prev.next = null;
			last = last.prev;
			size--;
			return;
		}

		ListNode tmp = nodeAt(index);
		ListNode succ = tmp.next;
		ListNode pred = tmp.prev;

		pred.next = succ;
		succ.prev = pred;
		size--;
	}

	/**
	 * Vraća referencu na čvor na poziciji index u kolekciji.
	 * 
	 * @param index indeks čvora u kolekciji.
	 * @return referencu čvora na poziciji index.
	 */
	private ListNode nodeAt(int index) {
		ListNode tmp = null;
		if (index < size / 2) {
			tmp = first;
			for (int i = 0; i < index; i++) {
				tmp = tmp.next;
			}
		} else {
			tmp = last;
			for (int i = size - 1; i > index; i--) {
				tmp = tmp.prev;
			}
		}

		return tmp;
	}

	/**
	 * Ispituje je li index unutar granica (uključivo) lowerBound i (isključivo)
	 * upperBound.
	 * 
	 * @param index
	 *            indeks elementa.
	 * @param lowerBound
	 *            donja granica.
	 * @param upperBound
	 *            gornja granica.
	 * @throws IndexOutOfBoundsException
	 *             ako index nije unutar granica.
	 */
	private void checkIndex(int index, int lowerBound, int upperBound) {
		if (index < lowerBound || index >= upperBound) {
			throw new IndexOutOfBoundsException(
					"Granice su [" + lowerBound + ", " + upperBound + ">, a zadan indeks " + index + ".");
		}
	}

}
