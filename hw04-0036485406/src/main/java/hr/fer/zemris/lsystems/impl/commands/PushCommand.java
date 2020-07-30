package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Stanje s vrha stoga kopira i kopiju stavlja na stog.
 * 
 * @author nikola
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		ctx.pushState(state.copy());
	}
	
}
