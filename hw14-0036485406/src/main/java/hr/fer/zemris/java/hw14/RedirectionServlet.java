package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet sends redirection to clients browser so that clients browser as a
 * follow-up action requests <code>/servleti/index.html</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/index.html")
public class RedirectionServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();

		resp.sendRedirect("servleti/index.html");

		pw.close();
	}

}
