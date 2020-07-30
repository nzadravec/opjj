package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;

/**
 * {@link GeometricalObjectEditor} for {@link Circle} object.
 * 
 * @author nikola
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * {@link Circle} object
	 */
	private Circle circle;

	/**
	 * {@link TextArea}s for {@link Circle} attributes
	 */
	private TextArea centerTextArea;
	private TextArea radiusTextArea;
	private TextArea colorTextArea;

	/**
	 * {@link Circle} attributes
	 */
	private Point center;
	private int radius;
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param circle {@link Circle} object
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel head = new JPanel();
		this.add(head, BorderLayout.PAGE_START);
		JLabel title = new JLabel("Circle");
		head.add(title);

		JPanel body = new JPanel();
		this.add(body, BorderLayout.CENTER);

		body.setLayout(new GridLayout(3, 2));

		center = circle.getCenter();
		radius = circle.getRadius();
		color = circle.getColor();

		centerTextArea = new TextArea(center.toString());
		radiusTextArea = new TextArea(Integer.toString(radius));
		colorTextArea = new TextArea(color.toString());

		body.add(new JLabel("center point (x,y)"));
		body.add(centerTextArea);
		body.add(new JLabel("radius "));
		body.add(radiusTextArea);
		body.add(new JLabel("outline color (r,g,b)"));
		body.add(colorTextArea);
	}

	@Override
	public void checkEditing() {
		center = Point.parsePoint(centerTextArea.getText());
		radius = Integer.parseInt(radiusTextArea.getText());
		color = Color.parseColor(colorTextArea.getText());
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(center);
		circle.setRadius(radius);
		circle.setColor(color);
	}

}
