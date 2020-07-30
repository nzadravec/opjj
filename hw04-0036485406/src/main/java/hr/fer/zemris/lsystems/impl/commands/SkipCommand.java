package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Kao {@link DrawCommand} samo što ne povlači liniju.
 * 
 * @author nikola
 *
 */
public class SkipCommand implements Command {

	/**
	 * Mjera pomaka kornjače.
	 */
	private double step;

	/**
	 * Podrazumijevani konstruktor.
	 * 
	 * @param step mjera pomaka kornjače
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getPosition();
		position.translate(state.getDirection().scaled(step));
	}
	
}
