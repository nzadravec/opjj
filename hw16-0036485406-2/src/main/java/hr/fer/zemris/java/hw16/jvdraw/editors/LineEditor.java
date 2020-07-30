package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.Color;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.Point;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * {@link GeometricalObjectEditor} for {@link Line} object.
 * 
 * @author nikola
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link Line} object
	 */
	private Line line;
	
	/**
	 * {@link TextArea}s for {@link Line} attributes
	 */
	private TextArea startTextArea;
	private TextArea endTextArea;
	private TextArea colorTextArea;
	
	/**
	 * {@link Line} attributes
	 */
	private Point start;
	private Point end;
	private Color color;
	
	/**
	 * Constructor.
	 * 
	 * @param line {@link Line} object
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel head = new JPanel();
		this.add(head, BorderLayout.PAGE_START);
		JLabel title = new JLabel("Line");
		head.add(title);
		
		JPanel body = new JPanel();
		this.add(body, BorderLayout.CENTER);

		body.setLayout(new GridLayout(3, 2));
		
		start = line.getStart();
		end = line.getEnd();
		color = line.getColor();
		
		startTextArea = new TextArea(start.toString());
		endTextArea = new TextArea(end.toString());
		colorTextArea = new TextArea(color.toString());
		
		body.add(new JLabel("start point (x,y)"));
		body.add(startTextArea);
		body.add(new JLabel("end point (x,y)"));
		body.add(endTextArea);
		body.add(new JLabel("line color (r,g,b)"));
		body.add(colorTextArea);
	}

	@Override
	public void checkEditing() {
		start = Point.parsePoint(startTextArea.getText());
		end = Point.parsePoint(endTextArea.getText());
		color = Color.parseColor(colorTextArea.getText());
	}

	@Override
	public void acceptEditing() {
		line.setStart(start);
		line.setEnd(end);
		line.setColor(color);
	}

}
