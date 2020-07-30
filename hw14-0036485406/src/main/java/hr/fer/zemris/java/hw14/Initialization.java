package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class builds a connection-pool and sets it in the attribute folder when
 * launching a web application, and drops and unmounts the pool when the
 * application is shut down.
 * 
 * @author nikola
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String configPath = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Properties configuration = new Properties();
		try {
			InputStream is = Files.newInputStream(Paths.get(configPath));
			configuration.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String host = configuration.getProperty("host");
		String port = configuration.getProperty("port");
		String name = configuration.getProperty("name");
		String user = configuration.getProperty("user");
		String password = configuration.getProperty("password");
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user + ";password="
				+ password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		Connection con;
		try {
			con = cpds.getConnection();
			prepareDatabase(con);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void prepareDatabase(Connection con) throws SQLException {
		if (!tableExist(con, "POLLS")) {
			PreparedStatement pst = con.prepareStatement(
					"CREATE TABLE POLLS\n" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
							+ "    title VARCHAR(150) NOT NULL,\n" + "    message CLOB(2048) NOT NULL\n" + ")");

			pst.executeUpdate();
			pst.close();
		}

		long foodPollID;
		long bandPollID;
		if (tableEmpty(con, "POLLS")) {
			PreparedStatement pst = con.prepareStatement("INSERT INTO POLLS (title, message) values\n"
					+ "    ('Glasanje za omiljeno jelo:', 'Od sljedećih jela, koje Vam je jelo najdraže? Kliknite na link kako biste glasali!')",
					Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			ResultSet rset = pst.getGeneratedKeys();
			rset.next();
			foodPollID = rset.getLong(1);

			rset.close();
			pst.close();

			pst = con.prepareStatement("INSERT INTO POLLS (title, message) values\n"
					+ "    ('Glasanje za omiljeni bend:', 'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!')",
					Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			rset = pst.getGeneratedKeys();
			rset.next();
			bandPollID = rset.getLong(1);

			rset.close();
			pst.close();

		} else {
			PreparedStatement pst = con.prepareStatement("SELECT id from POLLS");
			ResultSet rset = pst.executeQuery();
			rset.next();
			foodPollID = rset.getLong(1);
			rset.next();
			bandPollID = rset.getLong(1);

			rset.close();
			pst.close();
		}

		if (!tableExist(con, "POLLOPTIONS")) {
			PreparedStatement pst = con.prepareStatement(
					"CREATE TABLE POLLOPTIONS\n" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
							+ "    optionTitle VARCHAR(100) NOT NULL,\n" + "    optionLink VARCHAR(150) NOT NULL,\n"
							+ "    pollID BIGINT,\n" + "    votesCount BIGINT,\n"
							+ "    FOREIGN KEY (pollID) REFERENCES POLLS(id)\n" + ")");

			pst.executeUpdate();
			pst.close();
		}

		if (tableEmpty(con, "POLLOPTIONS")) {
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) values\n"
							+ "	('Pizza','https://en.wikipedia.org/wiki/Pizza'," + foodPollID + ",0),\n"
							+ "	('Sushi','https://en.wikipedia.org/wiki/Sushi'," + foodPollID + ",0),\n"
							+ "	('Hamburger','https://en.wikipedia.org/wiki/Hamburger'," + foodPollID + ",0),\n"
							+ "	('Fish','https://en.wikipedia.org/wiki/Fish_as_food'," + foodPollID + ",0),\n"
							+ "	('Fast food','https://en.wikipedia.org/wiki/Fast_food'," + foodPollID + ",0),\n"
							+ "	('Steak','https://en.wikipedia.org/wiki/Steak'," + foodPollID + ",0),\n"
							+ "	('Sandwich','https://en.wikipedia.org/wiki/Sandwich'," + foodPollID + ",0),\n"
							+ "	('Chicken','https://en.wikipedia.org/wiki/Chicken_as_food'," + foodPollID + ", 0)");

			pst.executeUpdate();
			pst.close();

			pst = con.prepareStatement("INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) VALUES\n"
					+ "	('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + bandPollID + ",0),\n"
					+ "	('The Platters','https://www.youtube.com/watch?v=H2di83WAOhU'," + bandPollID + ",0),\n"
					+ "	('The Beach Boys','https://www.youtube.com/watch?v=2s4slliAtQU'," + bandPollID + ",0),\n"
					+ "	('The Four Seasons','https://www.youtube.com/watch?v=y8yvnqHmFds'," + bandPollID + ",0),\n"
					+ "	('The Marcels','https://www.youtube.com/watch?v=qoi3TH59ZEs'," + bandPollID + ",0),\n"
					+ "	('The Everly Brothers','https://www.youtube.com/watch?v=tbU3zdAgiX8'," + bandPollID + ",0),\n"
					+ "	('The Mamas And The Papas','https://www.youtube.com/watch?v=N-aK6JnyFmk'," + bandPollID
					+ ",0)");

			pst.executeUpdate();
			pst.close();
		}
	}

	private boolean tableExist(Connection con, String tableName) throws SQLException {
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		if (tables.next()) {
			// Table exists
			return true;
		} else {
			// Table does not exist
			return false;
		}
	}

	private boolean tableEmpty(Connection con, String tableName) throws SQLException {
		PreparedStatement pst = con.prepareStatement("select * from " + tableName);
		ResultSet rs = pst.executeQuery();
		boolean empty = true;
		if (rs != null && rs.next()) {
			empty = false;
		}
		rs.close();
		pst.close();
		return empty;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}