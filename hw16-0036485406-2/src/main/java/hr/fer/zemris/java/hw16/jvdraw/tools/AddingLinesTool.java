package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Tool for adding lines. The first click defines the start point for the line
 * and the second click defines the end point for the line. Before the second
 * click occurs, as user moves the mouse, the line is drawn with end-point
 * tracking the mouse so that the user can see what will be the final result.
 * 
 * @author nikola
 *
 */
public class AddingLinesTool implements Tool {

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Drawing color provider
	 */
	private IColorProvider drawColorProvider;

	/**
	 * Line start point
	 */
	private Point startPoint;
	/**
	 * Flag to determine if second click accured
	 */
	private boolean secondClick;
	/**
	 * Mouse position
	 */
	private Point mousePosition;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel drawing model
	 * @param drawColorProvider drawing color provider
	 */
	public AddingLinesTool(DrawingModel drawingModel, IColorProvider drawColorProvider) {
		Objects.requireNonNull(drawingModel, "drawingModel object must not be null");
		Objects.requireNonNull(drawColorProvider, "drawColorProvider object must not be null");

		this.drawingModel = drawingModel;
		this.drawColorProvider = drawColorProvider;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (secondClick) {
			secondClick = false;
			Point endPoint = new Point(e.getPoint());
			Line line = new Line(startPoint, endPoint, drawColorProvider.getCurrentColor());
			drawingModel.add(line);
		} else {
			secondClick = true;
			startPoint = new Point(e.getPoint());
			mousePosition = new Point(e.getPoint());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (secondClick) {
			mousePosition = new Point(e.getPoint());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (secondClick) {
			Point endPoint = mousePosition;
			Line line = new Line(startPoint, endPoint, drawColorProvider.getCurrentColor());
			new GeometricalObjectPainter(g2d).visit(line);
		}
	}

}
