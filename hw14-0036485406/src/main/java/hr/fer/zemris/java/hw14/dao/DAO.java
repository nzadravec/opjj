package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

import java.util.List;

/**
 * Interface to Data Subsystem.
 * 
 * @author nikola
 *
 */
public interface DAO {

	/**
	 * Gets list of existing {@link Poll} from database, but fills only two attributes: id and
	 * title.
	 * 
	 * @return {@link Poll} list
	 * @throws DAOException
	 *             if error occurs
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Gets {@link Poll} for given id. If {@link Poll} doesn't exist, returns <code>null</code>.
	 * 
	 * @param id
	 * @return {@link Poll} for given id
	 * @throws DAOException if error occurs
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Gets list of all {@link PollOption} for given pollID.
	 * 
	 * @return {@link PollOption} list for given pollID
	 * @throws DAOException if error occurs
	 */
	public List<PollOption> getPollOptions(long pollID) throws DAOException;

	/**
	 * Increases votesCount by one of {@link PollOption} with given id.
	 * 
	 * @param id
	 * @throws DAOException if error occurs
	 */
	public void increaseVotesCountForPollOption(long id) throws DAOException;

}