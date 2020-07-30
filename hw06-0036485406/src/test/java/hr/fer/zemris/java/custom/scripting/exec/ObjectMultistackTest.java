package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		assertEquals(true, multistack.isEmpty("year"));
		multistack.push("year", year);
		assertEquals(false, multistack.isEmpty("year"));
		assertEquals(true, multistack.isEmpty("year1"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testPushNameNull() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push(null, year);
	}
	
	@Test(expected=NullPointerException.class)
	public void testIsEmtpy() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.isEmpty(null);
	}
	
	@Test
	public void testPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
	}
	
	@Test(expected=NullPointerException.class)
	public void testPeekNameNull() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek(null);
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPeekEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.peek("year");
	}
	
	@Test
	public void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("year", price);
		assertEquals(200.51, multistack.pop("year").getValue());
		assertEquals(Integer.valueOf(2000), multistack.pop("year").getValue());
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPopEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.pop("year");
		multistack.pop("year");
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPopEmptyStack1() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.pop("year");
	}

}
