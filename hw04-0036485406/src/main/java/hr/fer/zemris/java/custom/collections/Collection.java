package hr.fer.zemris.java.custom.collections;

/**
 * Razred predstavlja generalnu kolekciju objekata.
 * 
 * @author nikola
 *
 */
public class Collection {

	/**
	 * Pretpostavljeni konstruktor.
	 */
	protected Collection() {

	}

	/**
	 * Ispituje je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako kolekcija ne sadrži objekte, inače
	 *         <code>false</code>.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Vraća broj trenutno pohranjenih objekata u kolekciji.
	 * 
	 * Ovdje je implementirana da uvijek vraća <code>0</code>. Očekuje se od razreda
	 * koji će naslijediti ovaj da ponudi korektnu implementaciju.
	 * 
	 * @return uvijek vraća <code>0</code>.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Dodaje zadani objekt u kolekciju.
	 * 
	 * Ovdje je implementirana kao prazna. Očekuje se od razreda koji će naslijediti
	 * ovaj da ponudi korektnu implementaciju.
	 * 
	 * @param value
	 *            objekt koji se dodaje.
	 */
	public void add(Object value) {

	}

	/**
	 * Ispituje sadrži li kolekcija dani objekt.
	 * 
	 * Vraća <code>true</code> samo ako kolekcija sadrži dani objekt što je određeno
	 * metodom <code>equals</code>.
	 * 
	 * Ovdje je implementirana da uvijek vraća <code>false</code>. Očekuje se od
	 * razreda koji će naslijediti ovaj da ponudi korektnu implementaciju.
	 * 
	 * @param value
	 *            objekt koji se ispituje.
	 * @return <code>true</code> ako kolekcija sadrži dani objekt, inače
	 *         <code>false</code>.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Uklanja dani objekt iz kolekcije.
	 * 
	 * <p>Vraća <code>true</code> samo ako kolekcija sadrži dani objekt što je određeno
	 * metodom <code>equals</code> i uklanja jedno njegovo pojavljivanje (u ovom
	 * razredu nije specificirano koje).</p>
	 * 
	 * <p>Ovdje je implementirana da uvijek vraća <code>false</code>. Očekuje se od
	 * razreda koji će naslijediti ovaj da ponudi korektnu implementaciju.</p>
	 * 
	 * @param value
	 *            objekt koji se uklanja.
	 * @return
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Vraća kolekciju zapisanu u obliku polja.
	 * 
	 * Alocira novo polje veličine jednake veličini ove kolekcije, popuni je sa
	 * sadržajem kolekcije i vraća to polje. Ova metoda nikada ne vraća
	 * <code>null</code>.
	 * 
	 * Ovdje je implementirana da baca iznimku
	 * {@link UnsupportedOperationException}. Očekuje se od razreda koji će
	 * naslijediti ovaj da ponudi korektnu implementaciju.
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 *             uvijek
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Poziva <code>processor.process(.)</code> za svaki objekt ove kolekcije.
	 * 
	 * Redoslijed kojim će se objekti slati je nedefiniran u ovom razredu. Ovdje je
	 * implementirana kao prazna. Očekuje se od razreda koji će naslijediti ovaj da
	 * ponudi korektnu implementaciju.
	 * 
	 * @param processor
	 *            objekt nad kojim se za svaki objekt kolekcije poziva metoda
	 *            <code>process</code>.
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Dodaje u trenutnu kolekciju sve objekte iz dane kolekcije.
	 * 
	 * Dana kolekcija ostaje nepromijenjena.
	 * 
	 * @param other
	 *            kolekcija čiji se objekti dodaju u trenutnu kolekciju.
	 */
	public void addAll(Collection other) {

		class AddProcessor extends Processor {

			public void process(Object value) {
				add(value);
			}

		}

		other.forEach(new AddProcessor());
	}

	/**
	 * Uklanja sve objekte iz ove kolekcije.
	 * 
	 * Ovdje je implementirana kao prazna. Očekuje se od razreda koji će naslijediti
	 * ovaj da ponudi korektnu implementaciju.
	 * 
	 */
	public void clear() {

	}

}
