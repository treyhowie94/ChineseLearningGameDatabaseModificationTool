package resource_gathering;
import java.io.File;  
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.System.err;
import static java.lang.System.out;

public class CharacterInformationDownloader {
	private static final ArrayList<File> ALL_CHINESE_CHAR_PAGES = 
			new ArrayList<File>(Arrays.asList(new File(CharacterInformationDownloader.
					class.getClassLoader().getResource("resource_gathering/resources").getFile()).listFiles())); 
	
	public static ArrayList<ArrayList<String>> characterInformation = new ArrayList<ArrayList<String>>(0);
	
	
	private CharacterInformationDownloader() {
		// Static class ~~~> prevent instantiation
	}
	
	public static void grabCharacterInformation() throws InterruptedException {
		out.println("getting character information");
		try {
			for (File currHtmlFile: ALL_CHINESE_CHAR_PAGES) {
				Document doc = Jsoup.parse(currHtmlFile, "UTF-8");
				analyzePage(doc);
				removeDuplicates();
			}
		} catch (IOException e) {
			err.println("Jsoup could not parse the Html file ~~~~> could not analyze page");
			e.printStackTrace();
		}
	}
	
	private static void analyzePage(Document doc) {
		assert !(doc == null) : "the document is null";
		assert doc.toString().length() > 0 : "the document has an invalid String length ~~~~> no Html code";
		
		Elements tr_s = doc.select("tr");
		
		for (Element curr_tr: tr_s) {
			ArrayList<String> currCharInfo = new ArrayList<String>(0);
			
			Elements td_s = curr_tr.select("td");
			for (Element curr_td: td_s) {
				currCharInfo.add(curr_td.text());
			}
			
			if (!currCharInfo.isEmpty()) {
				characterInformation.add(currCharInfo);
			}
		}
	}
	
	private static void removeDuplicates() {
		if (!characterInformation.isEmpty()) {	
			Set<ArrayList<String>> s = new LinkedHashSet<>();
			
			s.addAll(characterInformation);
			characterInformation.clear();
			characterInformation.addAll(s);
		}
	}
	
}
