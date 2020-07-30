package hr.fer.zemris.java.hw14;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

/**
 * Servlet creates image showing {@link PiePlot3D} of given parameter
 * <code>pollID</code> voting results.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class VotingGraphicsServlet extends HttpServlet {

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

		PieDataset dataset = createDataset(options);
		final String chartTitle = "Rezulati";
		JFreeChart chart = createChart(dataset, chartTitle);

		final int width = 500;
		final int height = 270;

		BufferedImage image = chart.createBufferedImage(width, height);
		byte[] bs = ChartUtils.encodeAsPNG(image);

		resp.setContentType("image/png");
		resp.getOutputStream().write(bs);
	}

	/**
	 * Creates dataset of band names and number of votes of each band.
	 */
	private PieDataset createDataset(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (PollOption option : options) {
			result.setValue(option.getOptionTitle(), option.getVotesCount());
		}

		return result;
	}

	/**
	 * Creates a chart.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}

}
