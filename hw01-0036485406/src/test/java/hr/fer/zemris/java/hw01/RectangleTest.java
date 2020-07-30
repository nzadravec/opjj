package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {
	
	public static double EPSILON = 1E-8;
	
	@Test
	public void rectangleSurfaceTest() {
		Assert.assertEquals(1, Rectangle.rectangleSurface(1, 1), EPSILON);
	}
	
	@Test
	public void rectanglePerimeterTest() {
		Assert.assertEquals(4, Rectangle.rectanglePerimeter(1, 1), EPSILON);
	}
	
	@Test(expected=RuntimeException.class)
	public void checkRectangleParamsTest() {
		Rectangle.checkRectangleParams(0, 1);
	}

}
