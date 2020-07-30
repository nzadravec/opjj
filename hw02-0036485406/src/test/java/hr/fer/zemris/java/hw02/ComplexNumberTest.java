package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

public class ComplexNumberTest {
	
	@Test
	public void testFromReal() {
		Assert.assertEquals(new ComplexNumber(5, 0), ComplexNumber.fromReal(5));
	}
	
	@Test
	public void testFromImaginary() {
		Assert.assertEquals(new ComplexNumber(0, 5), ComplexNumber.fromImaginary(5));
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		Assert.assertEquals(new ComplexNumber(5, 0), ComplexNumber.fromMagnitudeAndAngle(5, 0));
		Assert.assertEquals(new ComplexNumber(0, 5), ComplexNumber.fromMagnitudeAndAngle(5, PI/2));
		Assert.assertEquals(new ComplexNumber(sqrt(2) / 2, sqrt(2) / 2), ComplexNumber.fromMagnitudeAndAngle(1, PI/4));
	}
	
	@Test
	public void testParse() {
		Assert.assertEquals(new ComplexNumber(3.51, 0), ComplexNumber.parse("3.51"));
	}
	
	@Test
	public void testParse1() {
		Assert.assertEquals(new ComplexNumber(-3.17, 0), ComplexNumber.parse("-3.17"));
	}
	
	@Test
	public void testParse2() {
		Assert.assertEquals(new ComplexNumber(0, -2.71), ComplexNumber.parse("-2.71i"));
	}
	
	@Test
	public void testParse3() {
		Assert.assertEquals(new ComplexNumber(0, 1), ComplexNumber.parse("i"));
	}
	
	@Test
	public void testParse4() {
		Assert.assertEquals(new ComplexNumber(1, 0), ComplexNumber.parse("1"));
	}
	
	@Test
	public void testParse5() {
		Assert.assertEquals(new ComplexNumber(-2.71, -3.15), ComplexNumber.parse("-2.71-3.15i"));
	}
	
	@Test
	public void testAdd() {
		Assert.assertEquals(new ComplexNumber(2, 2), new ComplexNumber(1, 1).add(new ComplexNumber(1, 1)));
	}
	
	@Test
	public void testSub() {
		Assert.assertEquals(new ComplexNumber(0, 0), new ComplexNumber(1, 1).sub(new ComplexNumber(1, 1)));
	}
	
	@Test
	public void testMul() {
		Assert.assertEquals(new ComplexNumber(0, 8), new ComplexNumber(2, 2).mul(new ComplexNumber(2, 2)));
	}
	
	@Test
	public void testDiv() {
		Assert.assertEquals(new ComplexNumber(1, 0), new ComplexNumber(2, 2).div(new ComplexNumber(2, 2)));
	}
	
	@Test(expected = DivideByZeroException.class)
	public void testDivException() {
		Assert.assertEquals(new ComplexNumber(1, 0), new ComplexNumber(2, 2).div(new ComplexNumber(0, 0)));
	}
	
	@Test
	public void testPower() {
		Assert.assertEquals(new ComplexNumber(-16, 16), new ComplexNumber(2, 2).power(3));
	}
	
	@Test
	public void testRoot() {
		ComplexNumber[] cs = new ComplexNumber[] {
				new ComplexNumber(1.1933, -0.7973),
				new ComplexNumber(0.7973, 1.1933),
				new ComplexNumber(-1.1933, 0.7973),
				new ComplexNumber(-0.7973, -1.1933)};
		
		Assert.assertArrayEquals(cs, new ComplexNumber(-3, -3).root(4));
	}

}
