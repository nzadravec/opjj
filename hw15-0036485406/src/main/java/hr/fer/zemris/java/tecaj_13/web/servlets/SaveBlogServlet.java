package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.BlogForm;

/**
 * Saves new or edited blog. If parameters are invalid, servlet returns
 * <code>"/WEB-INF/pages/Blog.jsp"</code> page filled with {@link BlogForm}
 * attributes. Current session has to contain id of logged in user; if not,
 * servlet aborts operation since it doesn't know who is adding new blog or who
 * wants to save edited blog.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/saveBlog")
public class SaveBlogServlet extends HttpServlet {

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
		String method = req.getParameter("method");
		if (!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		if (req.getSession().getAttribute("current.user.id") == null) {
			resp.sendError(400, "Bad request");
			return;
		}

		BlogForm f = new BlogForm();
		f.fillFromHTTPRequest(req);
		f.validate();

		if (f.hasErrors()) {
			req.setAttribute("blog", f);
			req.getRequestDispatcher("/WEB-INF/pages/Blog.jsp").forward(req, resp);
			return;
		}

		String blogId = req.getParameter("id");
		if (blogId == null || blogId.isEmpty()) {
			BlogEntry blog = new BlogEntry();
			f.fillBlogEntry(blog);
			blog.setCreatedAt(new Date());
			BlogUser user = DAOProvider.getDAO()
					.getBlogUser(req.getSession().getAttribute("current.user.nick").toString());
			blog.setCreator(user);
			DAOProvider.getDAO().addBlogEntry(blog);
		} else {
			long id;
			try {
				id = Long.parseLong(blogId);
			} catch (Exception e) {
				resp.sendError(400, "Bad request");
				return;
			}
			BlogEntry blog = DAOProvider.getDAO().getBlogEntry(id);
			if (blog == null) {
				resp.sendError(400, "Bad request");
				return;
			}
			f.fillBlogEntry(blog);
			blog.setLastModifiedAt(new Date());
		}

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
				+ req.getSession().getAttribute("current.user.nick"));
	}

}
