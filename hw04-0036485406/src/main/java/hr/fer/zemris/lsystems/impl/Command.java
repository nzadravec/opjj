package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje modelira objekte koji predstavljaju akcije koje kornjača
 * {@link TurtleState} mora napraviti.
 * 
 * @author nikola
 *
 */
public interface Command {

	/**
	 * Izvrši trenutnu naredbu.
	 * 
	 * @param ctx
	 *            objekt pomoću kojeg se izvodi postupak prikazivanja fraktala
	 * @param painter
	 *            objekt pomoću kojeg se crta
	 */
	public void execute(Context ctx, Painter painter);

}
