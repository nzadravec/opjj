package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class represents layout manager. To allocate components manager conceptualy
 * works with regular grid of 5 rows and 7 columns (this is fixed and can not be
 * changed). Numbering of rows and columns ranges from 1.
 * 
 * All grid rows are equally high; all grid columns are equally wide. Up to 31
 * components are supported. The component added to position (1, 1) is always
 * arranged so as to cover the positions (1, 2) to (1, 5) which can not
 * therefore be used.
 * 
 * @author nikola
 *
 */
public class CalcLayout implements LayoutManager2 {

	private int rows = 5;
	private int cols = 7;
	private int gap;

	private Map<RCPosition, Component> constraintsMap = new HashMap<>();

	public CalcLayout(int gap) {
		super();
		this.gap = gap;
	}

	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int totalGapsWidth = (cols - 1) * gap;
		int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
		int widthOnComponent = (widthWOInsets - totalGapsWidth) / cols;
		int extraWidthAvailable = (widthWOInsets - (widthOnComponent * cols + totalGapsWidth)) / 2;

		int totalGapsHeight = (rows - 1) * gap;
		int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
		int heightOnComponent = (heightWOInsets - totalGapsHeight) / rows;
		int extraHeightAvailable = (heightWOInsets - (heightOnComponent * rows + totalGapsHeight)) / 2;

		int left = insets.left + extraWidthAvailable;
		int top = insets.top + extraHeightAvailable;
		for (Entry<RCPosition, Component> e : constraintsMap.entrySet()) {
			RCPosition rcPos = e.getKey();
			Component comp = e.getValue();
			int r = rcPos.getRow();
			int c = rcPos.getColumn();
			int x = left + (c - 1) * widthOnComponent + (c - 1) * gap;
			int y = top + (r - 1) * heightOnComponent + (r - 1) * gap;
			if (r == 1 && c == 1) {
				comp.setBounds(x, y, 5 * widthOnComponent + 4 * gap, heightOnComponent);
			} else {
				comp.setBounds(x, y, widthOnComponent, heightOnComponent);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int w = 0;
		int h = 0;
		for (Entry<RCPosition, Component> e : constraintsMap.entrySet()) {
			RCPosition rcPos = e.getKey();
			Component comp = e.getValue();

			Dimension d = comp.getMinimumSize();
			if (d == null) {
				continue;
			}

			if (rcPos.getRow() == 1 && rcPos.getColumn() == 1) {
				w = Math.max((d.width - 4 * gap) / 5, w);
			} else {
				w = Math.max(d.width, w);
			}

			h = Math.max(d.height, h);
		}

		Dimension dim = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		dim.width = 7 * w + 6 * gap + insets.left + insets.right;
		dim.height = 5 * h + 4 * gap + insets.top + insets.bottom;

		return dim;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int w = 0;
		int h = 0;
		for (Entry<RCPosition, Component> e : constraintsMap.entrySet()) {
			RCPosition rcPos = e.getKey();
			Component comp = e.getValue();
			Dimension d = comp.getPreferredSize();
			if (rcPos.getRow() == 1 && rcPos.getColumn() == 1) {
				w = Math.max((d.width - 4 * gap) / 5, w);
			} else {
				w = Math.max(d.width, w);
			}

			h = Math.max(d.height, h);
		}

		Dimension dim = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		dim.width = cols * w + (cols - 1) * gap + insets.left + insets.right;
		dim.height = rows * h + (rows - 1) * gap + insets.top + insets.bottom;
		return dim;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);

		RCPosition rcPos;
		if (constraints.getClass() == RCPosition.class) {
			rcPos = (RCPosition) constraints;
		} else if (constraints.getClass() == String.class) {
			String s = (String) constraints;
			int row = Integer.parseInt(s.split(",")[0]);
			int column = Integer.parseInt(s.split(",")[1]);
			rcPos = new RCPosition(row, column);
		} else {
			throw new CalcLayoutException(
					"Constraints must be object of RCPosition class or String of format \"row,column\".");
		}

		if (rcPos.getRow() < 1 || rcPos.getRow() > 5 || rcPos.getColumn() < 1 || rcPos.getColumn() > 7) {
			throw new CalcLayoutException();
		}

		if (rcPos.getRow() == 1 && (rcPos.getColumn() > 1 && rcPos.getColumn() < 6)) {
			throw new CalcLayoutException();
		}

		if (constraintsMap.get(rcPos) == comp) {
			throw new CalcLayoutException();
		}

		constraintsMap.put(rcPos, comp);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int w = 0;
		int h = 0;
		for (Entry<RCPosition, Component> e : constraintsMap.entrySet()) {
			RCPosition rcPos = e.getKey();
			Component comp = e.getValue();

			Dimension d = comp.getMinimumSize();
			if (d == null) {
				continue;
			}

			if (rcPos.getRow() == 1 && rcPos.getColumn() == 1) {
				w = Math.min((d.width - 4 * gap) / 5, w);
			} else {
				w = Math.min(d.width, w);
			}

			h = Math.min(d.height, h);
		}

		Dimension dim = new Dimension(0, 0);
		Insets insets = target.getInsets();
		dim.width = 7 * w + 6 * gap + insets.left + insets.right;
		dim.height = 5 * h + 4 * gap + insets.top + insets.bottom;

		return dim;
	}

}
