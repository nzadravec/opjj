package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class FieldValueGettersTest {
	
	@Test
	public void test() {
		String jmbag = "0036485406";
		String lastName = "Zadravec";
		String firstName = "Nikola";
		int finalGrade = 3;
		StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
		Assert.assertEquals("Nikola", FieldValueGetters.FIRST_NAME.get(record));
		Assert.assertEquals("Zadravec", FieldValueGetters.LAST_NAME.get(record));
		Assert.assertEquals("0036485406", FieldValueGetters.JMBAG.get(record));
	}

}
