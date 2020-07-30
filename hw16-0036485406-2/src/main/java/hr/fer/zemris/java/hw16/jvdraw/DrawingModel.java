package hr.fer.zemris.java.hw16.jvdraw;

/**
 * In {@link DrawingModel} , graphical objects have its defined position and it
 * is expected that the image rendering will be created by drawing objects from
 * first-one to last-one (this is important if objects overlap).
 * 
 * @author nikola
 *
 */
public interface DrawingModel {

	/**
	 * Returns size of drawing model (number of {@link GeometricalObject}s model contains).
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Gets {@link GeometricalObject} at given index.
	 * 
	 * @param index given index
	 * @return {@link GeometricalObject} at given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds given {@link GeometricalObject} to model.
	 * 
	 * @param object given {@link GeometricalObject}
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds {@link DrawingModel} listener.
	 * 
	 * @param l {@link DrawingModelListener} object
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes {@link DrawingModel} listener.
	 * 
	 * @param l {@link DrawingModelListener} object
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes given {@link GeometricalObject} object.
	 * 
	 * @param object {@link GeometricalObject} object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Moves {@link GeometricalObject} object for given offset.
	 * 
	 * @param object {@link GeometricalObject} object
	 * @param offset given offset
	 */
	public void changeOrder(GeometricalObject object, int offset);
}
