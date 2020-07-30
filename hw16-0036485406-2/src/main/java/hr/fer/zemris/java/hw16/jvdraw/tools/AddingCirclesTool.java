package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Tool for adding circles. First click defines the circle center and as user
 * moves the mouse, a circle radius is defined. On second click, circle is added.
 * 
 * @author nikola
 *
 */
public class AddingCirclesTool implements Tool {

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Drawing color provider
	 */
	private IColorProvider drawColorProvider;

	/**
	 * Circle center
	 */
	private Point center;
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
	public AddingCirclesTool(DrawingModel drawingModel, IColorProvider drawColorProvider) {
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
			Circle circle = new Circle(center, new Point(e.getPoint()), drawColorProvider.getCurrentColor());
			drawingModel.add(circle);
		} else {
			secondClick = true;
			center = new Point(e.getPoint());
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
			Circle circle = new Circle(center, mousePosition, drawColorProvider.getCurrentColor());
			new GeometricalObjectPainter(g2d).visit(circle);
		}
	}

}
