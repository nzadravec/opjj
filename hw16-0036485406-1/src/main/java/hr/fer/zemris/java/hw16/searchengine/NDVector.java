package hr.fer.zemris.java.hw16.searchengine;

import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * N-Dimensional vector.
 * 
 * @author nikola
 *
 */
public class NDVector {

	/**
	 * Vectors compoments.
	 */
	private double[] elements;

	/**
	 * Creates {@link NDVector} of n-th dimesion.
	 * 
	 * @param n
	 *            vector dimension
	 */
	public NDVector(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("vector dimension can't be negative");
		}

		elements = new double[n];
	}

	/**
	 * Creats {@link NDVector} of components equals given array.
	 * 
	 * @param elements
	 *            vectors components
	 */
	public NDVector(double[] elements) {
		Objects.requireNonNull(elements, "elements object must not be null");

		System.arraycopy(elements, 0, this.elements, 0, elements.length);
	}

	/**
	 * Gets vectors value at given index.
	 * 
	 * @param index
	 *            index
	 * @return vectors value at index
	 */
	public double get(int index) {
		checkIndex(index);

		return elements[index];
	}

	/**
	 * Sets new value in vector at given index.
	 * 
	 * @param index
	 *            index
	 * @param value
	 *            new value
	 */
	public void set(int index, double value) {
		checkIndex(index);

		elements[index] = value;
	}

	/**
	 * Multiplies this and other element-wise and stores result in this.
	 * 
	 * @param other
	 *            second operand
	 */
	public void multiply(NDVector other) {
		checkIfNull(other);
		checkCompatibility(other);
		
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = this.elements[i] * other.elements[i];
		}
	}

	/**
	 * Returns vectors norm ("length").
	 * 
	 * @return vectors norm
	 */
	public double norm() {
		return sqrt(this.dot(this));
	}

	/**
	 * Returns scalar product of this and other.
	 * 
	 * @param other
	 *            second operand
	 * @return scalar product of this and other
	 */
	public double dot(NDVector other) {
		checkIfNull(other);
		checkCompatibility(other);

		double scalarProduct = 0;
		for (int i = 0; i < this.elements.length; i++) {
			scalarProduct += this.elements[i] * other.elements[i];
		}

		return scalarProduct;
	}

	/**
	 * Returns cosine of the angle of this and other.
	 * 
	 * @param other
	 *            second operand
	 * @return cosine of the angle of this and other
	 */
	public double cosAngle(NDVector other) {
		checkIfNull(other);
		checkCompatibility(other);

		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Throws {@link NullPointerException} if given vector is null.
	 * 
	 * @param other
	 *            checked vector
	 */
	private void checkIfNull(NDVector other) {
		Objects.requireNonNull(other, "Other vector must not be null");
	}

	/**
	 * Throws {@link IllegalArgumentException} if this and other are not same
	 * dimension.
	 * 
	 * @param other
	 *            checked vector
	 */
	private void checkCompatibility(NDVector other) {
		if (this.elements.length != other.elements.length) {
			throw new IllegalArgumentException("Other vector must be same dimension as this");
		}
	}

	/**
	 * Throws {@link IllegalArgumentException} if index is not between
	 * <code>0</code> and <code>elements.length - 1</code>.
	 * 
	 * @param index
	 *            index
	 */
	private void checkIndex(int index) {
		if (index < 0 || index >= elements.length) {
			throw new IllegalArgumentException(
					"Valid index values are integers between 0 and " + (elements.length - 1));
		}
	}

}
