package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;

/**
 * Represents circle defined with center point, radius and outline color.
 * 
 * @author nikola
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center point
	 */
	private Point center;

	/**
	 * Circle radius
	 */
	private int radius;

	/**
	 * Outline color
	 */
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param center center point
	 * @param radius circle radius
	 * @param color outline color
	 */
	public Circle(Point center, int radius, Color color) {
		super();
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	/**
	 * Constuctor.
	 * 
	 * @param center center point
	 * @param pointOnCircle some point on circle
	 * @param color outline color
	 */
	public Circle(Point center, Point pointOnCircle, Color color) {
		this.center = center;
		radius = (int) sqrt(pow(abs(pointOnCircle.x - center.x), 2) + pow(abs(pointOnCircle.y - center.y), 2));
		this.color = color;
	}

	/**
	 * Gets center point.
	 * 
	 * @return center point
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Sets center point.
	 * 
	 * @param center center point
	 */
	public void setCenter(Point center) {
		this.center = center;
		notifyListeners();
	}

	/**
	 * Gets circle radius.
	 * 
	 * @return circle radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets circle radius.
	 * 
	 * @param radius circle radius
	 */
	public void setRadius(int radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("radius must be positive number");
		}

		this.radius = radius;
		notifyListeners();
	}

	/**
	 * Gets outline color.
	 * 
	 * @return outline color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets outline color.
	 * 
	 * @param color outline color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "Circle (" + center + "), " + radius;
	}

}
