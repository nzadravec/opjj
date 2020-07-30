package hr.fer.zemris.java.hw16.jvdraw;

import java.util.Objects;

import javax.swing.AbstractListModel;
import javax.swing.JList;

/**
 * List model of {@link JList} for the object list component in {@link JVDraw}.
 * 
 * @author nikola
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	
	public DrawingObjectListModel(DrawingModel drawingModel) {
		Objects.requireNonNull(drawingModel, "drawingModel object must not be null");
		this.drawingModel = drawingModel;
		
		drawingModel.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(source, index0, index1);
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(source, index0, index1);
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

}
