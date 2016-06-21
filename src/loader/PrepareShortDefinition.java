package loader;

import java.util.ArrayList;

public class PrepareShortDefinition {

	public static String reduceDefinition(String def) {
		ArrayList<String> breakDefinitions = new ArrayList<String>(0);
		
		int start = 0;
		for (int i=0; i<def.length(); i++) {
			if (def.charAt(i) == ';' || def.charAt(i) == ',' || def.charAt(i) == '?') {
				breakDefinitions.add(def.substring(start, i));
				start = i+1;
			}
			else if (i == def.length()-1) {
				breakDefinitions.add(def.substring(start, def.length()));
			}
		}
		
		if (breakDefinitions.isEmpty()) {
			return removeBadParts(def);
		}
		else {
			int smallest = 0;
			for (int i=0; i<breakDefinitions.size(); i++) {
				String tmp = removeBadParts(breakDefinitions.get(i));
				
				if (tmp == null) {
					continue;
				}
				
				breakDefinitions.set(i, tmp);
				
				if (tmp.length() < breakDefinitions.get(smallest).length()) {
					smallest = i;
				}
			}
			return breakDefinitions.get(smallest);
		}
	}
	
	private static String removeBadParts(String def) {
		StringBuilder sb = new StringBuilder();
		
		boolean openParen = false;
		for (int i=0; i<def.length(); i++) {
			char currChar = def.charAt(i);
			
			if (currChar == '(') {
				openParen = true;
			}
			else if (currChar == ')') {
				openParen = false;
			}
			else if (!openParen) {
				sb.append(currChar);
			}
		}
		
		String s = sb.toString();
		if (s.length() < 1) {
			return null;
		}
		else {
			return removeSpaces(sb.toString());
		}
	}
	
	private static String removeSpaces(String def) {
		if (def.equals(" ")) {
			return null;
		}
		else if (def.charAt(0) == ' ') {
			if (def.charAt(def.length()-1) == ' ') {
				return def.substring(1, def.length()-1);
			}
			else {
				return def.substring(1, def.length());
			}
		}
		else if (def.charAt(def.length()-1) == ' ') {
			return def.substring(0, def.length()-1);
		}
		else {
			return def;
		}
	}
}
