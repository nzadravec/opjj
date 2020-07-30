package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Class stores connections to database in the {@link ThreadLocal} object.
 * 
 * @author nikola
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Set the connection to the current thread (or delete the entry from the folder
	 * if the argument is <code>null</code>).
	 * 
	 * @param con
	 *            connection to database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Get the connection that the current thread (caller) can use.
	 * 
	 * @return connection to database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}