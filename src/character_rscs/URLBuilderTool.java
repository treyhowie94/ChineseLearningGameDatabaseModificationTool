package character_rscs;
import java.util.ArrayList;

public class URLBuilderTool {
	private static final String SOUND_OF_TEXT_URL = "http://soundoftext.com/static/sounds/zh-TW/";
	private static final String EXTENSION         = ".mp3";
	
	
	public static String buildCharacterEncodingUrl(ArrayList<String> encoding) {
		StringBuilder sb = new StringBuilder();
		
		for (String s: encoding) {
			sb.append("%" + s);
		}
		
		return SOUND_OF_TEXT_URL + sb.toString() + EXTENSION;
	}
}
