package hr.fer.zemris.java.gui.layouts;

/**
 * Class represents position in grid. It's used as a constraint in
 * {@link CalcLayout}.
 * 
 * @author nikola
 *
 */
public class RCPosition {
	/**
	 * Positions row in grid
	 */
	private int row;
	/**
	 * Positions column in grid
	 */
	private int column;

	/**
	 * Creats arbitrary position in grid.
	 * 
	 * @param row row in grid
	 * @param column column in grid
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns positions row.
	 * 
	 * @return positions row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns positions column.
	 * 
	 * @return positions column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
