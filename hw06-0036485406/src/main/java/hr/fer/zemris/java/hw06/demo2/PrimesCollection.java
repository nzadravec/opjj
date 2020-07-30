package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja raspon prim brojeva od prvog <code>2</code>, do n-tog.
 * 
 * @author nikola
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Broj uzastopnih prim brojeva koje moraju biti u ovoj kolekciji.
	 */
	private int numOfPrimes;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param numOfPrimes
	 */
	public PrimesCollection(int numOfPrimes) {
		super();
		this.numOfPrimes = numOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}

	/**
	 * Iterator prim brojeva.
	 * 
	 * @author nikola
	 *
	 */
	private class PrimesIterator implements Iterator<Integer> {

		/**
		 * Brojač prim brojeva.
		 */
		private int counter;
		/**
		 * Trenutni prim broj.
		 */
		private int currPrime;

		@Override
		public boolean hasNext() {
			return counter < numOfPrimes;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			int number = currPrime + 1;
			while (!isPrime(number)) {
				number++;
			}
			currPrime = number;

			counter++;
			return currPrime;
		}

	}

	/**
	 * Ispituje je li dani broj prim broj.
	 * 
	 * @param number dani broj
	 * @return <code>true</code> ako je dani broj prim broj, inače <code>false</code>
	 */
	public static boolean isPrime(int number) {
		if (number < 2) {
			return false;
		}

		for (int i = 2; i < number; i++) {
			if ((number % i) == 0) {
				return false;
			}
		}

		return true;
	}

}
