package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * Instance ovog razreda omotaju jednu vrijednost tipa {@link Object}. Razred
 * predstavlja vrijednosti koje su povezane s ključevima razreda
 * {@link ObjectMultistack}.
 * 
 * @author nikola
 *
 */
public class ValueWrapper {

	/**
	 * Omotana vrijednost.
	 */
	private Object value;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param value
	 *            vrijednost
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Vraća omotanu vrijednost.
	 * 
	 * @return omotana vrijednost
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Postavlja novu vrijednost u omotač.
	 * 
	 * @param value
	 *            nova vrijednost
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Dodaje vrijednost danog omotača vrijednosti trenutnog.
	 * 
	 * 
	 * @param incValue
	 *            dani omotač
	 */
	public void add(Object incValue) {
		performOperation(value, incValue, (x, y) -> x + y);
	}

	/**
	 * Oduzima vrijednost danog omotača od vrijednosti trenutnog.
	 * 
	 * @param decValue
	 *            dani omotač
	 */
	public void subtract(Object decValue) {
		performOperation(value, decValue, (x, y) -> x - y);
	}

	/**
	 * Množi vrijednost ovog omotača s vrijednosti danog.
	 * 
	 * @param mulValue
	 *            dani omotač
	 */
	public void multiply(Object mulValue) {
		performOperation(value, mulValue, (x, y) -> x * y);
	}

	/**
	 * Dijeli vrijednost ovog omotača s vrijednosti danog.
	 * 
	 * @param mulValue
	 *            dani omotač
	 */
	public void divide(Object divValue) {
		performOperation(value, divValue, (x, y) -> x / y);
	}
	
	private void performOperation(Object first, Object second, BinaryOperator<Double> operator) {
		checkRestrictionsForAritmeticOperators(first);
		checkRestrictionsForAritmeticOperators(second);
		
		Number currValue = cast(first);
		Number argument = cast(second);
		
		Double result = operator.apply(currValue.doubleValue(), argument.doubleValue());
		if (currValue instanceof Double || argument instanceof Double) {
			value = result;
			return;
		}
		
		value = result.intValue();
	}

	/**
	 * Usporedba vrijednosti ovog omotača i vrijednosti danog.
	 * 
	 * @param withValue
	 *            dani omotač
	 * @return manje od <code>0</code> ako je vrijednost ovog omotača manja od
	 *         vrijednosti danog, veće od <code>0</code> ako je vrijednost ovog
	 *         omotača veća od vrijednosti danog i <code>0</code> ako je vrijednost
	 *         ovog omotača jednaka vrijednosti danog.
	 */
	public int numCompare(Object withValue) {
		Number value1 = cast(value);
		Number value2 = cast(withValue);

		double d1 = value1.doubleValue();
		double d2 = value2.doubleValue();

		return Double.compare(d1, d2);
	}

	/**
	 * Provjerava je li dani objekt valjanog tipa za aritmetičke operacije.
	 * 
	 * @param o
	 *            dani objekt
	 */
	private void checkRestrictionsForAritmeticOperators(Object o) {
		if (!allowedValue(o)) {
			throw new RuntimeException("Restrictions for aritmetic operation is not satisfied.");
		}
	}
	
	private Number cast(Object o) {
		if (o == null) {
			return Integer.valueOf(0);
		}
		if (o instanceof Integer) {
			return (Integer) o;
		}
		if (o instanceof Double) {
			return (Double) o;
		}
		if (o instanceof String) {
			String s = (String) o;
			if(s.contains(".") || s.contains("e") || s.contains("E")) {
				try {
					return Double.parseDouble(s);
				} catch (NumberFormatException e) {
					throw new RuntimeException("Error while parsing string into double.");
				}
			}
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Error while parsing string into integer.");
			}
		}
		
		throw new RuntimeException("Value must be either null, Integer, Double or String representing int or double.");
	}

	/**
	 * Provjerava je li dani objekt valjanog tipa za aritmetičke operacije. Valjani
	 * tipovi za aritmetičke operacije su: <code>null</code>, {@link Integer},
	 * {@link Double} i {@link String} koji se može parsirati u int ili double.
	 * 
	 * @param o
	 *            dani objekt
	 * @return <code>true</code> ako je valjanog tipa, inače <code>false</code>
	 */
	private boolean allowedValue(Object o) {
		if (o == null)
			return true;
		if (o instanceof Integer)
			return true;
		if (o instanceof Double)
			return true;
		if (o instanceof String)
			return true;

		return false;
	}

}
