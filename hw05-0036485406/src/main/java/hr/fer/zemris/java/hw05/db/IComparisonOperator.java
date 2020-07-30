package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje operatora relacije dvaju string literala.
 * 
 * @author nikola
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Ispituje relaciju dvaju string literala.
	 * 
	 * @param value1 prvi string literal
	 * @param value2 drugi string literal
	 * @return <code>true</code> ako je relacija zadovoljena, inače <code>false</code>
	 */
	public boolean satisfied(String value1, String value2);

}
