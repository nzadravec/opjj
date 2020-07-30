package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Class models geometrical shapes.
 * 
 * @author nikola
 *
 */
public abstract class GeometricalObject {
	
	/**
	 * Object listeners.
	 */
	protected List<GeometricalObjectListener> listeners = new ArrayList<>();

	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Adds {@link GeometricalObject} listener.
	 * 
	 * @param l {@link GeometricalObjectListener} object
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * Removes {@link GeometricalObject} listener.
	 * 
	 * @param l {@link GeometricalObjectListener} object
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all registered listeners that object changed.
	 */
	protected void notifyListeners() {
		for(GeometricalObjectListener l : new ArrayList<>(listeners)) {
			l.geometricalObjectChanged(this);
		}
	}

	/**
	 * Creates {@link GeometricalObjectEditor} for this object.
	 * 
	 * @return {@link GeometricalObjectEditor} for this object
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

}
