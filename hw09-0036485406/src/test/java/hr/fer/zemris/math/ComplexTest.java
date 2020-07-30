package hr.fer.zemris.math;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {
	
	@Test
	public void addTest() {
		Assert.assertEquals(new Complex(2, 2), new Complex(1, 1).add(new Complex(1, 1)));
	}
	
	@Test
	public void subTest() {
		Assert.assertEquals(new Complex(0, 0), new Complex(1, 1).sub(new Complex(1, 1)));
	}
	
	@Test
	public void multiplyTest() {
		Assert.assertEquals(new Complex(0, 8), new Complex(2, 2).multiply(new Complex(2, 2)));
	}
	
	@Test
	public void divideTest() {
		Assert.assertEquals(new Complex(1, 0), new Complex(2, 2).divide(new Complex(2, 2)));
	}
	
	@Test
	public void powerTest() {
		Complex actual = new Complex(2, 2).power(3);
		Complex expected = new Complex(-16, 16);
		Assert.assertEquals(expected.getRe(), actual.getRe(), 1e-6);
		Assert.assertEquals(expected.getIm(), actual.getIm(), 1e-6);
	}
	
	@Test
	public void rootTest() {
		Complex[] expected = new Complex[] {
				new Complex(0.7973, 1.1933),
				new Complex(-1.1933, 0.7973),
				new Complex(-0.7973, -1.1933),
				new Complex(1.1933, -0.7973)};
		
		List<Complex> l = new Complex(-3, -3).root(expected.length);
		Complex[] actual = new Complex[expected.length];
		for(int i = 0; i < expected.length; i++) {
			actual[i] = l.get(i);
		}
		for(int i = 0; i < expected.length; i++) {
			Complex a = actual[i];
			Complex e = expected[i];
			Assert.assertEquals(e.getRe(), a.getRe(), 1e-4);
			Assert.assertEquals(e.getIm(), a.getIm(), 1e-4);
		}
	}

}
