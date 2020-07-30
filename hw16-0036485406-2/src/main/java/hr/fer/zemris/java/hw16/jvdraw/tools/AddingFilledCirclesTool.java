package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Similary to {@link AddingCirclesTool}, only it fills the area of circle.
 * 
 * @author nikola
 *
 */
public class AddingFilledCirclesTool implements Tool {

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Drawing color provider
	 */
	private IColorProvider drawColorProvider;
	/**
	 * Filling color provider
	 */
	private IColorProvider fillColorProvider;

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
	 * @param fillColorProvider filling color provider
	 */
	public AddingFilledCirclesTool(DrawingModel drawingModel, IColorProvider drawColorProvider,
			IColorProvider fillColorProvider) {
		Objects.requireNonNull(drawingModel, "drawingModel object must not be null");
		Objects.requireNonNull(drawColorProvider, "drawColorProvider object must not be null");
		Objects.requireNonNull(fillColorProvider, "fillColorProvider object must not be null");

		this.drawingModel = drawingModel;
		this.drawColorProvider = drawColorProvider;
		this.fillColorProvider = fillColorProvider;
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
			FilledCircle circle = new FilledCircle(center, new Point(e.getPoint()), drawColorProvider.getCurrentColor(),
					fillColorProvider.getCurrentColor());
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
			FilledCircle circle = new FilledCircle(center, mousePosition, drawColorProvider.getCurrentColor(),
					fillColorProvider.getCurrentColor());
			new GeometricalObjectPainter(g2d).visit(circle);
		}
	}

}
