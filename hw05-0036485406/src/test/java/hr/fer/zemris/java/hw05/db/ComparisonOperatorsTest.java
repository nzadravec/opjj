package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void test() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		Assert.assertEquals(true, oper.satisfied("Ana", "Jasna"));
		
		oper = ComparisonOperators.LIKE;
		Assert.assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		Assert.assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		Assert.assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		Assert.assertEquals(true, oper.satisfied("AAAA", "*AAAA"));
	}

}
