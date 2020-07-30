package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import static java.lang.Math.max;
import static java.lang.Math.abs;
import static java.lang.Math.round;

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private BarChart barChart;

	private final int bottomToXLabelSpace = 10;
	private final int xLabelToXValsSpace = 10;
	private final int xValsToXAxisSpace = 10;

	private final int leftToYLabelSpace = 10;
	private final int yLabelToYValsSpace = 10;
	private final int yValsToYAxisSpace = 10;

	private final int rightToXArraySpace = 50;
	private final int topToYArraySpace = 50;

	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Dimension d = getSize();
		Insets ins = new Insets(getInsets().top + topToYArraySpace, getInsets().left + leftToYLabelSpace,
				getInsets().bottom + bottomToXLabelSpace, getInsets().right + rightToXArraySpace);

		FontMetrics fm = g2d.getFontMetrics();
		int fmh = fm.getHeight();

		int xLabelH = fmh;
		int xValsH = fmh;

		int bottomToXValsSpace = ins.bottom + bottomToXLabelSpace + xLabelH + xLabelToXValsSpace;
		int bottomToXAxisSpace = bottomToXValsSpace + xValsH + xValsToXAxisSpace;

		int yLabelH = fmh;
		int yValsH = fmh;

		int ymax = barChart.getYmax();
		int ymin = barChart.getYmin();
		int yInterval = barChart.getYInterval();
		if ((ymax - ymin) % yInterval != 0) {
			ymax += yInterval - (ymax - ymin) % yInterval;
		}
		int numOfYVals = 0;
		while (numOfYVals * yInterval != (ymax - ymin)) {
			numOfYVals++;
		}
		numOfYVals += 1;
		int yValsMaxW = fm.stringWidth(String.valueOf(max(abs(ymax), abs(ymin))));

		int leftToYValsSpace = ins.left + leftToYLabelSpace + yLabelH + yLabelToYValsSpace;
		int leftToYAxisSpace = leftToYValsSpace + yValsMaxW + yValsToYAxisSpace;

		int xAxisLen = d.width - ins.right - leftToYAxisSpace;
		int yAxisLen = d.height - ins.top - bottomToXAxisSpace;

		double yAxisInterval = yAxisLen / (double) (numOfYVals - 1);

		int[] yAxisValsPos = new int[numOfYVals];

		g2d.setColor(Color.lightGray);
		Rectangle r = new Rectangle(leftToYAxisSpace, ins.top, xAxisLen, yAxisLen);
		for (int i = 0; i < numOfYVals; i++) {
			int x1 = r.x;
			int y1 = (int) round(r.y + (numOfYVals - 1 - i) * yAxisInterval);
			int x2 = r.x + r.width;
			int y2 = y1;
			g2d.drawLine(x1, y1, x2, y2);
			yAxisValsPos[i] = y1;
		}

		int numOfXVals = barChart.getXyValueList().size();
		int xAxisInterval = (int) round(r.width / (double) (numOfXVals));
		int[] xAxisValsPos = new int[numOfYVals + 1];

		for (int i = 0; i < numOfXVals + 1; i++) {
			int x1 = r.x + i * xAxisInterval;
			int y1 = r.y;
			int x2 = (int) x1;
			int y2 = r.y + yAxisLen;
			g2d.drawLine(x1, y1, x2, y2);
			xAxisValsPos[i] = x1;
		}

		g2d.setColor(Color.orange);

		for (XYValue xyValue : barChart.getXyValueList()) {
			int xValue = xyValue.getX();
			int xIdx = xValue;
			int yValue = xyValue.getY();
			int yIdx = 0;
			while ((ymin + yIdx * yInterval) < yValue) {
				yIdx++;
			}

			double yOff = 0.0;
			if ((yValue % yInterval) != 0) {
				yOff = (int) round(((yInterval - (yValue % yInterval)) / (double) yInterval) * yAxisInterval);
			}

			int x = xAxisValsPos[xIdx - 1] + 1;
			int y = (int) round(yAxisValsPos[yIdx] + yOff);
			int width = xAxisInterval - 1;
			int height = yAxisValsPos[0] - y;
			g2d.fillRect(x, y, width, height);
		}

		g2d.setColor(Color.black);

		for (int i = 0; i < numOfYVals; i++) {
			String yvalue = String.valueOf(ymax - i * yInterval);
			int yvalueW = fm.stringWidth(yvalue);
			int x = (int) (leftToYValsSpace + (yValsMaxW - yvalueW));
			int y = yAxisValsPos[numOfYVals - 1 - i] + (int) round(yValsH / 2.0);
			g2d.drawString(yvalue, x, y);
		}

		for (int i = 0; i < numOfXVals; i++) {
			String xvalue = String.valueOf(i + 1);
			int xvalueW = fm.stringWidth(xvalue);
			int x = xAxisValsPos[i] + (int) round(xAxisInterval / 2.0 - xvalueW / 2.0);
			int y = d.height - bottomToXValsSpace;
			g2d.drawString(xvalue, x, y);
		}

		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		String ylabel = barChart.getYlabel();
		int ylabelW = fm.stringWidth(ylabel);
		int x = (int) round(-r.y - yAxisLen / 2.0 - ylabelW / 2.0);
		int y = leftToYLabelSpace + yLabelH;
		g2d.drawString(ylabel, x, y);

		g2d.setTransform(defaultAt);

		String xlabel = barChart.getXlabel();
		int xlabelW = fm.stringWidth(xlabel);
		x = (int) round(r.x + xAxisLen / 2.0 - xlabelW / 2.0);
		y = d.height - bottomToXLabelSpace;
		g2d.drawString(xlabel, x, y);
	}

}
