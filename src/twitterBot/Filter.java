package twitterBot;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.User;

public class Filter {
	
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
	
	
	/**
	 * Filtro de tweets a analizar
	 * @param tweet Tweet a comprobar
	 * @return Si pasa el filtro o si no lo pasa.
	 */
	public static boolean passLikeFilter(Status tweet){ 
		
		if(tweet.isRetweet()){
			
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the likeFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not liking: is a Retweet");
			return false;
		}
		
		if(tweet.isPossiblySensitive()){
			//Filtro de contenido sensible
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the likeFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not liking: Sensible tweet");
			return false;
		}
		if(!(tweet.getUser().getFollowersCount() > 50)){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the likeFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not liking: Not enough followers");
			return false;
		}
			if(
				!tweet.getUser().getName().contains("bot") && 	//Que no sea bot
				!tweet.getText().contains("#np") &&	
				!tweet.getText().contains("#Np") &&
				!tweet.getText().contains("#NP") &&
				!tweet.getText().contains("#NOWPLAYING") &&
				!tweet.getText().contains("now playing") &&
				!tweet.getText().contains("Now Playing") &&
				!tweet.getText().contains("nowplaying") &&
				!tweet.getText().contains("NowPlaying") &&
				!tweet.getText().contains("onair") &&
				!tweet.getText().contains("OnAir") &&
				!tweet.getText().contains("radio") &&
				!tweet.getText().contains("RADIO") &&
				!tweet.getText().contains("agazine") &&
				!tweet.getUser().getName().contains("adio") &&
				!tweet.getText().contains("actor") &&
				!tweet.getText().contains("Actor") &&
				!tweet.getText().contains("actress") &&
				!tweet.getText().contains("Actress") &&
				!tweet.getText().contains("film") &&
				!tweet.getText().contains("fashion") &&
				!tweet.getText().contains("drawing") &&
				!tweet.getText().contains("re-order") &&
				!tweet.getText().contains("re-Order") &&
				!tweet.getText().contains("reorder") &&
				!tweet.getText().contains("reOrder") &&
				!tweet.getText().contains("re order") &&
				!tweet.getText().contains("re Order") &&
				!tweet.getText().contains("new favorite") &&
				!tweet.getText().contains("New favorite")

				)
			return true;
			
			
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the likeFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
            TwitterOutConsole.printTwitterLogsln("    >|Contains a NAKey");
		return false;
	}
	public static boolean passFollowFilter(Status tweet){ 
			
			if(tweet.isPossiblySensitive()){
				//Filtro de contenido sensible
				TwitterOutConsole.printTwitterLogsln("");
				TwitterOutConsole.printTwitterLogsln("Didnt pass the followFilter: ");
				TwitterOutConsole.printTwitterText(">Tweet: ");
	            System.out.println(tweet.getText()); 
	            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
	            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
				TwitterOutConsole.printTwitterLogsln("    >|Reason not following: Sensible tweet");
				return false;
			}
			if(!(tweet.getUser().getFollowersCount() > 50)){
				TwitterOutConsole.printTwitterLogsln("");
				TwitterOutConsole.printTwitterLogsln("Didnt pass the followFilter: ");
				TwitterOutConsole.printTwitterText(">Tweet: ");
	            System.out.println(tweet.getText()); 
	            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
	            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
				TwitterOutConsole.printTwitterLogsln("    >|Reason not following: Not enough followers");
				return false;
			}
				if(
					!tweet.getUser().getName().contains("bot") && 	//Que no sea bot
					!tweet.getText().contains("#np") &&	
					!tweet.getText().contains("#Np") &&
					!tweet.getText().contains("#NP") &&
					!tweet.getText().contains("#NOWPLAYING") &&
					!tweet.getText().contains("now playing") &&
					!tweet.getText().contains("Now Playing") &&
					!tweet.getText().contains("nowplaying") &&
					!tweet.getText().contains("NowPlaying") &&
					!tweet.getText().contains("eatz") &&
					!tweet.getText().contains("onair") &&
					!tweet.getText().contains("OnAir") &&
					!tweet.getText().contains("radio") &&
					!tweet.getText().contains("RADIO") &&
					!tweet.getUser().getName().contains("adio") &&
					!tweet.getText().contains("agazine") &&
					!tweet.getText().contains("actor") &&
					!tweet.getText().contains("Actor") &&
					!tweet.getText().contains("actress") &&
					!tweet.getText().contains("Actress") &&
					!tweet.getText().contains("film") &&
					!tweet.getText().contains("fashion") &&
					!tweet.getText().contains("drawing") &&
					!tweet.getText().contains("re-order") &&
					!tweet.getText().contains("re-Order") &&
					!tweet.getText().contains("reorder") &&
					!tweet.getText().contains("reOrder") &&
					!tweet.getText().contains("re order") &&
					!tweet.getText().contains("re Order") &&
					!tweet.getText().contains("new favorite") &&
					!tweet.getText().contains("New favorite")
	
					)
				return true;
			
				TwitterOutConsole.printTwitterLogsln("");
				TwitterOutConsole.printTwitterLogsln("Didnt pass the followFilter: ");
				TwitterOutConsole.printTwitterText(">Tweet: ");
	            System.out.println(tweet.getText()); 
	            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
	            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
	            TwitterOutConsole.printTwitterLogsln("    >|Contains a NAKey");
			return false;
	}
	public static boolean passDMFilter(Status tweet){ //TODO: filtro
		
		if(tweet.isPossiblySensitive()){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the DMFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not DMing: Sensible tweet");
			/* Print trace*/
			return false;
		}
		
		if(tweet.getUser().getName().contains("bot")){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the DMFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not following: Bot");
			return false;
		}
		
		if(tweet.getUser().getFollowersCount() < 100){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the DMFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not following: Not enough followers");
			return false;
		}
		
		if(tweet.getUser().isProtected()){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterLogsln("Didnt pass the DMFilter: ");
			TwitterOutConsole.printTwitterText(">Tweet: ");
            System.out.println(tweet.getText()); 
            TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
            TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
			TwitterOutConsole.printTwitterLogsln("    >|Reason not following: Is protected");
			return false;
		}
		if(
				!tweet.getText().contains("agazine") &&
				!tweet.getText().contains("actor") &&
				!tweet.getText().contains("Actor") &&
				!tweet.getText().contains("actress") &&
				!tweet.getText().contains("Actress") &&
				!tweet.getText().contains("film") &&
				!tweet.getText().contains("fashion") &&
				!tweet.getText().contains("drawing")
				){
			return true;
			
		}
		
		TwitterOutConsole.printTwitterLogsln("");
		TwitterOutConsole.printTwitterLogsln("Didnt pass the followFilter: ");
		TwitterOutConsole.printTwitterText(">Tweet: ");
        System.out.println(tweet.getText()); 
        TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
        TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
        TwitterOutConsole.printTwitterLogsln("    >|Contains a NAKey");
		return false;
	}
	public static boolean passGetMailFilter(Status tweet){ //TODO: filtro
		
		if(Filter.passDMFilter(tweet)){
			return true;
		}
		
		return false;
	}
	public static String findMailInBio(User user){ //TODO: test function
	    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(user.getDescription());
	    if (m.find()){
	    	return m.group();
	    }

		return "null";
	}
}
