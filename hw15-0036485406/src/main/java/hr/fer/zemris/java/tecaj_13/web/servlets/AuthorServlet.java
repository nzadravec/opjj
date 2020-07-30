package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.BlogForm;
import hr.fer.zemris.java.tecaj_13.web.forms.CommentForm;

/**
 * If URL is of type <code>"/servleti/author/NICK"</code> and if there is user
 * in database with nickname equal to NICK, servlet returns page
 * <code>"/WEB-INF/pages/ShowBlogs.jsp"</code>. If URL is of type
 * <code>"/servleti/author/NICK/EID"</code> and if there is user with nickname
 * equal to NICK and this user has blog with id equal to EID, servlet returns
 * <code>"/WEB-INF/pages/ShowBlog.jsp"</code> page. If URL is of type
 * <code>"/servleti/author/NICK/new"</code> and user with nickname NICK is
 * currently logged, servlet returns <code>"/WEB-INF/pages/NewBlog.jsp"</code>
 * page. If URL is of type <code>"/servleti/author/NICK/edit?EID=id"</code>,
 * user with nickname NICK is currently logged and has blog with identificator
 * equal id, servlet returns <code>"/WEB-INF/pages/EditBlog.jsp"</code> page.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if (pathInfo == null) {
			resp.sendError(400, "Bad request");
			return;
		}

		int numOfBSlashes = 0;
		for (int i = 1; i < pathInfo.length(); i++) {
			if (pathInfo.charAt(i) == '/') {
				numOfBSlashes++;
			}

			if (numOfBSlashes > 1) {
				resp.sendError(400, "Bad request");
				return;
			}
		}

		int sndBSlashIndex = pathInfo.indexOf("/", 1);
		String nick;
		if (sndBSlashIndex == -1) {
			nick = pathInfo.substring(1);
		} else {
			nick = pathInfo.substring(1, sndBSlashIndex);
		}

		if (nick.isEmpty()) {
			resp.sendError(400, "Bad request");
			return;
		}

		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user == null) {
			resp.sendError(400, "Bad request");
			return;
		}

		if (nick.equals(req.getSession().getAttribute("current.user.nick"))) {
			req.setAttribute("providedNick", new Object());
		}

		if (sndBSlashIndex == -1) {
			showBlogs(req, resp, user);
			return;
		}

		String action = pathInfo.substring(sndBSlashIndex + 1);

		try {
			String EID = action;
			long id = Long.parseLong(EID);
			BlogEntry blog = DAOProvider.getDAO().getBlogEntry(id);
			if (blog == null || !blog.getCreator().getId().equals(user.getId())) {
				resp.sendError(400, "Bad request");
				return;
			}
			req.setAttribute("blog", blog);
			req.setAttribute("comment", new CommentForm());
			req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req, resp);
			return;
		} catch (Exception ignorable) {
		}

		if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
			resp.sendError(400, "Bad request");
			return;
		}

		if (action.equals("new")) {
			processBlog(req, resp, new BlogForm(), "/WEB-INF/pages/NewBlog.jsp");
			return;
		}

		if (action.startsWith("edit")) {
			String EID = req.getParameter("EID");
			long id;
			try {
				id = Long.parseLong(EID);
				BlogEntry blog = DAOProvider.getDAO().getBlogEntry(id);
				if (blog == null || !blog.getCreator().getId().equals(user.getId())) {
					resp.sendError(400, "Bad request");
					return;
				}
				BlogForm f = new BlogForm();
				f.fillFromBlogEntry(blog);
				processBlog(req, resp, f, "/WEB-INF/pages/EditBlog.jsp");
				return;
			} catch (Exception ignorable) {
			}
		}

		resp.sendError(400, "Bad request");
		return;
	}

	private void showBlogs(HttpServletRequest req, HttpServletResponse resp, BlogUser user)
			throws ServletException, IOException {
		req.setAttribute("user.nick", user.getNick());
		List<BlogEntry> blogs = DAOProvider.getDAO().getUserBlogs(user);
		req.setAttribute("blogs", blogs);

		req.getRequestDispatcher("/WEB-INF/pages/ShowBlogs.jsp").forward(req, resp);
	}

	private void processBlog(HttpServletRequest req, HttpServletResponse resp, BlogForm blog, String path)
			throws ServletException, IOException {
		req.setAttribute("blog", blog);

		req.getRequestDispatcher(path).forward(req, resp);
	}

}
