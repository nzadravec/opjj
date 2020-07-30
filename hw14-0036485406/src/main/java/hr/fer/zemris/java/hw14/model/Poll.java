package hr.fer.zemris.java.hw14.model;

import java.util.List;

/**
 * Class represents poll with unique id, title, message and list of
 * {@link PollOption} options to vote for.
 * 
 * @author nikola
 *
 */
public class Poll {

	/**
	 * Unique id
	 */
	private long id;
	/**
	 * Polls title
	 */
	private String title;
	/**
	 * Polls message
	 */
	private String message;
	/**
	 * List of options to vote for
	 */
	private List<PollOption> options;

	/**
	 * Gets id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id to given value.
	 * 
	 * @param id given value
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets polls title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets polls title to given string.
	 * 
	 * @param title given string
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets polls message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets polls message to given string.
	 * 
	 * @param message given string
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets list of options to vote for.
	 * 
	 * @return options options to vote for
	 */
	public List<PollOption> getOptions() {
		return options;
	}

	/**
	 * Sets list of options to given list.
	 * 
	 * @param options given list
	 */
	public void setOptions(List<PollOption> options) {
		this.options = options;
	}

	/**
	 * Class represents one poll option to vote for.
	 * 
	 * @author nikola
	 *
	 */
	public static class PollOption {

		/**
		 * Option id
		 */
		private long id;
		/**
		 * Option title
		 */
		private String optionTitle;
		/**
		 * Option link
		 */
		private String optionLink;
		/**
		 * Number of votes
		 */
		private long votesCount;

		/**
		 * Gets id.
		 * 
		 * @return id
		 */
		public long getId() {
			return id;
		}

		/**
		 * Sets id to given value.
		 * 
		 * @param id given value
		 */
		public void setId(long id) {
			this.id = id;
		}

		/**
		 * Gets option title.
		 * 
		 * @return option title
		 */
		public String getOptionTitle() {
			return optionTitle;
		}

		/**
		 * Sets option title to given string.
		 * 
		 * @param optionTitle given string
		 */
		public void setOptionTitle(String optionTitle) {
			this.optionTitle = optionTitle;
		}

		/**
		 * Gets option link.
		 * 
		 * @return option link
		 */
		public String getOptionLink() {
			return optionLink;
		}

		/**
		 * Sets option link to given string.
		 * 
		 * @param optionLink given string
		 */
		public void setOptionLink(String optionLink) {
			this.optionLink = optionLink;
		}

		/**
		 * Gets number of votes.
		 * 
		 * @return number of votes
		 */
		public long getVotesCount() {
			return votesCount;
		}

		/**
		 * Sets number of votes.
		 * 
		 * @param votesCount number of votes
		 */
		public void setVotesCount(long votesCount) {
			this.votesCount = votesCount;
		}

	}

}