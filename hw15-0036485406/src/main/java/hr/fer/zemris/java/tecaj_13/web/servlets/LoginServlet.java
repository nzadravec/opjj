package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.LoginForm;

/**
 * Logs clients. If parameters are invalid, servlet returns
 * <code>"/WEB-INF/pages/Login.jsp"</code> page filled with {@link LoginForm}
 * attributes. If parameters are valid, servlet adds information (id, first
 * name, last name, nickname) of logged user into current session.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

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

		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("users", users);

		HttpSession session = req.getSession();
		if (session.getAttribute("current.user.id") == null) {
			LoginForm f = new LoginForm();
			f.fillFromHTTPRequest(req);
			f.validate();

			if (f.hasErrors()) {
				req.setAttribute("user", f);
				req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
				return;
			}

			BlogUser user = DAOProvider.getDAO().getBlogUser(f.getNick());

			session.setAttribute("current.user.id", user.getId());
			session.setAttribute("current.user.fn", user.getFirstName());
			session.setAttribute("current.user.ln", user.getLastName());
			session.setAttribute("current.user.nick", user.getNick());
			session.setAttribute("current.user.email", user.getEmail());
		}

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
