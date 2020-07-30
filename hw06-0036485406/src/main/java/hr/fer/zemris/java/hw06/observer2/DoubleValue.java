package hr.fer.zemris.java.hw06.observer2;

/**
 * Istance razreda {@link DoubleValue} ispisuju na standardni izlaz dvostruku
 * vrijednost trenutne vrijendnosti pohranjene u {@link IntegerStorage}, ali
 * samo prvih n puta od registracije u {@link IntegerStorage}. Nakon ispisa
 * dvostruke vrijednosti po n-ti put, promatrač se automatski deregistrira iz
 * {@link IntegerStorage}.
 * 
 * @author nikola
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	private int counter;

	public DoubleValue(int counter) {
		this.counter = counter;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (counter > 0) {
			counter--;
			System.out.println("Double value: " + istorage.getCurrValue() * 2);
		}
	}

}
