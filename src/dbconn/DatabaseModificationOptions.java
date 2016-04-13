package dbconn;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import resource_gathering.CharacterInformationDownloader;
import search.WordReferenceResources;
import tools.DetermineLanguage;
import tools.ValueChecking;
import static java.lang.System.err;
import static java.lang.System.out;

public class DatabaseModificationOptions {
	
	private static final int CHINESE = 0;
	private static final int ENGLISH = 1;
	
	public static ArrayList<ArrayList<String>> misses = new ArrayList<ArrayList<String>>(0);
	
	public static boolean addNewEntry(boolean valid, String pinyin, String charToAdd, String definition, String gradeLevel) {
		if (DBConnection.connection == null) {
			throw new RuntimeException("DB connection is null ~~~> unable to communicate with the DB");
		}
		
		if (!valid) {
			return false;
		}
		else {
			final String queryCheck = "INSERT INTO characters(pinyin, charText, definition, gradeLevel) VALUES (?, ?, ?, ?)";
			
			try {
				PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
				
				ps.setString(1, pinyin);
				ps.setString(2, charToAdd);
				ps.setString(3, definition);
				ps.setInt(4, Integer.parseInt(gradeLevel));
				
				ps.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				err.println("SQLException ~~~> unable to insert new values");
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean deleteEntry(String charToDelete) {
		if (DBConnection.connection == null) {
			throw new RuntimeException("DB connection is null ~~~> unable to communicate with the DB");
		}
		
		if (!charAlreadyExists(charToDelete).isEmpty()) {
			final String deleteQuery = "DELETE FROM characters WHERE charText = ?";
			
			try {
				PreparedStatement ps = DBConnection.connection.prepareStatement(deleteQuery);
				ps.setString(1, charToDelete);
				ps.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				err.println("SQLException ~~~> unable to delete  \"" + charToDelete + "\"");
				return false;
			}
		}
		else {
			out.println("character doesn't exist in the table! ~~~> not deleting \"" + charToDelete + "\"");
			return false;
		}
	}
	
	public static boolean updateRowInformation(String chineseChar, String[] updateFields) {
		try {
			String sqlStatement = "UPDATE characters SET pinyin = ?, definition = ?, gradeLevel = ? WHERE charText = ?";
			
			PreparedStatement statement = DBConnection.connection.prepareStatement(sqlStatement);
			statement.setString(1, updateFields[0]);
			statement.setString(2, updateFields[1]);
			
			if (ValueChecking.isStringNumeric(updateFields[2])) {	
				statement.setInt(3, Integer.valueOf(updateFields[2]));
			}
			else {
				statement.setNull(3, java.sql.Types.INTEGER);
			}
			
			statement.setString(4, chineseChar);
			statement.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			err.println("unable to update row for " + chineseChar);
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<String[]> searchDB(String searchVal) {
		int language = DetermineLanguage.detectLanguage(searchVal);
		
		if (language == CHINESE) {
			return searchDBWithChineseChar(searchVal);
		}
		else if (language == ENGLISH) {
			return searchDBWithEnglishWord(searchVal);
		}
		else {
			return null;
		}
		
	}
	
	public static void deleteAllEntries() {
		if (DBConnection.connection == null) {
			throw new RuntimeException("DB connection is null ~~~> unable to communicate with the DB");
		}
		
		try {
			PreparedStatement stmt = DBConnection.connection.prepareStatement("TRUNCATE characters");
			stmt.executeUpdate();
		} catch (SQLException e) {
			err.println("SQL Exception ~~~> unable to perform database truncation");
			return;
		}
	}
	
	public static void restoreDB() {
		try {
			CharacterInformationDownloader.grabCharacterInformation();
			if (CharacterInformationDownloader.characterInformation.isEmpty()) {
				throw new IllegalArgumentException("Character information array is empty ~~~> unable to restore DB");
			}
			
		    PreparedStatement stmt = DBConnection.connection.prepareStatement("INSERT INTO characters("
		    		+ "pinyin, charText, definition, gradeLevel) VALUES(?, ?, ?, ?)");
		    for (ArrayList<String> list: CharacterInformationDownloader.characterInformation) { 
            	stmt.setString(1, list.get(1));
	            stmt.setString(2, list.get(2));
	            stmt.setString(3, list.get(3));
	            
	            if (list.get(4).equals("null")) {
	            	stmt.setNull(4, java.sql.Types.INTEGER);
	            }
	            else {
	            	stmt.setInt(4, Integer.parseInt(list.get(4)));
	            }
	            
	            stmt.executeUpdate();
	        }
            
		 } catch (SQLException | InterruptedException e) {
	        e.printStackTrace();
		 }
	}
	
	public static ArrayList<String[]> charAlreadyExists(String chineseChar) {
		assert !(chineseChar == null);
		
		ArrayList<String[]> searchResults = new ArrayList<String[]>(0);
		
		try {
			final String queryCheck = "SELECT * from characters WHERE charText = ?";
			
			PreparedStatement ps = DBConnection.connection.prepareStatement(queryCheck);
			ps.setString(1, chineseChar);
			
			final ResultSet resultSet = ps.executeQuery();
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				String[] row = new String[columnCount-1];
				for (int i = 2; i <= resultSetMetaData.getColumnCount(); i++) {
			        row[i - 2] = String.valueOf(resultSet.getObject(i));
			    }
				switchResults(row);
				searchResults.add(row);
			}
			
			return searchResults;
			
		} catch (SQLException e) {
			err.println("SQL error ~~~> unable to search for " + chineseChar + ", moving on...");
			e.printStackTrace();
			return null;
		}
	}
	
	private static ArrayList<String[]> searchDBWithChineseChar(String chineseChar) {
		if (DBConnection.connection == null) {
			throw new RuntimeException("DB connection is null ~~~> unable to communicate with the DB");
		}
		
		return charAlreadyExists(chineseChar);
	}
	
	private static ArrayList<String[]> searchDBWithEnglishWord(String engWord) {
		if (DBConnection.connection == null) {
			throw new RuntimeException("DB connection is null ~~~> unable to communicate with the DB");
		}
		
		ArrayList<String[]> searchResults = new ArrayList<String[]>(0);
		
		ArrayList<Character> relatedChars = WordReferenceResources.grabTranslation(engWord);
		if (relatedChars != null) {
			ArrayList<ArrayList<String[]>> existingChars = new ArrayList<ArrayList<String[]>>(0);
			for (Character currChar: relatedChars) {
				ArrayList<String[]> rows = charAlreadyExists(String.valueOf(currChar));
				if (!rows.isEmpty()) {
					existingChars.add(rows);
				}
			}
			
			for (ArrayList<String[]> list: existingChars) {
				for (String[] currRow: list) {
					searchResults.add(currRow);
				}
			}
			return searchResults;
		}
		else {
			return null;
		}
	} 
	
	private static void switchResults(String[] dbRow) {
		String tmp = dbRow[0];
		
		dbRow[0] = dbRow[1];
		dbRow[1] = tmp;
	}
	
}
