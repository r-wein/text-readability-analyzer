package dbCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbHandler.MySQLConnection;
import textAnalysis.TextAnalysis;

/**
 * This class is in charge of all our MySQL queries and updates. It interacts
 * with the Readability database.
 * 
 * @author RossWeinstein
 */

public class DBInteract {

	private Connection dbConnect;
	private PreparedStatement prepStatement;

	public DBInteract() {
		try {
			this.dbConnect = MySQLConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error - Could not connect to MySQL datbase");
		}
		this.prepStatement = null;
	}

	/**
	 * Add readability data for a text into the database
	 * 
	 * @param data
	 *            TextAnalysis object which holds all needed information for
	 *            database
	 */
	public void addEntries(TextAnalysis data) {
		try {
			int idNumber = this.addToReadability_Text(data);
			int analysisNumber = this.addToReadability_TextStatistics(data, idNumber);
			this.addToReadability_TextReadability(data, analysisNumber);
		} catch (SQLException e) {
			System.out.println("Error in Adding Entries: " + e.getMessage());
		}
	}

	/**
	 * Adds appropriate information into readability.textreadability table; this
	 * table holds all the results from performing the readability testa
	 * 
	 * @param data
	 *            TextAnalysis object with all needed information for the table
	 * @param idNumber
	 *            from textStatistics table for foreign key
	 * @throws SQLException
	 *             could not add information to readability.textReadability
	 *             table
	 */
	private void addToReadability_TextReadability(TextAnalysis data, int idNumber) throws SQLException {
		this.prepStatement = this.dbConnect
				.prepareStatement("INSERT INTO READABILITY.TEXTREADABILITY VALUES (NULL,?,?,?,?,?,?,?,?)");
		this.prepStatement.setInt(1, idNumber);
		this.prepStatement.setFloat(2, data.getResults().get("flesch reading ease").floatValue());
		this.prepStatement.setFloat(3, data.getResults().get("flesch-kincaid grade level").floatValue());
		this.prepStatement.setFloat(4, data.getResults().get("gunning fog").floatValue());
		this.prepStatement.setFloat(5, data.getResults().get("coleman-liau").floatValue());
		this.prepStatement.setFloat(6, data.getResults().get("SMOG").floatValue());
		this.prepStatement.setFloat(7, data.getResults().get("automated readability").floatValue());
		this.prepStatement.setFloat(8, data.getResults().get("average readability").floatValue());
		this.prepStatement.executeUpdate();
	}

