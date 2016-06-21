package loader;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import character_rscs.CharacterInformationDownloader;

import static java.lang.System.out;

public class DatabaseLoader {

	//removed DB location information
	
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	private static Connection connection = null;
	
	private DatabaseLoader() {
		// private class ~~~> prevent instantiation
	}
	
	public static void loadDB() {
		try {
			CharacterInformationDownloader.grabCharacterInformation();
		    
		    out.println("preparing database statement...");
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO characters(pinyin, charText, definition, reducedDefinition, gradeLevel) VALUES(?, ?, ?, ?, ?)");
			 
		    out.println("statement prepared");
		    out.println("adding words to the database...");
		    
			int listSize = CharacterInformationDownloader.characterInformation.size();
			for (int i=0; i<listSize; i++) { 
		    	ArrayList<String> list = CharacterInformationDownloader.characterInformation.get(i);
		    	
		    	stmt.setString(1, list.get(1));
	            stmt.setString(2, list.get(2));
	            stmt.setString(3, list.get(3));
	            stmt.setString(4, PrepareShortDefinition.reduceDefinition(list.get(3)));
	            
	            if (list.get(4).equals("null")) {
	            	stmt.setNull(5, java.sql.Types.INTEGER);
	            }
	            else {
	            	stmt.setInt(5, Integer.parseInt(list.get(4)));
	            }
	            
	            stmt.executeUpdate();
	            
	            if (i == (listSize / 4)) {
	            	out.println("quarter of the way done adding characters");
	            }
	            else if (i == (listSize / 2)) {
	            	out.println("half of the way done adding characters");
	            }
	            else if (i == (listSize * 0.75)) {
	            	out.println("three quarters of the way done adding characters");
	            }
	        }
			
			out.println("done!");
            
		 } catch (SQLException | InterruptedException e) {
	        e.printStackTrace();
		 }
	}
	
	public static void deleteAllEntries() {
		connectDB();
		
		out.println("deleting characters...");
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("TRUNCATE characters");
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void connectDB() {
		try {
			out.println("connecting to the database...");
			Class.forName (DRIVER_NAME);
			connection = DriverManager.getConnection (DB_CONN, ROOT_USER, ROOT_PASS);
	        System.out.println ("Database connection established");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
