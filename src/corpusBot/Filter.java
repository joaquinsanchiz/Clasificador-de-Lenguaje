package corpusBot;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.User;

public class Filter {
	
	/**
	 * Filtro de los estados para el corpus
	 * @param tweet
	 * @return
	 */
	public static boolean passCorpusFilter(Status tweet){
		
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		if(tweet.isPossiblySensitive()){
			//Filtro de contenido sensible
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the corpusFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not adding to corpus: Sensible tweet");
			return false;
		}
		
		if(
				!tweet.getUser().getName().contains("bot") &&
				!tweet.getText().matches(regex) &&
				!tweet.getText().contains("@YouTube") &&
				!tweet.getLang().equals("da") &&
				!tweet.getLang().equals("th") &&
				!tweet.getLang().equals("ar") &&
				!tweet.getLang().equals("ko")
			){
			return true;
		}
		
		TwitterOutConsole.printTwitterLogsln("");
		TwitterOutConsole.printTwitterLogsln("Didnt pass the corpusFilter: ");
		TwitterOutConsole.printTwitterText(">Tweet: ");
        System.out.println(tweet.getText()); 
        TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
        TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
        TwitterOutConsole.printTwitterLogsln("    >|Contains a NAKey");
        return false;
		
	}
}
