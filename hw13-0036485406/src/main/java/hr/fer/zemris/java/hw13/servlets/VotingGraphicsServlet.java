package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

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

/**
 * Servlet creates image showing Pie Chart of bands voting results.
 * 
 * @author nikola
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns={"/glasanje-grafika"})
public class VotingGraphicsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MusicalBand[] bands = VotingUtil.getMusicalBands(getServletContext());
		
		PieDataset dataset = createDataset(bands);
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
    private  PieDataset createDataset(MusicalBand[] bands) {
        DefaultPieDataset result = new DefaultPieDataset();
        for(MusicalBand band : bands) {
        	result.setValue(band.getBandName(), band.getNumberOfVotes());
        }
        
        return result;
    }

	/**
     * Creates a chart.
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
    
}
