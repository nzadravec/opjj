package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet loads list of {@link MusicalBand} objects for all available bands
 * and creates list of bands with the most votes; sends both lists to glasanjeRez.jsp
 * through request attributes.
 * 
 * @author nikola
 *
 */
@WebServlet("/glasanje-rezultati")
public class VotingResultsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MusicalBand[] bands = VotingUtil.getMusicalBands(getServletContext());

		req.setAttribute("bands", bands);

		List<MusicalBand> listOfWinningBands = new ArrayList<>();
		if (bands.length > 0) {
			int maxVotes = bands[0].getNumberOfVotes();
			listOfWinningBands.add(bands[0]);
			for (int i = 1; i < bands.length; i++) {
				if (bands[i].getNumberOfVotes() == maxVotes) {
					listOfWinningBands.add(bands[i]);
				} else {
					break;
				}
			}
		}

		MusicalBand[] winningBands = listOfWinningBands.toArray(new MusicalBand[listOfWinningBands.size()]);
		req.setAttribute("winningBands", winningBands);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
