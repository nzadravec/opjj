package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;

/**
 * Represents filled circle. Class inherits from {@link Circle} and adds single
 * attribut, area color.
 * 
 * @author nikola
 *
 */
public class FilledCircle extends Circle {

	/**
	 * Area color
	 */
	private Color areaColor;

	/**
	 * Constructor.
	 * 
	 * @param center center point
	 * @param radius circle radius
	 * @param outlineColor outline color
	 * @param areaColor area color
	 */
	public FilledCircle(Point center, int radius, Color outlineColor, Color areaColor) {
		super(center, radius, outlineColor);
		this.areaColor = areaColor;
	}

	/**
	 * Constuctor.
	 * 
	 * @param center center point
	 * @param pointOnCircle some point on circle
	 * @param outlineColor outline color
	 * 	 * @param areaColor area color
	 */
	public FilledCircle(Point center, Point pointOnCircle, Color outlineColor, Color areaColor) {
		super(center, pointOnCircle, outlineColor);
		this.areaColor = areaColor;
	}

	/**
	 * Gets area color.
	 * 
	 * @return area color
	 */
	public Color getAreaColor() {
		return areaColor;
	}

	/**
	 * Sets area color.
	 * 
	 * @param areaColor area color
	 */
	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
		notifyListeners();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return super.toString() + ", " + areaColor.toHex();
	}

}
