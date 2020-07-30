package hr.fer.zemris.java.hw05.db;

/**
 * Ovo sučelje dohvaća zatraženu vrijednost polja iz {@link StudentRecord}.
 * 
 * @author nikola
 *
 */
public interface IFieldValueGetter {

	/**
	 * Dohvaćuje zatraženu vrijednost.
	 * 
	 * @param record studenski zapis
	 * @return vrijednost atributa
	 */
	public String get(StudentRecord record);

}
