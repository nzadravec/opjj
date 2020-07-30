package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje modelira filter studentskih zapisa {@link StudentRecord}.
 * 
 * @author nikola
 *
 */
public interface IFilter {

	/**
	 * Ispituje da li dani zapis prolazi filter.
	 * 
	 * @param record
	 *            studentski zapis
	 * @return <code>true</code> ako zapis zadovoljava uvjet, inače
	 *         <code>false</code>
	 */
	public boolean accepts(StudentRecord record);

}
