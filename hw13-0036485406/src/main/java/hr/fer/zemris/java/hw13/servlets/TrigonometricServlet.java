package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toDegrees;

/**
 * Servlet calculates values of trigonometric functions sin(x) and cos(x) for
 * all integer angles in a range determined by URL parameters a and b.
 * 
 * @author nikola
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aString = req.getParameter("a");
		int a;
		if (aString != null) {
			a = Integer.parseInt(aString);
		} else {
			a = 0;
		}
		String bString = req.getParameter("b");
		int b;
		if (bString != null) {
			b = Integer.parseInt(bString);
		} else {
			b = 360;
		}

		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		if (b > (a + 720)) {
			b = a + 720;
		}

		int size = b - a + 1;
		Triplet[] triplets = new Triplet[size];
		for (int i = 0; i < triplets.length; i++) {
			int angle = a + i;
			triplets[i] = new Triplet(i, sin(toDegrees(angle)), cos(toDegrees(angle)));
		}

		req.setAttribute("triplets", triplets);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	public static class Triplet {
		private double first;
		private double second;
		private double third;

		public Triplet(double first, double second, double third) {
			super();
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public double getFirst() {
			return first;
		}

		public void setFirst(double first) {
			this.first = first;
		}

		public double getSecond() {
			return second;
		}

		public void setSecond(double second) {
			this.second = second;
		}

		public double getThird() {
			return third;
		}

		public void setThird(double third) {
			this.third = third;
		}

	}

}
