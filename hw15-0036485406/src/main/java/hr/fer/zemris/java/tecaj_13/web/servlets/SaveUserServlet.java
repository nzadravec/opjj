package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.RegistrationForm;

/**
 * Saves new user. If parameters for creating new user are invalid, servlet
 * returns <code>"/WEB-INF/pages/Register.jsp"</code> page filled with
 * {@link RegistrationForm} attributes. If parameters are valid, servlet creates
 * new user and adds his info (id, first name, last name, nickname) into current
 * session.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/saveUser")
public class SaveUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");
		if (!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		RegistrationForm f = new RegistrationForm();
		f.fillFromHTTPRequest(req);
		f.validate();

		if (f.hasErrors()) {
			req.setAttribute("user", f);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}

		BlogUser user = new BlogUser();
		f.fillBlogUser(user);
		DAOProvider.getDAO().addBlogUser(user);

		HttpSession session = req.getSession();
		session.setAttribute("current.user.id", user.getId());
		session.setAttribute("current.user.fn", user.getFirstName());
		session.setAttribute("current.user.ln", user.getLastName());
		session.setAttribute("current.user.nick", user.getNick());

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
