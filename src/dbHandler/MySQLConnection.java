package dbHandler;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

/**
 * This allows us to connect to the readability database
 * 
 * @author RossWeinstein
 */

public class MySQLConnection {

	private final static String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://localhost:3306/readability?autoReconnect=true&useSSL=false";
	private final static String DB_USERNAME = "****";
	private final static String DB_PASSWORD = "******";

	/**
	 * Connects to the readability database
	 * 
	 * @return Connection our connection to use in the DBInteract class
	 * @throws ClassNotFoundException could not find the MYSQL_DRIVER_CLASS
	 * @throws SQLException error connecting to the database
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection connect;

		Class.forName(MYSQL_DRIVER_CLASS);

		connect = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		// System.out.println("Successful connection to database.\n");
		return connect;
	}

	public static void main(String[] args) {

	}
}
