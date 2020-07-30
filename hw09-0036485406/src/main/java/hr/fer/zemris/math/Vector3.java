package hr.fer.zemris.math;

import static java.lang.Math.sqrt;

import java.util.Objects;

/**
 * Class represents unmodified 3D vector.
 * 
 * @author nikola
 *
 */
public class Vector3 {

	/**
	 * Coordinates
	 */
	private double x;
	private double y;
	private double z;

	/**
	 * Creates arbitrary 3D vector.
	 * 
	 * @param x
	 *            component
	 * @param y
	 *            component
	 * @param z
	 *            component
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns vectors norm ("length").
	 * 
	 * @return vectors norm
	 */
	public double norm() {
		return sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns normalized vector. Normalized vector has {@link #norm(Vector3)} equal
	 * <code>1</code>.
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double n = norm();
		if (n < 1E-9) {
			return new Vector3(0, 0, 0);
		} else {
			return new Vector3(x / n, y / n, z / n);
		}
	}

	/**
	 * Returns new vetor that is equal to this + other.
	 * 
	 * @param other second operand
	 * @return new vetor equal to this + other
	 */
	public Vector3 add(Vector3 other) {
		checkIfNull(other);
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Returns new vetor that is equal to this - other.
	 * 
	 * @param other second operand
	 * @return new vetor equal to this - other
	 */
	public Vector3 sub(Vector3 other) {
		checkIfNull(other);
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Returns scalar product of this and other.
	 * 
	 * @param other second operand
	 * @return scalar product of this and other
	 */
	public double dot(Vector3 other) {
		checkIfNull(other);
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Resturs new vector that is equal to vector product of this and other.
	 * 
	 * @param other second operand
	 * @return new vetor equal to vector product of this and other
	 */
	public Vector3 cross(Vector3 other) {
		checkIfNull(other);
		double x = this.y * other.z - this.z * other.y;
		double y = -(this.x * other.z - this.z * other.x);
		double z = this.x * other.y - this.y * other.x;
		return new Vector3(x, y, z);
	}

	/**
	 * Returns new vetor that is equal to s * this.
	 * 
	 * @param s scale
	 * @return new vetor equal to s * this
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Returns cosine of the angle of this and other.
	 * 
	 * @param other second operand
	 * @return cosine of the angle of this and other
	 */
	public double cosAngle(Vector3 other) {
		checkIfNull(other);
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Returns x compoment.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y compoment.
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns z compoment.
	 * 
	 * @return z component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns array of vectors components. 
	 * 
	 * @return array of vectors components
	 */
	public double[] toArray() {
		double[] array = new double[3];
		array[0] = x;
		array[1] = y;
		array[2] = z;
		return array;
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	/**
	 * Checks if other is null.
	 * 
	 * @param other checked vector
	 */
	private void checkIfNull(Vector3 other) {
		Objects.requireNonNull(other, "Other vector must not be null.");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
