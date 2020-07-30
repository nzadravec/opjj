package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.Tool;

/**
 * Tool representing current adding tool.
 * 
 * @author nikola
 *
 */
public class AddingTool implements Tool {
	
	/**
	 * Current tool
	 */
	private Tool currentTool;
	
	/**
	 * Constructor.
	 * 
	 * @param startTool start tool
	 */
	public AddingTool(Tool startTool) {
		currentTool = startTool;
	}
	
	/**
	 * Sets current tool to given tool.
	 * 
	 * @param tool given tool
	 */
	public void setTool(Tool tool) {
		currentTool = tool;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currentTool.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentTool.mouseReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentTool.mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentTool.mouseMoved(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currentTool.mouseDragged(e);
	}

	@Override
	public void paint(Graphics2D g2d) {
		currentTool.paint(g2d);
	}

}
