package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * {@link GeometricalObjectBBCalculator} is used to calculate the bounding box
 * for complete image.
 * 
 * @author nikola
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	private int y = Integer.MAX_VALUE;
	private int x = Integer.MAX_VALUE;
	private int yPlusHeight;
	private int xPlusWidth;

	@Override
	public void visit(Line line) {
		int x1 = line.getStart().x;
		int y1 = line.getStart().y;
		int x2 = line.getEnd().x;
		int y2 = line.getEnd().y;
		y = min(y, min(y1, y2));
		x = min(x, min(x1, x2));
		yPlusHeight = max(yPlusHeight, max(y1, y2));
		xPlusWidth = max(xPlusWidth, max(x1, x2));
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();
		y = min(y, center.y - radius);
		x = min(x, center.x - radius);
		yPlusHeight = max(yPlusHeight, center.y + radius);
		xPlusWidth = max(xPlusWidth, center.x + radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(x, y, xPlusWidth - x, yPlusHeight - y);
	}

}
