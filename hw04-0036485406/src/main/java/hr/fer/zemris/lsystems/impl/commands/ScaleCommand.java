package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class ScaleCommand implements Command {
	
	private double factor;

	public ScaleCommand(double factor) {
		super();
		
		if(factor < 0 || factor > 1) {
			throw new IllegalArgumentException();
		}
		
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setSegmentSize(state.getSegmentSize() * factor);
	}
	
	@Override
	public String toString() {
		return "ScaleCommand";
	}

}
