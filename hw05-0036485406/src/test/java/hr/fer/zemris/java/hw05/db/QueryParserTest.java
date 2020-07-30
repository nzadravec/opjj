package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class QueryParserTest {

	@Test
	public void test1() {
		QueryParser qp1 = new QueryParser(" jmbag=\"0123456789\"");
		Assert.assertEquals(true, qp1.isDirectQuery());
		Assert.assertEquals("0123456789", qp1.getQueriedJMBAG());
		Assert.assertEquals(1, qp1.getQuery().size());
	}
	
	@Test
	public void test2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		Assert.assertEquals(false, qp2.isDirectQuery());
		Assert.assertEquals(2, qp2.getQuery().size());
	}

	@Test(expected = IllegalStateException.class)
	public void testException() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		qp2.getQueriedJMBAG();
	}

}
