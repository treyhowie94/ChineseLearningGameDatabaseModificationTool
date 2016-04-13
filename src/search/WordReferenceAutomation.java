package search;

import java.io.IOException; 

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static java.lang.System.err;

public class WordReferenceAutomation {
	private final WebClient CLIENT = new WebClient(BrowserVersion.CHROME);
	
	public WordReferenceAutomation() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); 

		CLIENT.getOptions().setThrowExceptionOnScriptError(false);
		CLIENT.getOptions().setThrowExceptionOnFailingStatusCode(false);
		CLIENT.getOptions().setCssEnabled(false);
		CLIENT.getOptions().setJavaScriptEnabled(false);
	}
	
	@SuppressWarnings("finally")
	public String automateSearch(String searchChar) {
		HtmlPage searchAsXml = null;
		
		try {
			searchAsXml = CLIENT.getPage("http://www.wordreference.com/enzh/" + searchChar);
	    } catch (FailingHttpStatusCodeException | IOException e1) {
			err.println("unable to get");
		} finally {
			if (searchAsXml.equals(null)) {
				return null;
			}
			else {	
				return searchAsXml.asXml();
			}
		}
		
	}
	
}
