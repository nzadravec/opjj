package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

/**
 * Predstavlja relativni pokazivač na kartezijanskoj ravnini.
 * 
 * @author nikola
 *
 */
public class TurtleState {
	
	/**
	 * Trenutna pozicija na kojoj se kornjača nalazi.
	 */
	private Vector2D position;
	/**
	 * Smjer u kojem kornjača gleda.
	 */
	private Vector2D direction;
	/**
	 * Boja kojom kornjača crta.
	 */
	private Color color;
	/**
	 * Efektivna duljina pomaka.
	 */
	private double segmentSize;
	
	private static double TRESHOLD = 1E-6;
	
	/**
	 * Podrazumijevani konstruktor.
	 * 
	 * @param position trenutna pozicija na kojoj se kornjača nalazi
	 * @param direction smjer u kojem kornjača gleda
	 * @param color boja kojom kornjača crta
	 * @param segmentSize efektivna duljina pomaka
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double segmentSize) {
		super();
		
		if(position == null || direction == null || color == null) {
			throw new NullPointerException();
		}
		
		double x = position.getX();
		double y = position.getY();
		if(x < 0 || x > 1 || y < 0 || y > 1) {
			throw new IllegalArgumentException();
		}
		
		x = direction.getX();
		y = direction.getY();
		if(abs(sqrt(x * x + y * y) - 1) > TRESHOLD) {
			throw new IllegalArgumentException();
		}
		
		// treba li provjeriti i segmetSize?
		
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.segmentSize = segmentSize;
	}
	
	/**
	 * Stvara kopiju trenutne kornjače.
	 * 
	 * @return kopija trenutne kornjače
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), new Color(color.getRed(), color.getGreen(), color.getBlue()), segmentSize);
	}

	/**
	 * Vraća trenutnu poziciju na kojoj se kornjača nalazi.
	 * 
	 * @return trenutna pozicija na kojoj se kornjača nalazi
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Vraća smjer u kojem kornjača gleda.
	 * 
	 * @return smjer u kojem kornjača gleda
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Vraća boju kojom kornjača crta.
	 * 
	 * @return boja kojom kornjača crta
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Vraća efektivnu duljina pomaka.
	 * 
	 * @return efektivna duljina pomaka
	 */
	public double getSegmentSize() {
		return segmentSize;
	}

	/**
	 * Postavlja trenutnu poziciju na kojoj se kornjača nalazi.
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Postavlja smjer u kojem kornjača gleda.
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Postavlja boju kojom kornjača crta.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Postavlja efektivnu duljina pomaka.
	 */
	public void setSegmentSize(double segmentSize) {
		this.segmentSize = segmentSize;
	}
	
}
