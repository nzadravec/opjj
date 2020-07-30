package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {
	
	@Test
	public void testTranslate() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		Vector2D v2 = new Vector2D(-0.3, 0.2);
		Vector2D v = new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
		v1.translate(v2);
		Assert.assertEquals(true, v1.equals(v));
	}
	
	@Test
	public void testTranslated() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		Vector2D v2 = new Vector2D(-0.3, 0.2);
		Vector2D v = new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
		Assert.assertEquals(true, v1.translated(v2).equals(v));
	}
	
	@Test
	public void testRotate() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		double angle = 60;
		double angleInRad = angle * PI / 180.0;
		Vector2D v = new Vector2D(v1.getX() * cos(angleInRad) - v1.getY() * sin(angleInRad), 
				v1.getX() * sin(angleInRad) + v1.getY() * cos(angleInRad));
		v1.rotate(angle);
		Assert.assertEquals(true, v1.equals(v));
	}
	
	@Test
	public void testRotated() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		double angle = 60;
		double angleInRad = angle * PI / 180.0;
		Vector2D v = new Vector2D(v1.getX() * cos(angleInRad) - v1.getY() * sin(angleInRad), 
				v1.getX() * sin(angleInRad) + v1.getY() * cos(angleInRad));
		Assert.assertEquals(true, v1.rotated(angle).equals(v));
	}
	
	@Test
	public void testScale() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		double scale = 3.1;
		Vector2D v = new Vector2D(v1.getX() * scale, v1.getY() * scale);
		v1.scale(scale);
		Assert.assertEquals(true, v1.equals(v));
	}
	
	@Test
	public void testScaled() {
		Vector2D v1 = new Vector2D(0.1, -0.3);
		double scale = 3.1;
		Vector2D v = new Vector2D(v1.getX() * scale, v1.getY() * scale);
		Assert.assertEquals(true, v1.scaled(scale).equals(v));
	}

}
