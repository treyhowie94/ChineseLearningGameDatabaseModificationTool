package tools;

import java.util.ArrayList;

public class TextRefinement {

	public static ArrayList<Character> refineChineseText(String text) {
		ArrayList<Character> refinedText = new ArrayList<Character>(0);
		
		for (int i=0; i<text.length(); i++) {
			int codepoint = text.codePointAt(i);
	       
	        if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN) {
	            refinedText.add(text.charAt(i));
	        }
		}
		
		return refinedText;
	}
}
