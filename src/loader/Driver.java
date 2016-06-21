package loader;
import java.io.IOException; 
import java.util.ArrayList;

import character_rscs.CharacterConverterTool;
import character_rscs.CharacterInformationDownloader;
import character_rscs.URLBuilderTool;
import static java.lang.System.out;

public class Driver {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("file.encoding" , "UTF-8");
		DatabaseLoader.deleteAllEntries();
		DatabaseLoader.loadDB();
	}
	
	public static void encodedUrlTest() throws InterruptedException {
		CharacterInformationDownloader.grabCharacterInformation();
		for (ArrayList<String> list: CharacterInformationDownloader.characterInformation) {
			out.print(list.get(2) + ": ");
			out.print(URLBuilderTool.buildCharacterEncodingUrl(
					  CharacterConverterTool.convertCharacterToHexadecimal(
					  list.get(2))));
			out.println();
		}
	}
	
	public static void chineseCharEncodingTest() throws InterruptedException {
		CharacterInformationDownloader.grabCharacterInformation();
		for (ArrayList<String> list: CharacterInformationDownloader.characterInformation) {
			out.print(list.get(2) + ": ");
			for (String s: CharacterConverterTool.convertCharacterToHexadecimal(list.get(2))) {
				out.print(s + " ");
			}
			out.println();
		}
	}
	
	public static void textRetrievalTest() throws InterruptedException {
		CharacterInformationDownloader.grabCharacterInformation();
		
		for (ArrayList<String> currChar: CharacterInformationDownloader.characterInformation) {
			out.println("******************  NEW CHARACTER  ********************");
			for (String currInfo: currChar) {
				out.println(currInfo);
			}
		}
	}
	
	public static void characterEncodingToolTest(String s) {
		for (String str: CharacterConverterTool.convertCharacterToHexadecimal(s)) {
			out.print(str);
		}
	}
}
