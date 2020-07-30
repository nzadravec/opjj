package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents model of bar chart.
 * 
 * @author nikola
 *
 */
public class BarChart {

	/**
	 * List of 2D data
	 */
	private List<XYValue> xyValueList;
	/**
	 * Label of x axis
	 */
	private String xlabel;
	/**
	 * Label of y axis
	 */
	private String ylabel;
	/**
	 * Minimum y which is displayed on the axis
	 */
	private int ymin;
	/**
	 * Maximum y which is displayed on the axis
	 */
	private int ymax;
	/**
	 * Distance between two adjacent ys that are displayed on the axis
	 */
	private int yInterval;

	/**
	 * Creats bar chart.
	 * 
	 * @param xyValueList
	 *            list of 2D data
	 * @param xlabel
	 *            label of x axis
	 * @param ylabel
	 *            label of y axis
	 * @param ymin
	 *            minimum y which is displayed on the axis
	 * @param ymax
	 *            maximum y which is displayed on the axis
	 * @param yInterval
	 *            distance between two adjacent ys that are displayed on the axis
	 */
	public BarChart(List<XYValue> xyValueList, String xlabel, String ylabel, int ymin, int ymax, int yInterval) {
		super();
		this.xyValueList = xyValueList;
		this.xlabel = xlabel;
		this.ylabel = ylabel;
		this.ymin = ymin;
		this.ymax = ymax;
		this.yInterval = yInterval;
	}

	/**
	 * Returns list of 2D data.
	 * 
	 * @return list of 2D data
	 */
	public List<XYValue> getXyValueList() {
		return xyValueList;
	}

	/**
	 * Returns label of x axis.
	 * 
	 * @return label of x axis
	 */
	public String getXlabel() {
		return xlabel;
	}

	/**
	 * Returns ylabel label of y axis.
	 * 
	 * @return ylabel label of y axis
	 */
	public String getYlabel() {
		return ylabel;
	}

	/**
	 * Returns minimum y which is displayed on the axis.
	 * 
	 * @return minimum y which is displayed on the axis
	 */
	public int getYmin() {
		return ymin;
	}

	/**
	 * Returns maximum y which is displayed on the axis.
	 * 
	 * @return maximum y which is displayed on the axis
	 */
	public int getYmax() {
		return ymax;
	}

	/**
	 * Returns distance between two adjacent ys that are displayed on the axis.
	 * 
	 * @return distance between two adjacent ys that are displayed on the axis
	 */
	public int getYInterval() {
		return yInterval;
	}

}
