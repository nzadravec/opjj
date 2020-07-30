package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The model incrementally generates prim numbers (by creation, the model only
 * contains number 1). For each call method {@link #next()} model in the list of
 * numbers adds the first next prim number.
 * 
 * @author nikola
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Current prim number (1 is not prim number)
	 */
	private int currPrime = 1;
	
	/**
	 * Prim number list.
	 */
	private List<Integer> primeList = new ArrayList<>();

	/**
	 * Listeners list.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public Integer getElementAt(int index) {
		if (index >= primeList.size()) {
			throw new IllegalArgumentException();
		}

		return primeList.get(index);
	}

	@Override
	public int getSize() {
		return primeList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Generates next prim number after <code>currPrime</code>.
	 * 
	 * @return next prim number
	 */
	public int next() {
		int number = currPrime + 1;
		while (!isPrime(number)) {
			number++;
		}
		currPrime = number;
		int pos = primeList.size();
		primeList.add(currPrime);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		List<ListDataListener> listenersCopy = new ArrayList<>(listeners);
		for (ListDataListener l : listenersCopy) {
			l.intervalAdded(event);
		}

		return currPrime;
	}

	/**
	 * Checks if given number is prim.
	 * 
	 * @param number
	 *            given number.
	 * @return <code>true</code> if given number is true, else <code>false</code>
	 */
	public boolean isPrime(int number) {
		if (number == 2) {
			return true;
		}

		if (number < 2 || (number % 2) == 0) {
			return false;
		}

		for (int prim : primeList) {
			if ((number % prim) == 0) {
				return false;
			}
		}

		return true;
	}

}
