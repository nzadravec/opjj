package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Class providers {@link EntityManagerFactory} via static method
 * {{@link #getEmf()}.
 * 
 * @author nikola
 *
 */
public class JPAEMFProvider {

	/**
	 * {@link EntityManager} producer
	 */
	public static EntityManagerFactory emf;

	/**
	 * Gets {@link EntityManagerFactory#}.
	 * 
	 * @return {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets {@link EntityManagerFactory}.
	 * 
	 * @param emf {@link EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}