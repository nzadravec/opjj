package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ConditionalExpressionTest {

	@Test
	public void test() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Zad*",
				ComparisonOperators.LIKE);
		String jmbag = "0036485406";
		String lastName = "Zadravec";
		String firstName = "Nikola";
		int finalGrade = 3;
		StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		Assert.assertEquals(true, recordSatisfies);
	}

}
