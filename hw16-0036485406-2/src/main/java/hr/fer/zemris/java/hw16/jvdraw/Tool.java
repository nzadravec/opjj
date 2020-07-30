package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Models state of tool for drawing {@link GeometricalObject} objects using
 * mouse clicks.
 * 
 * @author nikola
 *
 */
public interface Tool {
	
	/**
	 * See {@link MouseListener#mousePressed(MouseEvent)}
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * See {@link MouseListener#mouseReleased(MouseEvent)}
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * See {@link MouseListener#mouseClicked(MouseEvent)}
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * See {@link MouseMotionListener#mouseMoved(MouseEvent)}
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * See {@link MouseMotionListener#mouseDragged(MouseEvent)}
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints using given {@link Graphics2D} object if needed.
	 *
	 * @param g2d {@link Graphics2D} object
	 */
	public void paint(Graphics2D g2d);
}
