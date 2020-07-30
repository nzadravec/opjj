package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This is the implementation of the {@link DAO} subsystem using JPA technology.
 * This concrete implementation expects that {@link EntityManager} is available
 * via the {@link JPAEMProvider} class, which means someone else would have to
 * set up there before the performance came to this point. In web applications,
 * the typical solution is to configure one filter that intercepts the servlet
 * calls and inserts here a connection from the connection-pool and after the
 * processing finishes it removes the connection. {@link JPAFilter} does this.
 * 
 * @author nikola
 */
public class JPADAOImpl implements DAO {

	/**
	 * Gets first element of result list obtained from performing the given query.
	 * If list is <code>null</code> or empty it returns <code>null</code>.
	 * 
	 * @param query query 
	 * @return first element of result list if exists; <code>code</code> otherwise
	 */
	public <T> T getSingleResult(TypedQuery<T> query) {
		query.setMaxResults(1);
		List<T> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		BlogUser blogUser = getSingleResult(JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.getByNick", BlogUser.class).setParameter("nick", nick));
		return blogUser;
	}

	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		List<BlogUser> users = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.getAllUsers", BlogUser.class)
				.getResultList();
		return users;
	}

	@Override
	public List<BlogEntry> getUserBlogs(BlogUser user) throws DAOException {
		List<BlogEntry> blogs = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogEntry.getUserBlogs", BlogEntry.class).setParameter("user", user).getResultList();
		return blogs;
	}

	@Override
	public void addBlogEntry(BlogEntry blog) throws DAOException {
		JPAEMProvider.getEntityManager().persist(blog);
	}

}