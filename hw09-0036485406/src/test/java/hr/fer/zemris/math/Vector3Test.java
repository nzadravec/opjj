package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {
	
	private double delta = 1E-6;
	
	@Test
	public void addTest() {
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = new Vector3(0, 0, 1);
		Vector3 actual = j.add(k);
		Vector3 expected = new Vector3(0, 1, 1);
		assertEquals(expected, actual, delta);
		actual = k.add(j);
		expected = new Vector3(0, 1, 1);
		assertEquals(expected, actual, delta);
	}
	
	@Test
	public void subTest() {
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = new Vector3(0, 0, 1);
		Vector3 actual = j.sub(k);
		Vector3 expected = new Vector3(0, 1, -1);
		assertEquals(expected, actual, delta);
	}
	
	@Test
	public void dotTest() {
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = new Vector3(0, 0, 1);
		double actual = j.dot(k);
		double expected = 0;
		Assert.assertEquals(expected, actual, delta);
		actual = k.dot(j);
		Assert.assertEquals(expected, actual, delta);
	}
	
	@Test
	public void crossTest() {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 actual = i.cross(j);
		Vector3 expected = new Vector3(0, 0, 1);
		assertEquals(expected, actual, delta);
		actual = j.cross(i);
		expected = new Vector3(0, 0, -1);
		assertEquals(expected, actual, delta);
	}
	
	@Test
	public void scaleTest() {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 actual = i.scale(0.3);
		Vector3 expected = new Vector3(0.3, 0, 0);
		assertEquals(expected, actual, delta);
	}
	
	@Test
	public void cosAngleTest() {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 j = new Vector3(0, 1, 0);
		double actual = i.cosAngle(j);
		double expected = 0;
		Assert.assertEquals(expected, actual, delta);
	}
	
	@Test
	public void normalizedTest() {
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = new Vector3(0, 0, 1);
		Vector3 l = k.add(j).scale(5);
		l = l.normalized();
		double actual = l.norm();
		double expected = 1;
		Assert.assertEquals(expected, actual, delta);
	}
	
	private void assertEquals(Vector3 expected, Vector3 actual, double delta) {
		Assert.assertEquals(expected.getX(), actual.getX(), delta);
		Assert.assertEquals(expected.getY(), actual.getY(), delta);
		Assert.assertEquals(expected.getZ(), actual.getZ(), delta);
	}

}
