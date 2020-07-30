package hr.fer.zemris.java.hw16.searchengine;

/**
 * Class used to store pair of elements.
 * 
 * @author nikola
 *
 * @param <F> first element
 * @param <S> second element
 */
public class Pair<F, S> {

	/**
	 * First element
	 */
	private F first;
	/**
	 * Second element
	 */
	private S second;
	
	/**
	 * Constructor.
	 * 
	 * @param first first element
	 * @param second second element
	 */
	public Pair(F first, S second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * Gets first element.
	 * 
	 * @return first element
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * Sets first element.
	 * 
	 * @param first first element
	 */
	public void setFirst(F first) {
		this.first = first;
	}

	/**
	 * Gets second element.
	 * 
	 * @return second element
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * Sets second element.
	 * 
	 * @param second second element
	 */
	public void setSecond(S second) {
		this.second = second;
	}
	
}
