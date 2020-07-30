package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;
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
		value = performOperation(value, incValue, (i1, i2) -> i1 + i2, (d1, d2) -> d1 + d2);
	}

	/**
	 * Oduzima vrijednost danog omotača od vrijednosti trenutnog.
	 * 
	 * @param decValue
	 *            dani omotač
	 */
	public void subtract(Object decValue) {
		value = performOperation(value, decValue, (i1, i2) -> i1 - i2, (d1, d2) -> d1 - d2);
	}

	/**
	 * Množi vrijednost ovog omotača s vrijednosti danog.
	 * 
	 * @param mulValue
	 *            dani omotač
	 */
	public void multiply(Object mulValue) {
		value = performOperation(value, mulValue, (i1, i2) -> i1 * i2, (d1, d2) -> d1 * d2);
	}

	/**
	 * Dijeli vrijednost ovog omotača s vrijednosti danog.
	 * 
	 * @param mulValue
	 *            dani omotač
	 */
	public void divide(Object divValue) {
		value = performOperation(value, divValue, (i1, i2) -> i1 / i2, (d1, d2) -> d1 / d2);
	}

	/**
	 * Izvršava jednu od danih operacija nad elementima ovisno o njihovom tipu. Ako
	 * se oba elementa mogu castati u int onda se koristi binarna operacija nad int
	 * vrijednostima, inače se koristi binarna operacija nad double vrijednostima.
	 * 
	 * @param first
	 *            prvi element binarne operacije
	 * @param second
	 *            drugi element binarne operacije
	 * @param operatori
	 *            binarna operacija nad int vrijednostima
	 * @param operatord
	 *            binarna operacija nad double vrijednostima
	 * @return rezultat jedne od binarnih operacija nad danim elementima
	 */
	private Object performOperation(Object first, Object second, BinaryOperator<Integer> operatori,
			BiFunction<Double, Double, ?> operatord) {
		checkRestrictionsForAritmeticOperators(first);
		checkRestrictionsForAritmeticOperators(second);

		Integer icurrValue = castToInteger(first);
		Integer iargument = castToInteger(second);
		if (icurrValue != null && iargument != null) {
			return operatori.apply(icurrValue, iargument);
		}

		Double dcurrValue = castToDouble(first);
		Double dargument = castToDouble(second);
		return operatord.apply(dcurrValue, dargument);
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
		Object o = performOperation(value, withValue, (i1, i2) -> i1.compareTo(i2), (d1, d2) -> d1.compareTo(d2));
		Integer i = (Integer) o;
		return i.intValue();
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

	/**
	 * Cast-a dani objekt u {@link Integer}. Ako je objekt <code>null</code> metoda
	 * vraća {@link Integer} vrijednosti <code>0</code>, ako je dani objekt
	 * {@link String} metoda ga pokušava pretvoriti u {@link Integer}.
	 * 
	 * @param o
	 *            dani objekt
	 * @return {@link Integer}
	 * @throws NumberFormatException
	 *             ako objekt {@link String} a ne predstavlja cijeli broj.
	 */
	private Integer castToInteger(Object o) {
		if (value == null) {
			return Integer.valueOf(0);
		}
		if (o instanceof Integer) {
			return (Integer) o;
		}
		;
		if (o instanceof String) {
			String s = (String) o;
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				if (!s.contains(".") || !s.contains("E")) {
					throw new RuntimeException();
				}
			}
		}

		return null;
	}

	/**
	 * Cast-a dani objekt u {@link Double}. Ako je objekt {@link Integer} metoda
	 * vraća {@link Double} castane vrijendnosti int u double, ako je dani objekt
	 * {@link String} metoda ga pokušava pretvoriti u {@link Double}.
	 * 
	 * @param o
	 *            dani objekt
	 * @return {@link Double}
	 * @throws NumberFormatException
	 *             ako objekt {@link String} a ne predstavlja decimalni broj.
	 */
	private Double castToDouble(Object o) {
		if (o instanceof Integer) {
			Integer i = (Integer) o;
			return (double) i;
		}
		;
		if (o instanceof Double) {
			return (Double) o;
		}
		;
		if (o instanceof String) {
			String s = (String) o;
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		}

		return null;
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
