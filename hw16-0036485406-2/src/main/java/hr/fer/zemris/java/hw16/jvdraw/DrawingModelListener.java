package hr.fer.zemris.java.hw16.jvdraw;

/**
 * {@link DrawingModel} listener interface. Interval defined by index0 to index1
 * is inclusive; for example, if properties of object on position 5 change, you
 * are expected to fire objectChanged(..., 5, 5) .
 * 
 * @author nikola
 *
 */
public interface DrawingModelListener {
	/**
	 * Called by {@link DrawingModel} when objects are added to it.
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 lower index value of interval
	 * @param index1 upper index value of interval
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called by {@link DrawingModel} when objects are removed to it.
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 lower index value of interval
	 * @param index1 upper index value of interval
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called by {@link DrawingModel} when objects are changed.
	 * 
	 * @param source {@link DrawingModel} object
	 * @param index0 lower index value of interval
	 * @param index1 upper index value of interval
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
