package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class represents component which is little square of dimension 15x15 pixels
 * and is filled with color that is currently stored. When user clicks on this
 * component, component opens color chooser dialog and allows user to select
 * color that will become new current color.
 * 
 * @author nikola
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;

	/**
	 * Current color
	 */
	private Color currentColor;
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> colorChangeListeners = new ArrayList<>();

	/**
	 * Constructor.
	 * 
	 * @param color start color
	 */
	public JColorArea(Color color) {
		currentColor = color;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				java.awt.Color newColor = JColorChooser.showDialog(JColorArea.this, "Select a color", currentColor);
				if (newColor == null) {
					return;
				}
				setCurrentColor(new Color(newColor));
				repaint();
			}
		});
	}

	/**
	 * Sets current color with given color.
	 * 
	 * @param color given color
	 */
	private void setCurrentColor(Color color) {
		Color oldColor = currentColor;
		currentColor = color;

		notifyListeners(oldColor);
	}

	/**
	 * Notifies all registered listeners that current color is changed.
	 * 
	 * @param oldColor
	 */
	private void notifyListeners(Color oldColor) {
		for (ColorChangeListener l : new ArrayList<>(colorChangeListeners)) {
			l.newColorSelected(this, oldColor, this.currentColor);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
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
		g2d.setColor(currentColor);
		g2d.fillRect(x, y, width, height);
	}

	@Override
	public Color getCurrentColor() {
		return currentColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.remove(l);
	}

}
