package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sends to a client a redirection to page <code>/servleti/main</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/index.jsp")
public class RedirectionServlet extends HttpServlet {
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
