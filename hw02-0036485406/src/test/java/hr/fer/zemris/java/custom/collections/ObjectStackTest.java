package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class ObjectStackTest {
	
	@Test
	public void testPush() {
		ObjectStack stack = new ObjectStack();
		stack.push(Integer.valueOf(20));
		stack.push("New York");
		stack.push("San Francisco");

		Assert.assertEquals(3, stack.size());
		Assert.assertEquals("San Francisco", stack.peek());
	}
	
	@Test(expected = NullPointerException.class)
	public void testPushException() {
		ObjectStack stack = new ObjectStack();
		stack.push(null);
	}
	
	@Test
	public void testPop() {
		ObjectStack stack = new ObjectStack();
		stack.push(Integer.valueOf(20));
		stack.push("New York");
		stack.push("San Francisco");
		
		Assert.assertEquals("San Francisco", stack.pop());
		Assert.assertEquals(2, stack.size());
		Assert.assertEquals("New York", stack.pop());
	}

}
