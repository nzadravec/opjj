package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class who knows who to return as a service provider to access the data
 * subsystem. Note that although the decision here is hardcoded, the class name
 * that was created could be dynamically read from the configuration file and
 * dynamically loaded - so we could change implementations without any code
 * compilation.
 * 
 * @author nikola
 *
 */
public class DAOProvider {

	/**
	 * Service provider
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Gets service provider.
	 * 
	 * @return object that encapsulates access to a data persistence layer.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}