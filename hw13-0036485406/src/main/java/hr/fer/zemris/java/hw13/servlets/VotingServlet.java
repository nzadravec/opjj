package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet loads available bands and sends them to glasanjeIndex.jsp through
 * request attribute <code>"musicalBands"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/glasanje")
public class VotingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));

		MusicalBand[] musicalBands = new MusicalBand[lines.size()];
		for (int i = 0; i < musicalBands.length; i++) {
			String[] bandAttributes = lines.get(i).split("\t");
			int ID = Integer.parseInt(bandAttributes[0]);
			String name = bandAttributes[1];
			musicalBands[i] = new MusicalBand(ID, name);
		}

		req.setAttribute("musicalBands", musicalBands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
