package dbconn;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.out;
import static java.lang.System.err;

public class DBConnection {

	private static final String DB_CONN     = "jdbc:mysql://chinesegame.crgklud03xra.us-west-2.rds.amazonaws.com:3306/mydb?characterEncoding=utf8";
	private static final String ROOT_PASS   = "chinesegame";
	private static final String ROOT_USER   = "sclewis";
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public static Connection connection = null;
	
	private DBConnection() {
		// private class ~~~> prevent instantiation
	}
	
	public static void connectDB() {
		out.println("connecting...");
		try {
			Class.forName (DRIVER_NAME);
			connection = DriverManager.getConnection (DB_CONN, ROOT_USER, ROOT_PASS);
	        out.println ("Database connection established");
		} catch (ClassNotFoundException e) {
			err.println("Driver Class not found, unable to connect to database ~~~> must exit with error code -1");
			System.exit(-1);
		} catch (SQLException e) {
			err.println("SQL error, unable to connect to database ~~~> must exit with error code -1");
			System.exit(-1);
		}
	}
}
