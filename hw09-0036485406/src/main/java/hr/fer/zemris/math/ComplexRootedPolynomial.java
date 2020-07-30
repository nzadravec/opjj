package hr.fer.zemris.math;

import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.math.Util.Combinations;

/**
 * Class represents polynomial f(z) of shape (z-z_1)*(z-z_2)*...*(z-z_n), where
 * z_1, z_2, ...,z_n are roots/nullpoints. All roots are given as complex
 * numbers, and even z is a complex number. The method
 * {@link #apply(Complex)} takes concrete z and calculates the value of
 * the polynomial at that point.
 * 
 * @author nikola
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Polynomial roots/nullpoints.
	 */
	private Complex[] roots;

	/**
	 * Creates arbitrary {@link ComplexRootedPolynomial}.
	 * 
	 * @param roots
	 *            polynomial roots/nullpoints
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Returns value of the polynomial at given point z.
	 * 
	 * @param z
	 *            given point
	 * @return value of the polynomial at z
	 */
	public Complex apply(Complex z) {
		Complex value = Complex.ONE;
		for (Complex root : roots) {
			value = value.multiply(z.sub(root));
		}
		return value;
	}

	/**
	 * Returns {@link ComplexPolynomial} of this.
	 * 
	 * @return {@link ComplexPolynomial} of this
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];
		for (int i = 0; i < factors.length - 1; i++) {
			int choose = roots.length - i;
			Iterator<List<Complex>> iter = new Combinations<Complex>(roots, choose);
			Complex factor = Complex.ZERO;
			while (iter.hasNext()) {
				List<Complex> comb = iter.next();
				Complex c = Complex.ONE;
				for (int j = 0; j < comb.size(); j++) {
					c = c.multiply(comb.get(j).negate());
				}
				factor = factor.add(c);
			}
			factors[i] = factor;
		}
		factors[factors.length - 1] = Complex.ONE;
		return new ComplexPolynomial(factors);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < roots.length; i++) {
			if (i != 0) {
				sb.append("*");
			}
			sb.append("(z-(" + roots[i] + "))");
		}
		return sb.toString();
	}

	/**
	 * Returns index of closest root for given complex number z that is treshold; if
	 * there is no such root, returns -1.
	 * 
	 * @param z
	 *            given complex number
	 * @param treshold
	 *            treshold
	 * @return index of closest root for z
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double closestDistance = treshold;
		for (int i = 0; i < roots.length; i++) {
			double distance = roots[i].sub(z).module();
			if (distance < closestDistance) {
				closestDistance = distance;
				index = i + 1;
			}
		}

		return index;
	}

}
