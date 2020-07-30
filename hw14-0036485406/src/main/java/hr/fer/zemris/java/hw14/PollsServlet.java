package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Servlet obtains a list of defined polls and renders it to user as a list of
 * clickable links by calling <code>Polls.jsp</code>. When user clicks on a Poll
 * title, the link that will be followed is
 * <code>/servleti/glasanje?pollID=x</code> where <code>x</code> is selected poll ID.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/Polls.jsp").forward(req, resp);
	}

}
