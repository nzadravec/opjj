package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Builds string of all geometrical objects for jvd text file.
 * 
 * @author nikola
 *
 */
public class GeometricalObjectStringBuilder implements GeometricalObjectVisitor {
	
	/**
	 * String builder
	 */
	private StringBuilder sb = new StringBuilder();

	@Override
	public void visit(Line line) {
		sb.append("LINE ");
		Point start = line.getStart();
		sb.append(start.x + " " + start.y + " ");
		Point end = line.getEnd();
		sb.append(end.x + " " + end.y + " ");
		Color c = line.getColor();
		sb.append(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		sb.append(System.getProperty("line.separator"));
	}

	@Override
	public void visit(Circle circle) {
		sb.append("CIRCLE ");
		Point center = circle.getCenter();
		sb.append(center.x + " " + center.y + " ");
		int radius = circle.getRadius();
		sb.append(radius + " ");
		Color c = circle.getColor();
		sb.append(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		sb.append(System.getProperty("line.separator"));
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		sb.append("FCIRCLE ");
		Point center = filledCircle.getCenter();
		sb.append(center.x + " " + center.y + " ");
		int radius = filledCircle.getRadius();
		sb.append(radius + " ");
		Color c = filledCircle.getColor();
		sb.append(c.getRed() + " " + c.getGreen() + " " + c.getBlue() + " ");
		Color ac = filledCircle.getAreaColor();
		sb.append(ac.getRed() + " " + ac.getGreen() + " " + ac.getBlue());
		sb.append(System.getProperty("line.separator"));
	}
	
	public String getString() {
		return sb.toString();
	}

}
