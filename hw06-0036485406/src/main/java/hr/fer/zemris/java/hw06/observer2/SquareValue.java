package hr.fer.zemris.java.hw06.observer2;

/**
 * Instance razreda {@link SquareValue} ispisuju kvadrat int vrijednosti
 * pohranjene u {@link IntegerStorage} na standardni izlaz.
 * 
 * @author nikola
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getCurrValue();
		System.out.println("Provided new value: " + value + ", square is " + (value * value));
	}

}
