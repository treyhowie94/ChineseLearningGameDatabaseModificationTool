package character_rscs;
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
					class.getClassLoader().getResource("character_rscs/resources").getFile()).listFiles())); 
	//private static final SearchAutomationTool SEARCH_AUTOMATOR = new SearchAutomationTool();
	
	//private static int charsRunThrough = 0;
	
	public static ArrayList<ArrayList<String>> characterInformation = new ArrayList<ArrayList<String>>(0);
	//public static ArrayList<File> soundFiles = new ArrayList<File>(0);
	
	
	
	private CharacterInformationDownloader() {
		// Static class ~~~> prevent instantiation
	}
	
	public static void grabCharacterInformation() throws InterruptedException {
		out.println("getting character information...");
		try {
			for (File currHtmlFile: ALL_CHINESE_CHAR_PAGES) {
				Document doc = Jsoup.parse(currHtmlFile, "UTF-8");
				analyzePage(doc);
				removeDuplicates();
			}
			
			out.println("done analyzing the pages");
			
		} catch (IOException e) {
			err.println("Jsoup could not parse the Html file ~~~~> could not analyze page");
			e.printStackTrace();
		}
	}
	
	private static void analyzePage(Document doc) {
		assert !doc.equals(null) : "the document is null";
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
				
				/*ArrayList<String> encodedCharBits = CharacterConverterTool.convertCharacterToHexadecimal(
						currCharInfo.get(2));
				assert encodedCharBits.size() > 0 : "character could not be encoded to hex ~~~> cannot build Url";
				
				String soundFileUrl = URLBuilderTool.buildCharacterEncodingUrl(encodedCharBits);
				assert !soundFileUrl.equals(null) : "null Url ~~~> cannot search soundofText.com";
				assert soundFileUrl.contains("http://soundoftext.com") : "invalid Url ~~~> cannot build valid Url object";
				
				SEARCH_AUTOMATOR.automateSearch(currCharInfo.get(2));
				
				try {
					File soundFile = new File(new URI(soundFileUrl));
					FileUtils.copyURLToFile(new URL(soundFileUrl), soundFile, 10000, 10000);
					soundFiles.add(soundFile);
				} catch (IOException | URISyntaxException e1) {
					err.println("could not grab sound file for " + currCharInfo.get(2));
					soundFiles.add(null);
				}
				
				charsRunThrough++;
				if (charsRunThrough == 500) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						err.println("could not sleep ~~~~> continuing");
					} finally {
						charsRunThrough = 0;
					}
				}
				*/
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
