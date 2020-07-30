package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred predstavlja subjekt u oblikovnom obrascu Promatrač.
 * 
 * @author nikola
 *
 */
public class IntegerStorage {

	/**
	 * Pohranjena vrijendnost.
	 */
	private int value;

	/**
	 * Lista promatrača.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param initialValue
	 *            početna vrijednost koja se pohranjuje
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Dodaje danog promatrača u listu. Dani promatrač se dodaje u listu ako ga
	 * lista se sadrži.
	 * 
	 * @param observer
	 *            promatrač
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Briše danog promatrača iz liste. Metoda ne radi ništa ako ne sadrži danog
	 * promatrača.
	 * 
	 * @param observer
	 *            promatrač
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Briše sve promatrače iz liste.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Vraća pohranjenu vrijednost.
	 * 
	 * @return pohranjenu vrijednost
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Postavlja člansku varijablu <code>value</code> na novu vrijednost. Ako je
	 * nova vijednost drugačija od trenutno pohranjene, objekt dojavljuje promjenu
	 * stanja svim promatračima.
	 * 
	 * @param value
	 *            nova vrijednost
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange istorageChange = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observersCopy) {
					observer.valueChanged(istorageChange);
				}
			}
		}
	}

}
