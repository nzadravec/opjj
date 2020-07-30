package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class ComplexPolynomialTest {
	
	@Test
	public void deriveTest() {
		Complex[] factors = new Complex[] {
				new Complex(1, 0),
				new Complex(5, 0),
				new Complex(2, 0),
				new Complex(7, 2)
		};
		ComplexPolynomial p =  new ComplexPolynomial(factors);
		
		Complex[] factors2 = new Complex[] {
				new Complex(5, 0),
				new Complex(2*2, 0),
				new Complex(7*3, 2*3)
		};
		ComplexPolynomial dp = new ComplexPolynomial(factors2);
		
		Assert.assertEquals(dp, p.derive());
	}
	
	@Test
	public void applyTest() {
		Complex[] factors = new Complex[] {
				new Complex(1, 0),
				new Complex(5, 0),
				new Complex(2, 0),
				new Complex(7, 2)
		};
		ComplexPolynomial p = new ComplexPolynomial(factors);
		
		Complex z = new Complex(1.2, -3);
		Complex actual = p.apply(z);
		Complex expected = new Complex(-250.904, 7.536);
		
		Assert.assertEquals(expected.getRe(), actual.getRe(), 1e-4);
		Assert.assertEquals(expected.getIm(), actual.getIm(), 1e-4);
	}

}
