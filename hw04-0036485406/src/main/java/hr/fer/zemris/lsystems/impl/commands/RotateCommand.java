package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * U trenutnom stanju modificira vektor smjera gledanja kornjače tako što ga
 * rotira za zadani kut.
 * 
 * @author nikola
 *
 */
public class RotateCommand implements Command {

	/**
	 * Kut rotacije kornjače.
	 */
	private double angle;

	/**
	 * Podrazumijevani konstruktor.
	 * 
	 * @param angle kut rotacije kornjače
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.getDirection().rotate(angle);
	}

}
