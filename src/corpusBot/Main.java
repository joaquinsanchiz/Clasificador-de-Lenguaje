package corpusBot;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Programa principal
 * @author joaquinsanchiz
 *
 */
public class Main {
public static void main(String[] args)throws TwitterException, InterruptedException {
    	
    	//Creo mi objeto bot
    	MyBot bot = new MyBot();
    	Chronometer chronometer = new Chronometer();

    	//accesso a la api de Twitter desde el .preferences
    	TwitterOutConsole.printTwitterUser("Login-in with @" + TwitterFactory.getSingleton().getScreenName() +"\n");
        Twitter twitter = TwitterFactory.getSingleton();
    	TwitterOutConsole.printTwitterUser("Logued-in with @" + TwitterFactory.getSingleton().getScreenName() +"\n");

    	
        //hashtags a buscar
        Search searches = new Search(); 
        searches.addNewSearch("twitter");
        
        Thread corpusThread = new Thread(new Runnable() {
        	public void run(){
        		try {
        			bot.startingCorpus(chronometer, twitter, searches);
        		}
        		catch(InterruptedException e){
        			FileWriting.writeException(e.getMessage());
        			e.printStackTrace();
        		}
        	}
        });
        corpusThread.start();
    }
}
