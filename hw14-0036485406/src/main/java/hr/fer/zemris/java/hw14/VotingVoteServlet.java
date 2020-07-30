package hr.fer.zemris.java.hw14;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

/**
 * Servlet increases votes of {@link PollOption} with <code>id</code> equal to
 * given parameter sent through URL by key <code>"id"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long pollID;
		long id;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
			id = Long.parseLong(req.getParameter("id"));
		} catch (Exception e) {
			resp.sendError(400, "Bad request");
			return;
		}

		DAOProvider.getDao().increaseVotesCountForPollOption(id);
		req.setAttribute("pollID", pollID);

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
