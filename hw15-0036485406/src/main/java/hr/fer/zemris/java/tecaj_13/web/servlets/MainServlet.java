package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.LoginForm;

/**
 * Dispatches client to <code>"/WEB-INF/pages/Main.jsp"</code> page if
 * there's not information about client in current session map (ie. client is
 * not logged in); otherwise, it dispatches client to
 * <code>"/WEB-INF/pages/Login.jsp"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("users", users);
		
		if(req.getSession().getAttribute("current.user.id") == null) {
			LoginForm f = new LoginForm();
			req.setAttribute("user", f);
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
		}
	}
	
}
