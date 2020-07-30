package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.web.forms.RegistrationForm;

/**
 * Dispatches client to <code>"/WEB-INF/pages/Register.jsp"</code> page if
 * there's not information about client in current session map (ie. client is
 * not logged in); otherwise, it dispatches client to
 * <code>"/WEB-INF/pages/Registered.jsp"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/register")
public class NewUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("current.user.id") != null) {
			req.getRequestDispatcher("/WEB-INF/pages/Registered.jsp").forward(req, resp);
		}

		RegistrationForm f = new RegistrationForm();
		req.setAttribute("user", f);
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}

}
