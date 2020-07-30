package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Objects;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * The central component of {@link JVDraw} frame. It draws
 * {@link GeometricalObject} objects stored in {@link DrawingModel} on itself.
 * When users clicks on it with mouse it forwards received notifications to the
 * current used {@link Tool}. Component is registered to listen on the
 * {@link DrawingModel} object. Each time it is notified that something has
 * changed, it calls a {@link #repaint()} method.
 * 
 * @author nikola
 *
 */
public class JDrawingCanvas extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Current tool used for drawing new {@link GeometricalObject}
	 */
	private Tool currentTool;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel drawing model
	 * @param currentTool current tool
	 */
	public JDrawingCanvas(DrawingModel drawingModel, Tool currentTool) {
		Objects.requireNonNull(drawingModel, "drawingModel object must not be null");
		Objects.requireNonNull(currentTool, "currentTool object must not be null");

		this.drawingModel = drawingModel;
		this.currentTool = currentTool;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				currentTool.mouseReleased(e);
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				currentTool.mousePressed(e);
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				currentTool.mouseDragged(e);
				repaint();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Dimension dim = getSize();
		Insets ins = getInsets();
		int x = ins.left;
		int y = ins.top;
		int width = dim.width - ins.right - ins.left;
		int height = dim.height - ins.bottom - ins.top;
		g2d.setColor(Color.white);
		g2d.fillRect(x, y, width, height);

		GeometricalObjectVisitor v = new GeometricalObjectPainter(g2d);
		for (int index = 0, n = drawingModel.getSize(); index < n; index++) {
			GeometricalObject o = drawingModel.getObject(index);
			o.accept(v);
		}
		currentTool.paint(g2d);
	}

}
