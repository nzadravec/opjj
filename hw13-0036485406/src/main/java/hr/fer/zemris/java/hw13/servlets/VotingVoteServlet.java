package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet notes vote given through URL in file glasanje-rezultati.txt.
 * 
 * @author nikola
 *
 */
@WebServlet("/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		VotingResult[] votingResults;
		Map<Integer, VotingResult> votingResultsMap = new HashMap<>();
		List<String> lines = Files.readAllLines(filePath);
		if(lines.isEmpty()) {
			String fileName2 = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
			lines = Files.readAllLines(Paths.get(fileName2));
			votingResults = new VotingResult[lines.size()];

			BufferedWriter writer = Files.newBufferedWriter(filePath);
			for(int i = 0; i < lines.size(); i++) {
				int bandID = Integer.parseInt(lines.get(i).split("\t")[0]);
				int numberOfVotes = 0;
				
				votingResults[i] = new VotingResult(bandID, numberOfVotes);
				votingResultsMap.put(bandID, votingResults[i]);
				
				writer.write(bandID + "\t" + numberOfVotes);
				writer.newLine();
			}
			writer.close();
			
		} else {
			votingResults = new VotingResult[lines.size()];
			for(int i = 0; i < lines.size(); i++) {
				String[] resultAttributes = lines.get(i).split("\t");
				int bandID = Integer.parseInt(resultAttributes[0]);
				int numberOfVotes = Integer.parseInt(resultAttributes[1]);
				
				votingResults[i] = new VotingResult(bandID, numberOfVotes);
				votingResultsMap.put(bandID, votingResults[i]);
			}
		}
		
		String idString = req.getParameter("id");
		if(idString == null) {
			String errorMessage = "Missing parameter id";
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		
		int bandID;
		try {
			bandID = Integer.parseInt(idString);
		} catch (Exception e) {
			String errorMessage = "Parameter id must be integer";
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		
		VotingResult votingResult = votingResultsMap.get(bandID);
		if(votingResult == null) {
			String errorMessage = bandID + " is not valid band ID";
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		
		votingResult.numberOfVotes++;
		
		BufferedWriter writer = Files.newBufferedWriter(filePath);
		for(VotingResult result: votingResults) {
			writer.write(result.getBandID() + "\t" + result.getNumberOfVotes());
			writer.newLine();
		}
		writer.close();
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
	/**
	 * Represents number of votes of certain band.
	 * 
	 * @author nikola
	 *
	 */
	public static class VotingResult {
		/**
		 * Bands unique ID
		 */
		private int bandID;
		/**
		 * Number of votes
		 */
		private int numberOfVotes;
		
		/**
		 * Constructor 
		 * 
		 * @param bandID bands unique ID
		 * @param numberOfVotes number of votes
		 */
		public VotingResult(int bandID, int numberOfVotes) {
			super();
			this.bandID = bandID;
			this.numberOfVotes = numberOfVotes;
		}

		/**
		 * Gets band ID
		 * 
		 * @return band ID
		 */
		public int getBandID() {
			return bandID;
		}

		/**
		 * Sets band ID
		 * 
		 * @param bandID band ID
		 */
		public void setBandID(int bandID) {
			this.bandID = bandID;
		}

		/**
		 * Gets number of votes
		 * 
		 * @return number of votes
		 */
		public int getNumberOfVotes() {
			return numberOfVotes;
		}

		/**
		 * Sets number of votes
		 * 
		 * @param numberOfVotes number of votes
		 */
		public void setNumberOfVotes(int numberOfVotes) {
			this.numberOfVotes = numberOfVotes;
		}
	}

}
