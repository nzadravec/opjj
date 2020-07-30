package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

import static java.lang.Math.pow;

/**
 * Implemetacija sučelja koja modelira objekte koje je moguće konfigurirati i
 * potom pozvati metodu build() koja vraća jedan konkretan Lindermayerov sustav
 * prema zadanoj konfiguraciji. Konfiguracija se može napraviti na dva načina:
 * pozivanjem odgovarajućih metoda ili pak metode configureFromText.
 * 
 * @author nikola
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary commands;
	private Dictionary productions;

	private static final double DEFAULT_UNIT_LENGTH = 0.1;
	private static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	private static final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	private static final double DEFAULT_ANGLE = 0;
	private static final String DEFAULT_AXIOM = "";

	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	private static final String DELIMITER = "\\s+";

	private static ArrayIndexedCollection supportedCommands;

	static {
		supportedCommands = new ArrayIndexedCollection();
		supportedCommands.add("draw");
		supportedCommands.add("skip");
		supportedCommands.add("scale");
		supportedCommands.add("rotate");
		supportedCommands.add("push");
		supportedCommands.add("pop");
		supportedCommands.add("color");
	}

	{
		unitLength = DEFAULT_UNIT_LENGTH;
		unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
		origin = DEFAULT_ORIGIN;
		angle = DEFAULT_ANGLE;
		axiom = DEFAULT_AXIOM;

		commands = new Dictionary();
		productions = new Dictionary();

		commands.put("draw", new DrawCommand(unitLength));
		commands.put("skip", new SkipCommand(unitLength));
		commands.put("scale", new ScaleCommand(unitLengthDegreeScaler));
	}

	/**
	 * Implemetacija Lindermayerovog sustava.
	 * 
	 * @author nikola
	 *
	 */
	public class LSystemImpl implements LSystem {

		@Override
		/**
		 * Crta rezultantni fraktal uporabom primljenog objekta za crtanje linija.
		 */
		public void draw(int level, Painter painter) {
			String generatedString = generate(level);

			Context ctx = new Context();
			Vector2D position = origin.copy();
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(angle);
			Color color = new Color(0, 0, 0);
			double segmentSize = unitLength * pow(unitLengthDegreeScaler, level);
			TurtleState state = new TurtleState(position, direction, color, segmentSize);
			ctx.pushState(state);

			for (int i = 0, n = generatedString.length(); i < n; i++) {
				char symbol = generatedString.charAt(i);
				Command command = (Command) commands.get(symbol);
				if(command == null) {
					continue;
				}
				command.execute(ctx, painter);
			}
		}

		@Override
		/**
		 * Vraća string koji odgovara generiranom nizu nakon zadanog broja razina
		 * primjena produkcija. Ako je razina 0, vraća se aksiom, za razinu 1 vraća se
		 * niz dobiven primjenom produkcija na aksiom, odnosno općenito, ako je razina
		 * k, vraća se niz dobiven primjenom produkcija na niz dobiven za razinu k-1.
		 */
		public String generate(int level) {
			String generatedString = axiom;
			for (int i = 0; i < level; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0, n = generatedString.length(); j < n; j++) {
					char symbol = generatedString.charAt(j);
					String production = (String) productions.get(symbol);
					if (production == null) {
						production = String.valueOf(symbol);
					}
					sb.append(production);
				}

				generatedString = sb.toString();
			}

			return generatedString;
		}

	}

	@Override
	/**
	 * Vraća jedan konkretan Lindermayerov sustav prema zadanoj konfiguraciji.
	 */
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for(String line : lines) {
			line = line.trim();
			if(line.equals("")) {
				continue;
			}
			
			try {
			String directive = line.split(DELIMITER)[0];
			if(directive.equals("origin")) {
				double x = Double.parseDouble(line.split(DELIMITER)[1]);
				double y = Double.parseDouble(line.split(DELIMITER)[2]);
				setOrigin(x, y);
			} else if(directive.equals("angle")) {
				double angle = Double.parseDouble(line.split(DELIMITER)[1]);
				setAngle(angle);
			} else if(directive.equals("unitLength")) {
				double unitLength = Double.parseDouble(line.split(DELIMITER)[1]);
				setUnitLength(unitLength);
			} else if(directive.equals("unitLengthDegreeScaler")) {
				double unitLengthDegreeScaler;
				if(line.split(DELIMITER, 2)[1].contains("/")) {
					double numerator = Double.parseDouble(line.split(DELIMITER, 2)[1].split("/")[0]);
					double denominator = Double.parseDouble(line.split(DELIMITER, 2)[1].split("/")[1]);
					unitLengthDegreeScaler = numerator / denominator;
				} else {
					unitLengthDegreeScaler = Double.parseDouble(line.split(DELIMITER)[1]);
				}
				setUnitLengthDegreeScaler(unitLengthDegreeScaler);
			} else if(directive.equals("command")) {
				char symbol = line.split(DELIMITER)[1].charAt(0);
				String action = line.split(DELIMITER, 3)[2];
				registerCommand(symbol, action);
			} else if(directive.equals("axiom")) {
				String axiom = line.split(DELIMITER)[1];
				setAxiom(axiom);
			} else if(directive.equals("production")) {
				char symbol = line.split(DELIMITER)[1].charAt(0);
				String production = line.split(DELIMITER, 3)[2];
				registerProduction(symbol, production);
			} else {
				throw new IllegalArgumentException();
			}
			
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}

		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		if (action == null) {
			throw new NullPointerException();
		}

		if (action.split(" +").length < 1 || action.split(" +").length > 2) {
			throw new IllegalArgumentException();
		}

		String command = action.split(" +")[0];
		if (!supportedCommands.contains(command)) {
			throw new IllegalArgumentException();
		}

		try {

			if (command.equals("draw")) {
				double step = Double.parseDouble(action.split(" +")[1]);
				commands.put(symbol, new DrawCommand(step));
			} else if (command.equals("skip")) {
				double step = Double.parseDouble(action.split(" +")[1]);
				commands.put(symbol, new SkipCommand(step));
			} else if (command.equals("scale")) {
				double factor = Double.parseDouble(action.split(" +")[1]);
				commands.put(symbol, new ScaleCommand(factor));
			} else if (command.equals("rotate")) {
				double angle = Double.parseDouble(action.split(" +")[1]);
				commands.put(symbol, new RotateCommand(angle));
			} else if (command.equals("push")) {
				commands.put(symbol, new PushCommand());
			} else if (command.equals("pop")) {
				commands.put(symbol, new PopCommand());
			} else if (command.equals("color")) {
				String colorStr = action.split(" +")[1];
				Color color = new Color(Integer.valueOf(colorStr.substring(0, 2), 16),
						Integer.valueOf(colorStr.substring(2, 4), 16), Integer.valueOf(colorStr.substring(4, 6), 16));

				commands.put(symbol, new ColorCommand(color));
			} else {
				throw new IllegalArgumentException();
			}

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}

		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		if (production == null) {
			throw new NullPointerException();
		}

//		if (commands.get(symbol) == null) {
//			throw new IllegalArgumentException();
//		}
//
//		for (int i = 0, n = production.length(); i < n; i++) {
//			if (commands.get(production.charAt(i)) != null) {
//				throw new IllegalArgumentException();
//			}
//		}

		productions.put(symbol, production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		if (angle < 0 || angle > 360) {
			throw new IllegalArgumentException();
		}

		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		if (axiom == null) {
			throw new NullPointerException();
		}

//		for (int i = 0, n = axiom.length(); i < n; i++) {
//			if (commands.get(axiom.charAt(i)) == null) {
//				throw new IllegalArgumentException();
//			}
//		}

		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		if (x < 0 || x > 1 || y < 0 || y > 1) {
			throw new IllegalArgumentException();
		}

		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		if (unitLength < 0 || unitLength > 1) {
			throw new IllegalArgumentException();
		}

		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		if (unitLengthDegreeScaler < 0 || unitLengthDegreeScaler > 1) {
			throw new IllegalArgumentException();
		}

		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
