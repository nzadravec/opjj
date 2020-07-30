package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;

/**
 * Represents line defined with start, end point and line color.
 * 
 * @author nikola
 *
 */
public class Line extends GeometricalObject {
	
	/**
	 * Start point
	 */
	private Point start;
	/**
	 * End point
	 */
	private Point end;
	/**
	 * Line color
	 */
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param start start point
	 * @param end end point
	 * @param color line color
	 */
	public Line(Point start, Point end, Color color) {
		super();
		this.start = start;
		this.end = end;
		this.color = color;
	}

	/**
	 * Gets start point.
	 * 
	 * @return start point
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Sets start point.
	 * 
	 * @param start start point
	 */
	public void setStart(Point start) {
		this.start = start;
		notifyListeners();
	}

	/**
	 * Gets end point.
	 * 
	 * @return end point
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Sets end point.
	 * 
	 * @param end end point
	 */
	public void setEnd(Point end) {
		this.end = end;
		notifyListeners();
	}

	/**
	 * Gets line color.
	 * 
	 * @return line color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets line color.
	 * 
	 * @param color line color.
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public String toString() {
		return "Line (" + start + ")-(" + end + ")";
	}

}
