package hr.fer.zemris.java.hw05.db;

/**
 * Zapis studenta.
 * 
 * @author nikola
 *
 */
public class StudentRecord {
	
	/**
	 * Jmbag studenta.
	 */
	private String jmbag;
	/**
	 * Prezime studenta.
	 */
	private String lastName;
	/**
	 * Ime studenta.
	 */
	private String firstName;
	/**
	 * Završna ocjena.
	 */
	private int finalGrade;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param jmbag jmbag studenta
	 * @param lastName prezime studenta
	 * @param firstName ime studenta
	 * @param finalGrade završna ocjena
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Vraća jmbag studenta.
	 * 
	 * @return jmbag studenta
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Vraća prezime studenta.
	 * 
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Vraća ime studenta.
	 * 
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Vraća završnu ocjenu studenta.
	 * 
	 * @return završna ocjena
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
