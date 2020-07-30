package hr.fer.zemris.java.hw02;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

/**
 * Reprezentacija nepromjenjivog kompleksnog broja. Razred predstavlja podršku
 * za rad s kompleksnim brojevima.
 * 
 * @author nikola
 *
 */
public class ComplexNumber {

	/**
	 * realni dio kompleknog broja
	 */
	private double real;

	/**
	 * imaginarni dio kompleksnog broja
	 */
	private double imag;

	/**
	 * modul kompleksnog broja
	 */
	private double magnitude;

	/**
	 * argument kompleksnog broja
	 */
	private double angle;

	/**
	 * 
	 */
	public static double THRESHOLD = 1E-4;

	/**
	 * Stvara kompleksni broj pomoću realnog i imaginarnog dijela.
	 * 
	 * @param real
	 *            realni dio kompleknog broja.
	 * @param imag
	 *            imaginarni dio kompleksnog broja.
	 */
	public ComplexNumber(double real, double imag) {
		this.real = real;
		this.imag = imag;
		this.magnitude = sqrt(real * real + imag * imag);
		this.angle = atan2(imag, real);
	}

	/**
	 * Stvara kompleksni broj pomoću samo realnog dijela.
	 * 
	 * @param real
	 *            realni dio kompleknog broja.
	 * @return kompleksni broj.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Stvara kompleksni broj pomoću samo imaginarnog dijela.
	 * 
	 * @param imag
	 *            imaginarni dio kompleksnog broja.
	 * @return kompleksni broj.
	 */
	public static ComplexNumber fromImaginary(double imag) {
		return new ComplexNumber(0, imag);
	}

	/**
	 * Stvara kompleksni broj pomoću modula i argumenta.
	 * 
	 * @param magnitude
	 *            modul kompleksnog broja.
	 * @param angle
	 *            argument kompleksnog broja.
	 * @return kompleksni broj.
	 * @throws IllegalArgumentException
	 *             ako je modul manji od nule.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) {
			throw new IllegalArgumentException("Magnituda ne smije biti negativna.");
		}

		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Stvara kompleksni broj parsiranje niz znakova.
	 * 
	 * Prihvaća nizove kao što su: <code>"3.51", "-3.17", "-2.71i", "i", "1",
	"-2.71-3.15i")</code>
	 * 
	 * @param s
	 *            niz znakova.
	 * @return kompleksni broj.
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		double real = 0;
		double imag = 0;

		int signReal = 1;
		if (s.charAt(0) == '-') {
			signReal = -1;
			s = s.substring(1);
		}

		int signImag = 1;
		int index = s.indexOf("+");
		if (index == -1) {
			signImag = -1;
			index = s.indexOf("-");
		}

		try {
			if (index == -1 && s.charAt(s.length() - 1) != 'i') {
				real = Double.parseDouble(s);
				return new ComplexNumber(signReal * real, imag);

			} else if (index == -1 && s.charAt(s.length() - 1) == 'i') {
				signImag = signReal;
				imag = 1;
				if (s.length() != 1) {
					imag = Double.parseDouble(s.substring(0, s.length() - 1));
				}

				return new ComplexNumber(0, signImag * imag);
			}
			real = Double.parseDouble(s.substring(0, index));
			imag = Double.parseDouble(s.substring(index + 1, s.length() - 1));

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("String se ne može parsirati.");
		}

		return new ComplexNumber(signReal * real, signImag * imag);
	}

	/**
	 * Dohvaća realni dio kompleksnog broja.
	 * 
	 * @return realni dio.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Dohvaća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio.
	 */
	public double getImag() {
		return imag;
	}

	/**
	 * Dohvaća modul kompleksnog broja.
	 * 
	 * @return modul.
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Dohvaća argument kompleksnog broja.
	 * 
	 * @return argument.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Zbraja trenutni i dani kompleksni broj. Metoda ne mijenja trenutni kompleksni
	 * broj ili dani, nego stvara novi koji predstavlja zbroj trenutnog i danog.
	 * 
	 * @param c
	 *            kompleksni broj koji se zbraja s trenutnim.
	 * @return novi kompleksni broj koji je zbroj trenutnog i danog.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imag + c.imag);
	}

	/**
	 * Oduzima dani kompleksni broj od trenutnog. Metoda ne mijenja trenutni
	 * kompleksni broj ili dani, nego stvara novi koji predstavlja razliku trenutnog
	 * i danog.
	 * 
	 * @param c
	 *            kompleksni broj koji se oduzima od trenutnog.
	 * @return novi kompleksni broj koji je razlika trenutnog i danog.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imag - c.imag);
	}

	/**
	 * Množi trenutni i dani kompleksni broj. Metoda ne mijenja trenutni kompleksni
	 * broj ili dani, nego stvara novi koji predstavlja umnožak trenutnog i danog.
	 * 
	 * @param c
	 *            kompleksni broj koji se množi s trenutnim.
	 * @return novi kompleksni broj koji je umnožak trenutnog i danog.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double magnitude = this.magnitude * c.magnitude;
		double angle = this.angle + c.angle;
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Dijeli trenutni s danim kompleksnim brojem. Metoda ne mijenja trenutni
	 * kompleksni broj ili dani, nego stvara novi koji predstavlja količnik
	 * trenutnog i danog.
	 * 
	 * @param c
	 *            kompleksni broj koji dijeli trenutnog.
	 * @return novi kompleksni broj koji je količnik trenutnog i danog.
	 * @throws DivideByZeroException
	 *             ako je realni dio danog kompleknog broja nula.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c.real == 0) {
			throw new DivideByZeroException();
		}

		double magnitude = this.magnitude / c.magnitude;
		double angle = this.angle - c.angle;
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Potencira trenutni kompleksni broj s danim cijelim brojem. Metoda ne mijenja trenutni
	 * kompleksni broj nego stvara novi koji predstavlja trenutni potenciran s danim
	 * brojem.
	 * 
	 * @param n
	 *            potencija.
	 * @return novi kompleksni broj koji je trenutni potenciran s danim brojem.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija ne smije biti negativna.");
		}
		
		double magnitude = pow(this.magnitude, n);
		double angle = n * this.angle;
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Vraća korijene trenutnog kompleksnog broja. Metoda izračunava n korijena
	 * trenutnog kompleknog broja gdje je n dani cijeli broj.
	 * 
	 * @param n broj korijena.
	 * @return korijeni trenutnog kompleksnog broja.
	 */
	public ComplexNumber[] root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Broj korijena mora biti pozitivan broj.");
		}

		double rootMagnitude = pow(magnitude, 1.0 / (double) n);
		ComplexNumber[] cs = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			cs[i] = new ComplexNumber(rootMagnitude * cos((angle + 2 * PI * i) / (double) n),
					rootMagnitude * sin((angle + 2 * PI * i) / n));
		}
		return cs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
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
		ComplexNumber other = (ComplexNumber) obj;
		if (abs(this.real - other.real) > THRESHOLD) {
			return false;
		}
		if (abs(this.imag - other.imag) > THRESHOLD) {
			return false;
		}
		return true;
	}

	public String toString() {
		if (imag >= 0) {
			return real + "+" + imag + "i";
		} else {
			return "" + real + imag + "i";
		}
	}

}
