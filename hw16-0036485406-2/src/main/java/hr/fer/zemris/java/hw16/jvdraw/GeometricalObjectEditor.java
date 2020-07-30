package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.JPanel;

/**
 * For each concrete {@link GeometricalObject} define appropriate subclass which
 * accepts in constructor a reference to appropriately subtyped geometrical
 * object, creates appropriate swing components and initializes them with values
 * retrieved from given geometrical object. When {@link #checkEditing} is
 * called, the method must check if fields are correctly filled and if not, must
 * throw some exception. When {@link #acceptEditing} is called, the values from
 * all fields must be written back into given geometrical object.
 * 
 * @author nikola
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Method checks if fields are correctly filled and if not, it throw some
	 * exception.
	 */
	public abstract void checkEditing();

	/**
	 * Values from all fields are written back into given geometrical object.
	 */
	public abstract void acceptEditing();

}
