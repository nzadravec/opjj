package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.web.forms.CommentForm;

/**
 * Saves comments. Comment is saved only if its not empty.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/saveComment")
public class SaveCommentServlet extends HttpServlet {

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
		String blogId = req.getParameter("id");
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
		
		req.setAttribute("blog", blog);
		
		String nick = blog.getCreator().getNick();
		if (nick.equals(req.getSession().getAttribute("current.user.nick"))) {
			req.setAttribute("providedNick", new Object());
		}
		
		CommentForm f = new CommentForm();
		f.fillFromHTTPRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req, resp);
			return;
		}

		BlogComment comment = new BlogComment();
		comment.setBlogEntry(blog);
		comment.setMessage(f.getMessage());
		comment.setPostedOn(new Date());

		String email;
		if (req.getSession().getAttribute("current.user.id") != null) {
			email = req.getSession().getAttribute("current.user.email").toString();
		} else {
			email = "unknown";
		}
		comment.setUsersEMail(email);
		
		blog.getComments().add(comment);
		
		req.setAttribute("comment", new CommentForm());
		req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req, resp);
	}

}
