package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValueWrapperTest {
	
	@Test
	public void testAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testAdd2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}
	
	@Test
	public void testAdd3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testAdd4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}
	
	@Test
	public void testNumCompare() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		int i = v1.numCompare(v2.getValue());
		assertEquals(true, i == 0);
		assertEquals(null, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testNumCompare2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		int i = v3.numCompare(v4.getValue());
		assertEquals(true, i > 0);
		assertEquals("1.2E1", v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}
	
	@Test
	public void testNumCompare3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		int i = v6.numCompare(v5.getValue());
		assertEquals(true, i < 0);
		assertEquals("12", v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testNumCompare4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.numCompare(v8.getValue());
	}

}
