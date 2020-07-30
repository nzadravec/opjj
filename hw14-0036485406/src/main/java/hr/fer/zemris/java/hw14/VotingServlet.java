package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

/**
 * Servlet loads list of {@link PollOption} for given parameter <code>"pollID"</code> and
 * sends them to glasanjeIndex.jsp through request attribute
 * <code>"options"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/glasanje")
public class VotingServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (Exception e) {
			resp.sendError(400, "Bad request");
			return;
		}

		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		req.setAttribute("options", options);
		req.setAttribute("pollID", pollID);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
