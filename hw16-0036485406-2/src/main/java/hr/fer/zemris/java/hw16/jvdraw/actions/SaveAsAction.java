package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectStringBuilder;

/**
 * Action writes a text file with extension *.jvd.
 * 
 * @author nikola
 *
 */
public class SaveAsAction extends AbstractAction {
	
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
	public SaveAsAction(String name, DrawingModel model, JVDraw frame) {
		super(name);
		this.model = model;
		this.frame = frame;
		
		setEnabled(false);
		
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				if(source.getSize() == 0) {
					setEnabled(false);
				}
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				setEnabled(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As");
		int retVal = fc.showSaveDialog(frame);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path filePath = fc.getSelectedFile().toPath().toAbsolutePath();
		String pathname = filePath.toString();
		if(pathname.lastIndexOf(".") == -1) {
			filePath = Paths.get(pathname + ".jvd");
		}else if(!pathname.endsWith(".jvd")) {
			JOptionPane.showMessageDialog(frame, filePath + " is not jvd file!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (Files.exists(filePath)) {

			int decision = JOptionPane.showConfirmDialog(frame,
					"File exists in \"" + filePath.getName(filePath.getNameCount() - 2)
							+ "\". Replacing will overwrite it.",
					"File \"" + filePath.getFileName() + "\" exits. Want to replace it?", JOptionPane.YES_NO_OPTION);

			if (decision == JOptionPane.NO_OPTION) {
				return;
			}
		}
		
		GeometricalObjectStringBuilder sb = new GeometricalObjectStringBuilder();
		for(int index = 0, n = model.getSize(); index < n; index++) {
			GeometricalObject o = model.getObject(index);
			o.accept(sb);
		}
		String s = sb.getString();
		try {
			Files.write(filePath, s.getBytes());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		frame.setPath(filePath);
	}

}
