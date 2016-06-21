package character_rscs;
import java.io.UnsupportedEncodingException;  
import java.util.ArrayList;

import static java.lang.System.err;

public class CharacterConverterTool {

	@SuppressWarnings("finally")
	public static ArrayList<String> convertCharacterToHexadecimal(String chineseChar) {
		ArrayList<String> rtnEncoding = new ArrayList<String>(0);
		
		try {
			byte[] bs = chineseChar.getBytes("utf-8");
			for (byte b : bs) {
			    String currVal = grabUsefulBits(Integer.toHexString(b));
				
			    if (!currVal.equals("NO_GO")) {
					rtnEncoding.add(currVal);
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			err.println("CharacterConverterTool: could not convert " + chineseChar + " to its hexadecimal representation ~~~> invalid");
		} finally {
			return rtnEncoding;
		}
	}
	
	private static String grabUsefulBits(String hexEncoding) {
		assert !hexEncoding.equals(null) : "hex encoding is null ~~~> can't grab useful bits";
		
		if (hexEncoding.length() == 0) {
			return "NO_GO";
		}
		
		StringBuilder sb = new StringBuilder();
		for (char c: hexEncoding.substring(6, 8).toCharArray()) {
			assert Character.isLetterOrDigit(c) : "character is neither letter nor digit ~~~> invalid character";
			if (!Character.isDigit(c)) {	
				int upperCase = ((int) c) - 32;
				assert !Character.isLowerCase((char) upperCase) : "character is lower case ~~~> invalid conversion";
				
				sb.append((char) upperCase);
			}
			else {
				assert Character.isDigit(c) : "character is not digit as expected ~~~> invalid addition";
				sb.append(c);
			}
		}
	
		return sb.toString();
	}
	
}
