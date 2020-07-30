package hr.fer.zemris.java.hw05.db;

/**
 * Podržani dohvati atributa razreda {@link StudentRecord}.
 * 
 * @author nikola
 *
 */
public class FieldValueGetters {
	
	/**
	 * Dohvaća ime studenta.
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	/**
	 * Dohvaća prezime studenta.
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	/**
	 * Dohvaća jmbag studenta.
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();

}
