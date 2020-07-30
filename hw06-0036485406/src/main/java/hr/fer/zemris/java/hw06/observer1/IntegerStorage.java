package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers;

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	public void clearObservers() {
		observers.clear();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observersCopy){
					observer.valueChanged(this);
				}
			}
		}
	}

}
