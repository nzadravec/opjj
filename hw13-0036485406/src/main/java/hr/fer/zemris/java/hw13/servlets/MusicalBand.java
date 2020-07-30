package hr.fer.zemris.java.hw13.servlets;

/**
 * Represents single musical band.
 * 
 * @author nikola
 *
 */
public class MusicalBand {
	/**
	 * Band unique ID
	 */
	private int bandID;
	/**
	 * Band name
	 */
	private String bandName;
	/**
	 * Link to bands representative song
	 */
	private String linkToSong;
	/**
	 * Number of votes
	 */
	private int numberOfVotes;

	/**
	 * Constructor.
	 * 
	 * @param bandID band unique ID
	 * @param bandName band name
	 */
	public MusicalBand(int bandID, String bandName) {
		this(bandID, bandName, null, 0);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param bandID band unique ID
	 * @param bandName band name
	 * @param linkToSong link to bands representative song
	 */
	public MusicalBand(int bandID, String bandName, String linkToSong) {
		this(bandID, bandName, linkToSong, 0);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param bandID band unique ID
	 * @param bandName band name
	 * @param linkToSong link to bands representative song
	 * @param numberOfVotes number of votes
	 */
	public MusicalBand(int bandID, String bandName, String linkToSong, int numberOfVotes) {
		super();
		this.bandID = bandID;
		this.bandName = bandName;
		this.linkToSong = linkToSong;
		this.numberOfVotes = numberOfVotes;
	}

	/**
	 * Gets band ID.
	 * 
	 * @return band ID
	 */
	public int getBandID() {
		return bandID;
	}

	/**
	 * Sets band ID.
	 * 
	 * @param bandID band ID
	 */
	public void setBandID(int bandID) {
		this.bandID = bandID;
	}

	/**
	 * Gets band name.
	 * 
	 * @return band name
	 */
	public String getBandName() {
		return bandName;
	}

	/**
	 * Sets band name.
	 * 
	 * @param bandName band name
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	/**
	 * Gets link to representative song.
	 * 
	 * @return link to representative song
	 */
	public String getLinkToSong() {
		return linkToSong;
	}

	/**
	 * Sets link to representative song.
	 * 
	 * @param linkToSong link to representative song
	 */
	public void setLinkToSong(String linkToSong) {
		this.linkToSong = linkToSong;
	}

	/**
	 * Gets number of votes.
	 * 
	 * @return number of votes
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	/**
	 * Sets number of votes.
	 * 
	 * @param numberOfVotes number of votes.
	 */
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
}
