package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Action reads a text file with extension *.jvd.
 * 
 * @author nikola
 *
 */
public class OpenAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Model
	 */
	private DrawingModel model;
	/**
	 * Frame
	 */
	private JVDraw frame;

	/**
	 * Constructor.
	 * 
	 * @param name name
	 * @param model model
	 * @param frame frame
	 */
	public OpenAction(String name, DrawingModel model, JVDraw frame) {
		super(name);
		this.model = model;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD FILES", "jvd");
		fc.setFileFilter(filter);
		fc.setDialogTitle("Open");

		int retVal = fc.showOpenDialog(frame);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path filePath = fc.getSelectedFile().toPath().toAbsolutePath();
		if (!filePath.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(frame, filePath + " is not jvd file!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(frame, filePath + " doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<GeometricalObject> objects;
		try {
			objects = ActionsUtil.loadObjectsFrom(filePath);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (int index = 0, n = model.getSize(); index < n; index++) {
			GeometricalObject o = model.getObject(0);
			model.remove(o);
		}

		objects.forEach(o -> model.add(o));
		frame.setPath(filePath);
	}

}
