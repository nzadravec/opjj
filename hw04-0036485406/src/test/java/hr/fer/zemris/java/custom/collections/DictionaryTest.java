package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryTest {
	
	private Dictionary createDictionary() {
		Dictionary d = new Dictionary();
		d.put(0, Integer.valueOf(20));
		d.put(1, "New York");
		d.put(2, "San Francisco");
		return d;
	}
	
	@Test
	public void testPut() {
		Dictionary d = createDictionary();

		Assert.assertEquals(Integer.valueOf(20), d.get(0));
		Assert.assertEquals("New York", d.get(1));
		Assert.assertEquals("San Francisco", d.get(2));
	}
	
	@Test
	public void testPut2() {
		Dictionary d = createDictionary();
		d.put(0, "New York");

		Assert.assertEquals("New York", d.get(0));
		Assert.assertEquals("New York", d.get(1));
		Assert.assertEquals("San Francisco", d.get(2));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		Dictionary d = new Dictionary();
		d.put(null, 1);
	}
	
	public void testGet() {
		Dictionary d = createDictionary();
		Assert.assertEquals(null, d.get("0"));
	}
	
	@Test
	public void testClear() {
		Dictionary d = new Dictionary();
		d.clear();
		Assert.assertEquals(0, d.size());
	}

}
