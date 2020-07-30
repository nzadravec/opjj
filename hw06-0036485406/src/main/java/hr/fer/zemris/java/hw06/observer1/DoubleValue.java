package hr.fer.zemris.java.hw06.observer1;

public class DoubleValue implements IntegerStorageObserver {
	
	private int counter;
	
	public DoubleValue(int counter) {
		this.counter = counter;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(counter > 0) {
			counter--;
			System.out.println("Double value: " + istorage.getValue() * 2);
		} else {
			istorage.removeObserver(this);
		}
	}

}
