package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * U trenutno stanje kornjače zapisuje predanu boju.
 * @author nikola
 *
 */
public class ColorCommand implements Command {

	/**
	 * Boja kojom kornjača crta.
	 */
	private Color color;

	/**
	 * Podrazumijevani konstruktor.
	 * 
	 * @param color boja kojom kornjača crta
	 */
	public ColorCommand(Color color) {
		super();
		
		if(color == null) {
			throw new NullPointerException();
		}
		
		this.color = color;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setColor(color);
	}
	
}
