package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;

/**
 * {@link GeometricalObjectEditor} for {@link FilledCircle} object.
 * 
 * @author nikola
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link FilledCircle} object
	 */
	private FilledCircle filledCircle;
	
	/**
	 * {@link TextArea}s for {@link FilledCircle} attributes
	 */
	private TextArea centerTextArea;
	private TextArea radiusTextArea;
	private TextArea colorTextArea;
	private TextArea areaColorTextArea;
	
	/**
	 * {@link FilledCircle} attributes
	 */
	private Point center;
	private int radius;
	private Color color;
	private Color areaColor;

	/**
	 * Constructor.
	 * 
	 * @param filledCircle {@link FilledCircle} object
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel head = new JPanel();
		this.add(head, BorderLayout.PAGE_START);
		JLabel title = new JLabel("Filled circle");
		head.add(title);

		JPanel body = new JPanel();
		this.add(body, BorderLayout.CENTER);

		body.setLayout(new GridLayout(4, 2));

		center = filledCircle.getCenter();
		radius = filledCircle.getRadius();
		color = filledCircle.getColor();
		areaColor = filledCircle.getAreaColor();

		centerTextArea = new TextArea(center.toString());
		radiusTextArea = new TextArea(Integer.toString(radius));
		colorTextArea = new TextArea(color.toString());
		areaColorTextArea = new TextArea(areaColor.toString());

		body.add(new JLabel("center point (x,y)"));
		body.add(centerTextArea);
		body.add(new JLabel("radius "));
		body.add(radiusTextArea);
		body.add(new JLabel("outline color (r,g,b)"));
		body.add(colorTextArea);
		body.add(new JLabel("area color (r,g,b)"));
		body.add(areaColorTextArea);
	}

	@Override
	public void checkEditing() {
		center = Point.parsePoint(centerTextArea.getText());
		radius = Integer.parseInt(radiusTextArea.getText());
		color = Color.parseColor(colorTextArea.getText());
		areaColor = Color.parseColor(areaColorTextArea.getText());
	}

	@Override
	public void acceptEditing() {
		filledCircle.setCenter(center);
		filledCircle.setRadius(radius);
		filledCircle.setColor(color);
		filledCircle.setAreaColor(areaColor);
	}

}
