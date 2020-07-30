package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {
	
	@Test
	public void testingFactorial() {
		Assert.assertEquals(1, Factorial.factorial(0));
		Assert.assertEquals(3628800, Factorial.factorial(10));
	}
	
	@Test(expected=RuntimeException.class)
	public void testingExceptionInFactorial() {
		Assert.assertEquals(0, Factorial.factorial(-1));
	}
	
	@Test(expected=RuntimeException.class)
	public void testingExceptionInFactorial2() {
		Assert.assertEquals(0, Factorial.factorial(21));
	}

}
