package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

/**
 * Servlet loads list of {@link PollOption} objects for given parameter <code>pollID</code>
 * and creates list of options with the most votes; sends both lists to glasanjeRez.jsp
 * through request attributes.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class VotingResultsServlet extends HttpServlet {
	
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
		
		List<PollOption> bestOptions = new ArrayList<>();
		bestOptions.add(options.get(0));
		for(int i = 1; i < options.size(); i++) {
			PollOption option = options.get(i);
			if(option.getVotesCount() == options.get(0).getVotesCount()) {
				bestOptions.add(option);
			} else {
				break;
			}
		}
		req.setAttribute("bestOptions", bestOptions);
		req.setAttribute("pollID", pollID);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
