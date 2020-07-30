package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

public class ExportAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private DrawingModel model;
	private JFrame frame;
	
	private ExitAction exitAction;

	public ExportAction(String name, DrawingModel model, JFrame frame, ExitAction exitAction) {
		super(name);
		this.model = model;
		this.frame = frame;
		this.exitAction = exitAction;

		setEnabled(false);

		model.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				if (source.getSize() == 0) {
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
		fc.setDialogTitle("Save as jpg, png or gif");
		int retVal = fc.showSaveDialog(frame);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path filePath = fc.getSelectedFile().toPath().toAbsolutePath();
		String pathname = filePath.toString();
		if (pathname.lastIndexOf(".") == -1) {
			JOptionPane.showMessageDialog(frame, "Missing extension; jpg, png or gif", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String extension = pathname.substring(pathname.lastIndexOf(".") + 1);
		if (!extension.equals("jpg") && !extension.equals("png") && !extension.equals("gif")) {
			JOptionPane.showMessageDialog(frame, extension + " is not valid extension!", "Error",
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

		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int index = 0, n = model.getSize(); index < n; index++) {
			GeometricalObject o = model.getObject(index);
			o.accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();

		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);
		GeometricalObjectVisitor v = new GeometricalObjectPainter(g);
		for (int index = 0, n = model.getSize(); index < n; index++) {
			GeometricalObject o = model.getObject(index);
			o.accept(v);
		}
		g.dispose();

		try {
			ImageIO.write(image, extension, new File(pathname));
			exitAction.setChanged(false);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(frame, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
