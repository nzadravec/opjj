package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Class represents polynomial f(z) of shape z_n*z^n+z_{n-1}*z^{n-1}+...+z_2*z^2
 * +z_1*z+z_0 over complex numbers, where z_1, z_2, ...,z_n are
 * factors/coefficients that write with the corresponding powers of z. All
 * factors are given as complex numbers, and even z is a complex number. The
 * method {@link #apply(Complex)} takes concrete z and calculates the
 * value of the polynomial at that point.
 * 
 * @author nikola
 *
 */
public class ComplexPolynomial {

	/**
	 * Polynomial factors/coefficients
	 */
	private Complex[] factors;

	/**
	 * Creates arbitrary polynomial.
	 * 
	 * @param factors polynomial factors/coefficients
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns polynomial order.
	 * 
	 * @return polynomial order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Returns new polynomial that is equal to this * p.
	 * 
	 * @param p second operand
	 * @return new polynomial equal to this * p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] factors = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < factors.length; i++) {
			factors[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				factors[i + j] = factors[i + j].add(this.factors[i].add(p.factors[j]));
			}
		}
		return new ComplexPolynomial(factors);
	}

	/**
	 * Returns first derivative of this.
	 * 
	 * @return first derivative of this
	 */
	public ComplexPolynomial derive() {
		Complex[] factors = new Complex[this.order()];
		for (int i = 0; i < factors.length; i++) {
			factors[i] = this.factors[i + 1].multiply(new Complex(i + 1.0, 0.0));
		}
		return new ComplexPolynomial(factors);
	}

	/**
	 * Returns value of the polynomial at given point z.
	 * 
	 * @param z
	 *            given point
	 * @return value of the polynomial at z
	 */
	public Complex apply(Complex z) {
		Complex value = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			value = value.add(factors[i].multiply(z.power(i)));
		}
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append("(" + factors[i] + ")").append("z^" + i);
			if (i != 0) {
				sb.append("+");
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
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
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (!Arrays.equals(factors, other.factors))
			return false;
		return true;
	}

}
