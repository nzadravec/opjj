package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class StudentDatabase {

	/**
	 * Lista studentskih zapisa.
	 */
	List<StudentRecord> studentList;
	/**
	 * Mapa uređenih parova (jmbag, studenski zapis).
	 */
	SimpleHashtable<String, StudentRecord> studentHashtable;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param lines
	 *            lista string objekata, gdje svaki string predstavlja jedan red
	 *            datoteke baze podataka
	 */
	public StudentDatabase(List<String> lines) {
		studentList = new ArrayList<>();
		studentHashtable = new SimpleHashtable<>(lines.size());

		for (String line : lines) {
			line = line.trim();
			String[] ss = line.split("\t");
			if (ss.length != 4) {
				throw new IllegalArgumentException();
			}

			String jmbag = ss[0];
			String lastName = ss[1];
			String firstName = ss[2];
			int finalGrade;
			try {
				finalGrade = Integer.parseInt(ss[3]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}

			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentList.add(record);
			studentHashtable.put(jmbag, record);
		}
	}

	/**
	 * Dohvaća studentski zapis preko njegovog jmbaga.
	 * 
	 * @param jmbag jmbag
	 * @return studentski zapis
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (jmbag == null) {
			throw new NullPointerException();
		}

		return studentHashtable.get(jmbag);
	}

	/**
	 * Vraća listu studenata koji zadvoljavaju dani uvjet.
	 * 
	 * @param filter uvjet
	 * @return lista studenata
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmpList = new ArrayList<>();
		for (StudentRecord record : studentList) {
			if (filter.accepts(record)) {
				tmpList.add(record);
			}
		}

		return tmpList;
	}

}
