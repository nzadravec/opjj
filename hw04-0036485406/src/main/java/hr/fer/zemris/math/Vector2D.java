package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

/**
 * Razred modelira 2D vektor.
 * 
 * @author nikola
 *
 */
public class Vector2D {

	/** vrijednost na x osi */
	private double x;
	/** vrijednost na y osi */
	private double y;
	
	public static double THRESHOLD = 1E-4;

	/**
	 * Stvara 2D vektor pomoću vrijednosti na x i y osi.
	 * 
	 * @param x vrijednost na x osi
	 * @param y vrijednost na y osi
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Vraća vrijednost na x osi.
	 * 
	 * @return vrijednost na x osi
	 */
	public double getX() {
		return x;
	}

	/**
	 * Vraća vrijednost na y osi.
	 * 
	 * @return vrijednost na y osi
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translatira trenutni vektor za dani vektor.
	 * 
	 * @param offset vektor translacije
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException();
		}

		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
	}

	/**
	 * Vraća novi vektor koji je translacija trenutnog vektora za dani vektor.
	 * 
	 * @param offset vektor translacije
	 * @return novi vektor koji je translacija trenutnog vektora za dani vektor
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D newVector = this.copy();
		newVector.translate(offset);
		return newVector;
	}

	/**
	 * Rotira trenutni vektor za dani kut.
	 * 
	 * @param angle kut rotacije
	 */
	public void rotate(double angle) {
		double angleInRad = angle * PI / 180.0;

		double x = this.x * cos(angleInRad) - this.y * sin(angleInRad);
		double y = this.x * sin(angleInRad) + this.y * cos(angleInRad);
		this.x = x;
		this.y = y;
	}

	/**
	 * Vraća novi vektor koji je rotacija trenutnog vektora za dani kut.
	 * 
	 * @param angle kut rotacije
	 * @return novi vektor koji je rotacija trenutnog vektora za dani kut
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = this.copy();
		newVector.rotate(angle);
		return newVector;
		}

	/**
	 * Skalira trenutni vektor za dani skalar.
	 * 
	 * @param scaler skalar
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}

	/**
	 * Vraća novi vektor koji je skaliran trenutni vektor.
	 * 
	 * @param scaler skalar
	 * @return novi vektor koji je skaliran trenutni vektor
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = this.copy();
		newVector.scale(scaler);
		return newVector;
	}

	/**
	 * Stvara kopiju trenutnog vektora.
	 * 
	 * @return kopiju trenutnog vektora
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (abs(this.x - other.x) > THRESHOLD) {
			return false;
		}
		if (abs(this.y - other.y) > THRESHOLD) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
