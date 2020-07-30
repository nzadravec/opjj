package hr.fer.zemris.java.hw06.observer2;

public class IntegerStorageChange {
	
	private IntegerStorage istorage;
	private int prevValue;
	private int currValue;
	
	public IntegerStorageChange(IntegerStorage istorage, int prevValue, int currValue) {
		super();
		this.istorage = istorage;
		this.prevValue = prevValue;
		this.currValue = currValue;
	}

	public IntegerStorage getIstorage() {
		return istorage;
	}

	public int getPrevValue() {
		return prevValue;
	}

	public int getCurrValue() {
		return currValue;
	}
	
}
