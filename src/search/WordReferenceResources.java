package search;

import java.util.ArrayList; 
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import tools.TextRefinement;

public class WordReferenceResources {
	private static final WordReferenceAutomation AUTO_WORD_REF = new WordReferenceAutomation();
	
	public static ArrayList<Character> grabTranslation(String engWord) {
		String html = AUTO_WORD_REF.automateSearch(engWord);
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		
		return analyzePage(doc);
	}
	
	private static ArrayList<Character> analyzePage(Document doc) {
		if (!doc.getElementsByClass("WRD").isEmpty()) {	
			Element table = doc.getElementsByClass("WRD").get(0);
			ArrayList<Element> good_tr_s = new ArrayList<Element>(0);
			
			for (Element tr: table.getElementsByTag("tr")) {
				if (tr.hasAttr("id") && tr.id().contains("enzh:")) {
					good_tr_s.add(tr);
				}
			}
			
			ArrayList<Character> chineseChars = new ArrayList<Character>(0);
			for (Element curr_tr: good_tr_s) {
				String tr_text = curr_tr.getElementsByClass("ToWrd").get(0).text();
				ArrayList<Character> currChars = TextRefinement.refineChineseText(tr_text);
				
				chineseChars.addAll(currChars);
			}
			
			return removeDuplicates(chineseChars);
		}
		else {
			return null;
		}
	}
	
	private static ArrayList<Character> removeDuplicates(ArrayList<Character> chars) {
		Set<Character> hs = new HashSet<>();
		
		hs.addAll(chars);
		chars.clear();
		chars.addAll(hs);

		return chars;
	}
}
