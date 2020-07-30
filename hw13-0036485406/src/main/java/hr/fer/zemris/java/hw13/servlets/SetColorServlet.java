package hr.fer.zemris.java.hw13.servlets;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet remembers the selected color as user's session attribute pickedBgCol.
 * 
 * @author nikola
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String colorString = req.getParameter("bgCol");
		if(colorString != null) {
			try {
			    Color.decode("#"+colorString);
			    req.getSession().setAttribute("pickedBgCol", colorString);
			} catch (Exception ignorable) {
			}
		}
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
