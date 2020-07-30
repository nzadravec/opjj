package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco");

		Assert.assertEquals(Integer.valueOf(20), col.get(0));
		Assert.assertEquals("New York", col.get(1));
		Assert.assertEquals("San Francisco", col.get(2));
	}

	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(1);
		col.add(null);
	}

	private ArrayIndexedCollection createArrayIndexedCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco");
		return col;
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetException() {
		ArrayIndexedCollection col = createArrayIndexedCollection();
		col.get(col.size());
	}

	@Test
	public void testClear() {
		ArrayIndexedCollection col = createArrayIndexedCollection();
		col.clear();
		Assert.assertEquals(0, col.size());
	}

	@Test
	public void testInsert() {
		ArrayIndexedCollection col = createArrayIndexedCollection();

		col.insert("test1", 0);
		Assert.assertEquals(4, col.size());
		Assert.assertEquals("test1", col.get(0));

		col.insert("test2", 2);
		Assert.assertEquals(5, col.size());
		Assert.assertEquals("test2", col.get(2));

		col.insert("test3", col.size());
		Assert.assertEquals(6, col.size());
		Assert.assertEquals("test3", col.get(col.size() - 1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertException() {
		ArrayIndexedCollection col = createArrayIndexedCollection();
		col.insert("test", col.size() + 1);
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection col = createArrayIndexedCollection();
		col.add("New York");
		// first occurrence
		Assert.assertEquals(1, col.indexOf("New York"));
		// index of null
		Assert.assertEquals(-1, col.indexOf(null));
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection col = createArrayIndexedCollection();
		
		col.insert("test1", 0);
		col.remove(0);
		Assert.assertEquals(3, col.size());
		Assert.assertEquals(Integer.valueOf(20), col.get(0));
		
		col.insert("test2", 1);
		col.remove(1);
		Assert.assertEquals(3, col.size());
		Assert.assertEquals("New York", col.get(1));
		
		col.insert("test3", col.size());
		col.remove(col.size()-1);
		Assert.assertEquals(3, col.size());
		Assert.assertEquals("San Francisco", col.get(col.size() - 1));
	}

}
