package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementacija prilagodljive kolekcije objekata s potporom polja. Ovaj razred
 * nasljeđuje razred {@link Collection}. Dopušteni su duplikati objekata.
 * Pohranjivanje <code>null</code> reference nije dopušteno.
 * 
 * @author nikola
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * trenutna veličina kolekcije (broj objekata koji su zapravo pohranjeni)
	 */
	private int size;

	/**
	 * trenutni kapacitet dodijeljenog polja referenci objekta
	 */
	private int capacity;

	/**
	 * polje referenci objekata čija je duljina određena varijablom kapacitet
	 */
	private Object[] elements;

	/**
	 * pretpostavljeni kapacitet kolekcije
	 */
	public static final int DEFAULT_CAPACITY = 16;

	/**
	 * Stvara objekt s kapacitetom postavljenim na <code>16</code>.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Stvara objekt s kapacitetom postavljenim na danu vrijednost initialCapacity.
	 * 
	 * @param initialCapacity
	 *            inicijalni kapacitet
	 * @throws IllegalArgumentException
	 *             ako je kapacitet manji od <code>1</code>.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet mora biti pozitivan broj. Zadano " + initialCapacity + ".");
		}

		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * Stvara objekt s kapacitetom postavljenim na <code>16</code> i dodaje u
	 * novostvorenu kolekciju sve objekte iz dane kolekcije.
	 * 
	 * @param other
	 *            kolekcija čiji se objekti dodaju u novostvorenu kolekciju.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Stvara objekt s kapacitetom postavljenim na danu vrijednost initialCapacity i
	 * dodaje u novostvorenu kolekciju sve objekte iz dane kolekcije. Ako je
	 * initialCapacity manji od veličine dane kolekcije, za alokaciju polja elements
	 * se koristi veličina dane kolekcije.
	 * 
	 * @param other
	 *            kolekcija čiji se objekti dodaju u novostvorenu kolekciju.
	 * @param initialCapacity
	 *            inicijalni kapacitet
	 * @throws ako
	 *             je dana kolekcija <code>null</code>;
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException("Dana kolekcija ne smije biti 'null'.");
		}

		this.capacity = initialCapacity > other.size() ? initialCapacity : other.size();
		elements = new Object[capacity];
		addAll(other);
	}

	public int size() {
		return size;
	}

	/**
	 * Dodaje zadani objekt u kolekciju. Referenca se dodaje na prvo slobodno prazno
	 * mjesto u polju elements; ako je polje elements puno, realocira je
	 * udvostručavanjem njegove veličine. Metoda odbija dodavanje <code>null</code>
	 * referenci bacanjem iznimke {@link NullPointerException}. Prosječna složenost
	 * metode je O(1).
	 * @throws NullPointerException ako je dodaje <code>null</code>.
	 */
	public void add(Object value) {
		insert(value, size);
	}

	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index == -1) {
			return false;
		}

		remove(index);
		return true;
	}

	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Vraća objekt koji je pohranjen u polju na poziciji index. Valjane vrijednosti
	 * index su od 0 do size-1. Ako index nije valjan, implementacija baca iznimku
	 * {@link IndexOutOfBoundsException}. Prosječna složenost ove metode je O(1).
	 * 
	 * @param index indeks objekta u kolekciji.
	 * @return objekt na poziciji index u kolekciji.
	 */
	public Object get(int index) {
		checkIndex(index, 0, size);

		return elements[index];
	}

	/**
	 * Uklanja sve objekte iz kolekcije.
	 * 
	 * Alocirano polje je ostavljeno na trenutnom kapacitetu. Postavlja veličinu
	 * polja na <code>0</code>; piše <code>null</code> reference u polje tako da ih
	 * sakupljač smeća može obrisati. Ne alocira novo polje.
	 * 
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

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

		if (size == capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}

		for (int i = size - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}

		elements[position] = value;
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
	 * @param value objekt kolekcije.
	 * @return indeks danog objekta u kolekciji.
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
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

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		size--;
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
