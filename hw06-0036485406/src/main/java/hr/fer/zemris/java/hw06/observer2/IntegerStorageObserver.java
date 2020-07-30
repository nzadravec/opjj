package hr.fer.zemris.java.hw06.observer2;

/**
 * Sučenje Promatrač.
 * 
 * @author nikola
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Metoda koja služi za dojavljuvanje promjene stanja objekta razreda
	 * {@link IntegerStorage}.
	 * 
	 * @param istorage
	 *            promjena stanja
	 */
	public void valueChanged(IntegerStorageChange istorage);

}
