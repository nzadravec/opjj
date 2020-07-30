package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program displays an example of a graph over its entire surface. The program
 * receives one argument: the path to the file where the graph description is.
 * The program opens a file, based by the its content program creates
 * {@link BarChart} object, handing it over to the window. Along the top of the
 * window is placed a label in which it is printed the path to the file from
 * which the data is retrieved.
 * 
 * An example of a file:
 * 
 * Number of people in the car Frequency 1,8 2,20 3,22 4,10 5,4 0 22 2
 * 
 * Each line corresponds to single attribute of {@link BarChart} object. The
 * points are spaced apart by spaces and components by dot-point.
 * 
 * @author nikola
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * File path string of graph description.
	 */
	String filePath;

	/**
	 * BarChartDemo constructor.
	 * 
	 * @param filePath
	 *            file path string of graph description
	 */
	BarChartDemo(String filePath) {
		this.filePath = filePath;

		setLocation(20, 50);
		setSize(500, 400);
		setTitle("BarChartDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel l = new JLabel(filePath, SwingConstants.CENTER);
		cp.add(l, BorderLayout.PAGE_START);

		BarChart model = null;
		try {
			model = getGraphDescriptionfromFile(filePath);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(2);
		}

		cp.add(new BarChartComponent(model), BorderLayout.CENTER);
	}

	/**
	 * Returns {@link BarChart} object described by given file.
	 * 
	 * @param filePath
	 *            string path to file.
	 * @return {@link BarChart} object
	 * @throws FileNotFoundException
	 *             if the filePath argument is null
	 */
	private static BarChart getGraphDescriptionfromFile(String filePath) throws FileNotFoundException {
		try (Scanner sc = new Scanner(new File(filePath))) {
			String xlabel = sc.nextLine();
			String ylabel = sc.nextLine();

			String line = sc.nextLine();
			String[] points = line.split(" ");
			List<XYValue> xyValueList = new ArrayList<>(points.length);
			for (String point : points) {
				String[] xy = point.split(",");
				int x = Integer.parseInt(xy[0]);
				int y = Integer.parseInt(xy[1]);
				xyValueList.add(new XYValue(x, y));
			}

			line = sc.nextLine();
			int ymin = Integer.parseInt(line);

			if (!sc.hasNext()) {
				throw new IllegalArgumentException("File format is not valid.");
			}

			line = sc.nextLine();
			int ymax = Integer.parseInt(line);

			if (!sc.hasNext()) {
				throw new IllegalArgumentException("File format is not valid.");
			}
			line = sc.nextLine();
			int yinterval = Integer.parseInt(line);

			return new BarChart(xyValueList, xlabel, ylabel, ymin, ymax, yinterval);

		} catch (Exception e) {
			throw new IllegalArgumentException("File format is not valid.");
		}
	}

	/**
	 * Main function.
	 * 
	 * @param args
	 *            command line arguments - expects only one argument, file path of
	 *            graph description.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Missing one argument: file path.");
			System.exit(1);
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(args[0]).setVisible(true);
		});
	}

}
