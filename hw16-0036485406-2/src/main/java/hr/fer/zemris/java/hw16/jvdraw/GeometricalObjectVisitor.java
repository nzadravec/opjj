package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * {@link GeometricalObject} visitor.
 * 
 * @author nikola
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visiting {@link Line} operation.
	 * 
	 * @param line
	 */
	public abstract void visit(Line line);
	/**
	 * Visiting {@link Circle} operation.
	 * 
	 * @param line
	 */
	public abstract void visit(Circle circle);
	/**
	 * Visiting {@link FilledCircle} operation.
	 * 
	 * @param line
	 */
	public abstract void visit(FilledCircle filledCircle);
}
