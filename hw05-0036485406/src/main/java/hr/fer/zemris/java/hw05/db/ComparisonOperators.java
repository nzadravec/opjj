package hr.fer.zemris.java.hw05.db;

/**
 * Podržani operatori relacije dvaju string literala.
 * 
 * @author nikola
 *
 */
public class ComparisonOperators {

	/**
	 * Ispituje je li prvi string literal manji od drugog.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	/**
	 * Ispituje je li prvi string literal manji ili jednak od drugog.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
	/**
	 * Ispituje je li prvi string literal veći od drugog.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	/**
	 * Ispituje je li prvi string literal veći ili jednak od drugog.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	/**
	 * Ispituje je li prvi string literal jednak drugom.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.equals(value2);
	/**
	 * Ispituje je li prvi string literal različit od drugog.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> !value1.equals(value2);
	/**
	 * Ispituje je li string literal (prvi argument) jednak uzorku (drugi argument).
	 * Uzorak može sadržavati najviše jedan zamjenski znak <code>*</code>, gdje
	 * istoimeni može nadomjestiti nula, jedan ili više znakova u string literalu.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {
			String[] ss = value2.split("\\*");
			if (ss.length == 1) {
				return value1.matches(value2.split("\\*")[0] + "(.*)");
			} else {
				return value1.matches(value2.split("\\*")[0] + "(.*)" + value2.split("\\*")[1]);
			}
		}
	};

}
