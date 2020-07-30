package hr.fer.zemris.java.hw06.observer2;

/**
 * Instance razreda {@link ChangeCounter} broje (i ispisuju na standardni izlaz)
 * broj puta koliko se vrijednost pohranjena u {@link IntegerStorage} promjenila
 * od registracije u {@link IntegerStorage}.
 * 
 * @author nikola
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Brojač.
	 */
	private int counter;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
