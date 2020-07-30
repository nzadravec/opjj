package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents unmodified complex number.
 * 
 * @author nikola
 *
 */
public class Complex {

	/**
	 * Real part
	 */
	private double re;
	/**
	 * Imaginary part
	 */
	private double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Creates complex number 0+0j.
	 */
	public Complex() {
		super();
	}

	/**
	 * Creates arbitrary 3D vector.
	 * 
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns module this.
	 * 
	 * @return module of this
	 */
	public double module() {
		return sqrt(re * re + im * im);
	}

	/**
	 * Returns new complex number that is equal to this * c.
	 * 
	 * @param c
	 *            second operand
	 * @return new complex number equal to this * c
	 */
	public Complex multiply(Complex c) {
		double re = this.re * c.re - this.im * c.im;
		double im = this.re * c.im + this.im * c.re;
		return new Complex(re, im);
	}

	/**
	 * Returns new complex number that is equal to this / c.
	 * 
	 * @param c
	 *            second operand
	 * @return new complex number equal to this / c
	 */
	public Complex divide(Complex c) {
		double module;
		module = this.module() / c.module();
		double angle = this.angle() - c.angle();
		return new Complex(module * cos(angle), module * sin(angle));
	}

	/**
	 * Returns new complex number that is equal to this + c.
	 * 
	 * @param c
	 *            second operand
	 * @return new complex number equal to this + c
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Returns new complex number that is equal to this - c.
	 * 
	 * @param c
	 *            second operand
	 * @return new complex number equal to this - c
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Returns new complex number that is equal to -this.
	 * 
	 * @param c
	 *            second operand
	 * @return new complex number equal to -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Returns new complex number that is equal to this^n.
	 * 
	 * @param n
	 *            power/potency
	 * @return new complex number equal to this^n
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must not be negative.");
		}

		double module = pow(this.module(), n);
		double angle = n * this.angle();
		return new Complex(module * cos(angle), module * sin(angle));
	}

	/**
	 * Returns n-th root of this.
	 * 
	 * @param n
	 *            root
	 * @return n-th root of this
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Broj korijena mora biti pozitivan broj.");
		}

		double module = pow(module(), 1.0 / n);
		double angle = angle();
		List<Complex> cs = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			cs.add(new Complex(module * cos((angle + 2 * PI * i) / (double) n),
					module * sin((angle + 2 * PI * i) / n)));
		}
		return cs;
	}

	@Override
	public String toString() {
		if (re == 0) {
			return im + "i";
		}
		if (im == 0) {
			return re + "";
		}
		return im > 0 ? re + "+" + im + "i" : re + im + "i";
		// return String.format("%g%+gi", re, im);
	}

	/**
	 * Returns angle of this.
	 * 
	 * @return angle of this
	 */
	private double angle() {
		double angle = atan2(im, re);
		return angle >= 0 ? angle : (angle + 2 * PI);
	}

	/**
	 * Returns complex number parsed from string s.
	 * 
	 * Supported syntax for complex numbers is of form a+ib or a-ib where parts that
	 * are zero can be dropped, but not both (empty string is not legal complex
	 * number); for example, zero can be given as 0, i0, 0+i0, 0-i0. If there is 'i'
	 * present but no b is given, method assumes that b=1.
	 * 
	 * @param s
	 *            parsed string
	 * @return complex number
	 */
	public static Complex parse(String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		if (s.length() == 0) {
			throw new IllegalArgumentException("Empty string is not legal complex number.");
		}

		double re = 0.0;
		double im = 0.0;

		s = s.trim();
		s = s.replace(" ", "");

		int index = s.indexOf("+");
		if (index == -1) {
			index = s.lastIndexOf("-");
		}

		if (index == -1 || index == 0) {
			// only real or imag
			index = s.indexOf("i");
			if (index == -1) {
				// real
				re = parseReal(s);
			} else {
				im = parseImag(s);
			}

			return new Complex(re, im);
		}

		String realPart = s.substring(0, index);
		re = parseReal(realPart);

		String imagPart = s.substring(index);
		im = parseImag(imagPart);

		return new Complex(re, im);
	}

	/**
	 * Parses real part of complex number from string s.
	 * 
	 * @param s parsed string
	 * @return real part
	 */
	private static double parseReal(String s) {

		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(s + " cant parse to double.");
		}
	}

	/**
	 * Parses imaginary part of complex number from string s.
	 * 
	 * @param s parsed string
	 * @return imaginary part
	 */
	private static double parseImag(String s) {
		int index = s.indexOf("i");
		// if there's no decimal numbers after 'i'
		if (s.substring(index + 1).length() == 0) {
			return 1.0;
		} else {
			s = s.replace("i", "");
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s + " cant parse to double.");
			}
		}
	}

	/**
	 * Returns real part.
	 * 
	 * @return real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Returns imaginary part.
	 * 
	 * @return imaginary part
	 */
	public double getIm() {
		return im;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
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
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}

}
