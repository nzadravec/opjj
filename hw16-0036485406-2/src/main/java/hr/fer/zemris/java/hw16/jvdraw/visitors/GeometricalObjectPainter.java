package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * {@link GeometricalObjectPainter} receives through constructor a reference to
 * Graphics2D and then can be used to paint all geometrical objects.
 * 
 * @author nikola
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Object for drawing
	 */
	private Graphics2D g2d;

	/**
	 * Constructor.
	 * 
	 * @param g2d object for drawing
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		Objects.requireNonNull(g2d, "g2d object must not be null");
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		int x1 = line.getStart().x;
		int y1 = line.getStart().y;
		int x2 = line.getEnd().x;
		int y2 = line.getEnd().y;
		g2d.setColor(line.getColor());
		g2d.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();
		Shape circleShape = new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2 * radius,
				2 * radius);
		g2d.setColor(circle.getColor());
		g2d.draw(circleShape);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();
		Shape circleShape = new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2 * radius,
				2 * radius);
		g2d.setColor(filledCircle.getColor());
		g2d.draw(circleShape);
		g2d.setColor(filledCircle.getAreaColor());
		g2d.fill(circleShape);
	}

}
