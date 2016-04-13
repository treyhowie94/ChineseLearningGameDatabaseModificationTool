package tools;

public class DetermineLanguage {
	
	public static int detectLanguage(String str) {
		if (str.length() == 0 || str == null) {
			return -1;
		}
		
		int codepoint = str.codePointAt(0);
	    if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN) {
            return 0;
        }
	    else {
	    	if (isEngChar(str.charAt(0))) {
	    		int count = 1;
	    		for (int i=1; i<str.length(); i++) {
	    			if (isEngChar(str.charAt(i))) {
	    				count++;
	    			}
	    		}
	    		
	    		if (count == str.length()) {
	    			return 1;
	    		}
	    		else {
	    			return -1;
	    		}
	    	}
	    	else {
	    		return -1;
	    	}
	    }
	}
	
	private static boolean isEngChar(char c) {
		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
    		return true;
    	}
		else {
			return false;
		}
	}
}
