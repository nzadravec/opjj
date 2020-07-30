package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Računa gdje kornjača mora otići; povlači liniju zadanom bojom od trenutne
 * pozicije kornjače do izračunate i pamti u trenutnom stanju novu poziciju
 * kornjače.
 * 
 * @author nikola
 *
 */
public class DrawCommand implements Command {

	/**
	 * Mjera pomaka kornjače.
	 */
	private double step;

	/**
	 * Podrazumijevani konstruktor.
	 * 
	 * @param step mjera pomaka kornjače
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getPosition();
		double x0 = position.getX();
		double y0 = position.getY();
		position.translate(state.getDirection().scaled(step * state.getSegmentSize()));
		double x1 = position.getX();
		double y1 = position.getY();
		painter.drawLine(x0, y0, x1, y1, state.getColor(), (float) state.getSegmentSize());
	}

}
