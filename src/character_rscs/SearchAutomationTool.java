package character_rscs;
import java.io.IOException; 

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import static java.lang.System.err;

public class SearchAutomationTool {
	private final WebClient CLIENT = new WebClient(BrowserVersion.CHROME);
	
	private HtmlPage        soundOfText;
	private HtmlForm        submitForm;
	private HtmlTextInput   textEntry;
	private HtmlSelect      select;
	private HtmlOption      option;
	private HtmlSubmitInput submit;
	
	public SearchAutomationTool() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); 

		CLIENT.getOptions().setThrowExceptionOnScriptError(false);
		CLIENT.getOptions().setThrowExceptionOnFailingStatusCode(false);

		try {
			this.soundOfText = CLIENT.getPage("http://soundoftext.com");
			this.submitForm  = soundOfText.getFirstByXPath("//form[@action='/sounds']");
			this.textEntry   = submitForm.getInputByName("text");
			this.select		 = (HtmlSelect) submitForm.getSelectByName("lang");
			this.option		 = select.getOptionByValue("zh-TW");
			this.submit		 = submitForm.getInputByValue("Submit");
			
			select.setSelectedAttribute(option, true);
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			err.println("failed to access soundoftext.com ~~~~> cannot search the page");
			return;
		}
	}
	
	public void automateSearch(String searchChar) {
		textEntry.setValueAttribute(searchChar);
		
	    try {
	    	submit.click();
	    	CLIENT.waitForBackgroundJavaScript(7000);
	    } catch (IOException e) {
			err.println("could not submit " + searchChar + " for search ~~~~> no sound file was able to be attained");
			return;
		} 
	}
}
