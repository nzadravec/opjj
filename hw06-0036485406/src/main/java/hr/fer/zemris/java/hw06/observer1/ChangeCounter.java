package hr.fer.zemris.java.hw06.observer1;

public class ChangeCounter implements IntegerStorageObserver {
	
	private int counter;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
