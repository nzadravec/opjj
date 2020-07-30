package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.ConnectionSetterFilter;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the {@link DAO} subsystem using SQL technology.
 * This concrete implementation expects that {@link Connection} is available via
 * the {@link SQLConnectionProvider} class, which means someone else would have
 * to set up there before the performance came to this point. In web
 * applications, the typical solution is to configure one filter that intercepts
 * the servlet calls and inserts here a connection from the connection-pool and
 * after the processing finishes it removes the connection.
 * {@link ConnectionSetterFilter} does this.
 * 
 * @author nikola
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from POLLS");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata ankete.", ex);
		}
		return polls;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, votesCount from POLLOPTIONS where pollID="
					+ pollID + " order by votesCount DESC");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setVotesCount(rs.getLong(4));
						options.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste opcija ankete.", ex);
		}
		return options;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from POLLS where id=" + id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste opcija ankete.", ex);
		}

		return poll;
	}

	@Override
	public void increaseVotesCountForPollOption(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement("select votesCount from POLLOPTIONS where id=" + id);
			rs = pst.executeQuery();
			long votesCount = 0;
			if (rs != null && rs.next()) {
				votesCount = rs.getLong(1);
			}
			pst.close();
			rs.close();

			votesCount++;

			pst = con.prepareStatement("UPDATE POLLOPTIONS set votesCount=" + votesCount + " where id=" + id);
			pst.executeUpdate();
			pst.close();

		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste opcija ankete.", ex);
		}
	}

}