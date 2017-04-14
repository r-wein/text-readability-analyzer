package dbHandler;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;

public class MySQLConectionTest {

	@Test
	public void gotConnection() throws ClassNotFoundException, SQLException {
		assertNotNull("FAIL - no database connection", MySQLConnection.getConnection());
	}
	
}
