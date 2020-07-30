package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface to Data Subsystem.
 * 
 * @author nikola
 *
 */
public interface DAO {

	/**
	 * Gets {@link BlogEntry} for given id. If {@link BlogEntry} with given id
	 * doesn't exist, returns <code>null</code>.
	 * 
	 * @param id
	 *            id
	 * @return {@link BlogEntry} for given id
	 * @throws DAOException
	 *             if error occurs
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Adds given {@link BlogEntry} object to database.
	 * 
	 * @param blog
	 *            {@link BlogEntry} object
	 * @throws DAOException
	 *             if error occurs
	 */
	public void addBlogEntry(BlogEntry blog) throws DAOException;

	/**
	 * Gets {@link BlogUser} for given nickname. If {@link BlogEntry} with given
	 * nickname doesn't exist, returns <code>null</code>.
	 * 
	 * @param nick
	 *            nickname
	 * @return {@link BlogUser} for given nickname
	 * @throws DAOException
	 *             if error occurs
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Add given {@link BlogUser} object to database.
	 * 
	 * @param user
	 *            {@link BlogUser} object
	 * @throws DAOException
	 *             if error occurs
	 */
	public void addBlogUser(BlogUser user) throws DAOException;

	/**
	 * Gets list of existing {@link BlogUser} from database.
	 * 
	 * @return {@link BlogUser} list
	 * @throws DAOException
	 *             if error occurs
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Gets list of existing {@link BlogEntry} for given user from database.
	 * 
	 * @param user
	 *            user
	 * @return {@link BlogEntry} list
	 * @throws DAOException
	 *             if error occurs
	 */
	public List<BlogEntry> getUserBlogs(BlogUser user) throws DAOException;

}