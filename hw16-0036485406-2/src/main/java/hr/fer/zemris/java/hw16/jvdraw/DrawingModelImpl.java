package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * {@link DrawingModel} implementation.
 * 
 * @author nikola
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of {@link GeometricalObject} objects
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	/**
	 * Listener to single {@link GeometricalObject}; added to newly added
	 * geometrical objects
	 */
	private GeometricalObjectListener geomObjectListener = new GeometricalObjectListener() {
		@Override
		public void geometricalObjectChanged(GeometricalObject o) {
			int index = objects.indexOf(o);
			notifyListeners(l -> l.objectsChanged(DrawingModelImpl.this, index, index));
		}
	};

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IllegalArgumentException("Index must be between 0 and " + (objects.size() - 1));
		}

		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		object.addGeometricalObjectListener(geomObjectListener);
		int index = objects.size();
		objects.add(object);
		notifyListeners(l -> l.objectsAdded(this, index, index));
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies listeners.
	 * 
	 * @param consumer determines which method to call on listeners
	 */
	protected void notifyListeners(Consumer<DrawingModelListener> consumer) {
		for (DrawingModelListener l : new ArrayList<>(listeners)) {
			consumer.accept(l);
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(index);
		notifyListeners(l -> l.objectsRemoved(this, index, index));
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if ((index == 0 && offset < 0) || (index == (objects.size() - 1) && offset > 0)) {
			return;
		}

		objects.remove(index);
		int newIndex = index + offset;
		objects.add(newIndex, object);
		int index0 = Math.min(index, newIndex);
		int index1 = Math.max(index, newIndex);
		notifyListeners(l -> l.objectsChanged(DrawingModelImpl.this, index0, index1));
	}

}
