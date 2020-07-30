package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Util {

	/**
	 * Represents {@link Iterator} that iterates all n choose k combinations, where
	 * n is array of <code>T</code> items and k is number of items in single combination.
	 * 
	 * @author nikola
	 *
	 * @param <T> item type
	 */
	public static class Combinations<T> implements Iterator<List<T>> {

		/**
		 * Indices of items that will be in new combination returned by {@link #next()}
		 */
		private int[] indices;
		/**
		 * Array of items
		 */
		private T[] itemset;
		/**
		 * Number of items in single combination
		 */
		private int choose;
		/**
		 * Flag if this has next element
		 */
		private boolean hasNext = true;

		/**
		 * Creates {@link Combinations} object.
		 * 
		 * @param itemset array of items
		 * @param choose number of items in single combination
		 */
		public Combinations(T[] itemset, int choose) {
			Objects.requireNonNull(itemset, "Itemset must not be null.");
			if (choose < 1) {
				throw new IllegalArgumentException("Choose must be positive.");
			}

			if (itemset.length < choose) {
				throw new IllegalArgumentException("Itemset size must be greater or equal to choose.");
			}

			this.itemset = itemset;
			this.choose = choose;

			// Initialize indices
			indices = new int[choose];
			for (int i = 0; i < indices.length; i++) {
				indices[i] = i;
			}
		}

		/**
		 * Returns rightmost index.
		 * 
		 * @return rightmost index
		 */
		private int findRightMostIndex() {
			for (int i = choose - 1; i >= 0; i--) {
				int bounds = itemset.length - choose + i;
				if (indices[i] < bounds) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * Prepares indices for next element.
		 */
		private void prepareNext() {
			int rightMostIndex = findRightMostIndex();

			// increment all indices
			if (rightMostIndex >= 0) {
				indices[rightMostIndex]++;
				for (int i = rightMostIndex + 1; i < choose; i++) {
					indices[i] = indices[i - 1] + 1;
				}
				// there are still more combinations
				hasNext = true;
				return;
			}
			// reached the end, no more combinations
			hasNext = false;
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public List<T> next() {
			if (!hasNext) {
				throw new NoSuchElementException();
			}

			List<T> combination = new ArrayList<>();
			for (int index : indices) {
				combination.add(itemset[index]);
			}

			prepareNext();
			return combination;
		}

	}

}
