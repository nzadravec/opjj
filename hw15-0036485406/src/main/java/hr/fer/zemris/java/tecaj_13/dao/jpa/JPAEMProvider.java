package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class stores connections to database in the {@link ThreadLocal} object.
 * 
 * @author nikola
 *
 */
public class JPAEMProvider {

	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Get the connection that the current thread (caller) can use. If theres no
	 * connection for current thread, method creates one.
	 * 
	 * @return connection to database
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes the connection for the current thread (caller).
	 * 
	 * @throws DAOException
	 *             if error occurs
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}