	/**
	 * Adds appropriate information into the textStatistics table; this table
	 * holds all the information from parsing the text
	 * 
	 * @param data
	 *            TextAnalysis object with all needed information for the table
	 * @param idNumber
	 *            from text table for foreign key
	 * @return int so we can hand it off to the textReadability table for
	 * @throws SQLException
	 *             could not add information to readability.textStatistics table
	 */
	private int addToReadability_TextStatistics(TextAnalysis data, int idNumber) throws SQLException {
		this.prepStatement = this.dbConnect.prepareStatement(
				"INSERT INTO READABILITY.TEXTSTATISTICS VALUES (NULL,?,?,?,?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		this.prepStatement.setInt(1, idNumber);
		this.prepStatement.setInt(2, data.getResults().get("words").intValue());
		this.prepStatement.setInt(3, data.getResults().get("sentences").intValue());
		this.prepStatement.setInt(4, data.getResults().get("syllables").intValue());
		this.prepStatement.setInt(5, data.getResults().get("letters").intValue());
		this.prepStatement.setInt(6, data.getResults().get("unique words").intValue());
		this.prepStatement.setInt(7, data.getResults().get("complex words").intValue());
		this.prepStatement.setFloat(8, data.getResults().get("words per sentence").floatValue());
		this.prepStatement.setFloat(9, data.getResults().get("syllables per word").floatValue());
		this.prepStatement.setFloat(10, data.getResults().get("letters per word").floatValue());
		this.prepStatement.executeUpdate();

		return this.getLastUpdateID(this.prepStatement.getGeneratedKeys());
	}

	/**
	 * Adds appropriate information into text table; this table only holds an
	 * auto increment id number and the text name
	 * 
	 * @param data
	 *            TextAnalysis object with all needed information for the table
	 * @return int to hand off to the textStatistics table for its foreign key
	 * @throws SQLException
	 *             could not add information to readability.text table
	 */
	private int addToReadability_Text(TextAnalysis data) throws SQLException {
		this.prepStatement = this.dbConnect.prepareStatement("INSERT INTO READABILITY.TEXT VALUES (NULL,?)",
				Statement.RETURN_GENERATED_KEYS);
		this.prepStatement.setString(1, data.getTitle());
		this.prepStatement.executeUpdate();

		return this.getLastUpdateID(this.prepStatement.getGeneratedKeys());
	}

	/**
	 * This gets the last number generated via auto-increment in our tables
	 * 
	 * @param results
	 *            the ResultSet from our last update
	 * @return int of the last auto_incremented value
	 * @throws SQLException
	 *             could not get the last auto_increment value
	 */
	private int getLastUpdateID(ResultSet results) throws SQLException {
		int idNum = 0;
		if (results.next()) {
			idNum = results.getInt(1);
		}
		return idNum;
	}

	/**
	 * Delete a specific entry from the database based on the its title
	 * 
	 * @param textName
	 *            String representing the title of the desired record to delete
	 */
	public boolean deleteEntry(String textName) {

		if (this.doesEntryExist(textName)) {
			try {
				this.prepStatement = this.dbConnect.prepareStatement("DELETE FROM READABILITY.TEXT WHERE TITLE = ?");
				this.prepStatement.setString(1, textName);
				this.prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("Error in Delete Entry: " + e.getMessage());
			}
			return true;
		} else {
			System.out.println("Could not fild " + textName + " in database.");
			return false;
		}
	}

	private boolean doesEntryExist(String title) {
		try {
			this.prepStatement = this.dbConnect.prepareStatement("SELECT * FROM READABILITY.TEXT WHERE TITLE = ?");
			this.prepStatement.setString(1, title);
			this.prepStatement.executeQuery();
			return this.prepStatement.getResultSet().next();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Truncates the entire database. Only use if you really want to delete
	 * everything.
	 */
	public void deleteAllEntries() {
		try {
			this.prepStatement = this.dbConnect.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
			this.prepStatement.executeUpdate();
			this.prepStatement = this.dbConnect.prepareStatement("TRUNCATE TABLE READABILITY.TEXTREADABILITY");
			this.prepStatement.executeUpdate();
			this.prepStatement = this.dbConnect.prepareStatement("TRUNCATE TABLE READABILITY.TEXTSTATISTICS");
			this.prepStatement.executeUpdate();
			this.prepStatement = this.dbConnect.prepareStatement("TRUNCATE TABLE READABILITY.TEXT");
			this.prepStatement.executeUpdate();
			this.prepStatement = this.dbConnect.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
			this.prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error in Delete All Entrires: " + e.getMessage());
		}
	}

	/**
	 * Queries the database for everything, SELECT *
	 * 
	 * @return ResultSet everything piece of information in the database
	 */
	public ResultSet getAllData() {
		try {
			this.prepStatement = this.dbConnect.prepareStatement("SELECT * FROM readability.allData");
			this.prepStatement.executeQuery();
			return this.prepStatement.getResultSet();
		} catch (SQLException e) {
			System.out.println("Error in Get All Data: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Prints all the information from the database to the console
	 */
	public void printAllResults() {
		try {
			this.printResults(this.getAllData());
		} catch (SQLException e) {
			System.out.println("Error in Print All Results: " + e.getMessage());
		}
	}

	/**
	 * Prints the supplied results to the console
	 * 
	 * @param results
	 *            ResultSet results from database query
	 * @throws SQLException
	 *             unable to get query results
	 */
	private void printResults(ResultSet results) throws SQLException {

		if (!results.next()) {
			System.out.println("\tDatabase Empty");
		} else {
			System.out.println("\t#\tScore:\t\tTitle:");
			System.out.println("\t---\t----------\t----------------------");
			int place = 1;
			results.beforeFirst();
			while (results.next()) {
				System.out.println("\t" + place++ + "\t" + results.getDouble("averageReadability") + "\t\t"
						+ results.getString("title"));
			}
		}
	}

	/**
	 * Queries the database for the most difficult text, those with the highest
	 * average readability scores, within a supplied limit.
	 * 
	 * @param results
	 *            int how many entries do you want to return
	 * @return ResultsSet results from the database query
	 * @throws SQLException
	 *             unable to get query results
	 */
	public ResultSet getTop(int results) throws SQLException {
		this.prepStatement = this.dbConnect.prepareStatement("SELECT * FROM readability.allData LIMIT " + results);
		this.prepStatement.executeQuery();
		return this.prepStatement.getResultSet();
	}

	/**
	 * Queries the database for the least difficult text, those with the lowest
	 * average readability scores, within a supplied limit.
	 * 
	 * @param results
	 *            int how many entries do you want to return
	 * @return ResultsSet results from the database query
	 * @throws SQLException
	 *             unable to get query results
	 */
	public ResultSet getBottom(int results) throws SQLException {
		this.prepStatement = this.dbConnect
				.prepareStatement("SELECT * FROM readability.allData ORDER BY averagereadability ASC LIMIT " + results);
		this.prepStatement.executeQuery();
		return this.prepStatement.getResultSet();
	}

	/**
	 * Prints to the console the results of the getTop query
	 * 
	 * @param results
	 *            int how many entries do you want returned
	 */
	public void showTop(int results) {
		try {
			System.out.println("Most Difficult Texts to Read:\n");
			this.printResults(this.getTop(results));
		} catch (SQLException e) {
			System.out.println("Error in Show Most Difficult: " + e.getMessage());
		}
	}

	/**
	 * Prints to the console the results of the getBottom query
	 * 
	 * @param results
	 *            int how many entries do you want returned
	 */
	public void showBottom(int results) {
		try {
			System.out.println("\nLeast Difficult Texts to Read\n");
			this.printResults(this.getBottom(results));
		} catch (SQLException e) {
			System.out.println("Error in Show Least Difficult: " + e.getMessage());
		}
	}

	/**
	 * Prints a selection of text with the highest and lowest average
	 * readability values
	 * 
	 * @param results
	 *            int how many entries from each list do you want returned
	 */
	public void showBestAndWorst(int results) {
		this.showTop(results);
		this.showBottom(results);
	}
}
