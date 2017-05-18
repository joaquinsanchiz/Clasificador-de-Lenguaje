package twitterBot;


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
    	UsersToFollow usersToFollow = new UsersToFollow();
    	UsersToMail usersToMail = new UsersToMail();
    	Chronometer chronometer = new Chronometer();

    	//accesso a la api de Twitter desde el .preferences
    	TwitterOutConsole.printTwitterUser("Login-in with @" + TwitterFactory.getSingleton().getScreenName() +"\n");
        Twitter twitter = TwitterFactory.getSingleton();
    	TwitterOutConsole.printTwitterUser("Logued-in with @" + TwitterFactory.getSingleton().getScreenName() +"\n");

    	
        //hashtags a buscar
        Search searches = new Search();
        /*
        searches.addNewSearch("\"#producer\"");
        searches.addNewSearch("\"#dj\"");
        searches.addNewSearch("\"#ableton\"");
        searches.addNewSearch("\"#studio\"");
        searches.addNewSearch("\"#flstudio\"");
        searches.addNewSearch("\"#trapnation\"");
        searches.addNewSearch("\"#futurebass\"");
        searches.addNewSearch("\"#EDM\"");
        searches.addNewSearch("\"#trapmusic\"");
        searches.addNewSearch("\"#thechainsmokers\"");
        searches.addNewSearch("\"#chainsmokers\"");
        searches.addNewSearch("\"#producer #logic\"");
        searches.addNewSearch("\"#martingarrix\"");
        searches.addNewSearch("\"#garrix\"");
        searches.addNewSearch("\"#futurehouse\"");
        searches.addNewSearch("\"#progressivehouse\"");
        searches.addNewSearch("\"#soundcloud\"");
        searches.addNewSearch("\"#sampling\"");
        searches.addNewSearch("\"#mashup\"");
        searches.addNewSearch("\"#mix\"");
        searches.addNewSearch("\"#protools\"");
        searches.addNewSearch("\"#SPINNTALENT\"");
        searches.addNewSearch("\"#tomorrowland\"");
        searches.addNewSearch("\"#ultramusic\"");
        searches.addNewSearch("\"#bigroom\"");
        searches.addNewSearch("\"#dubstep\"");
        searches.addNewSearch("\"#basshouse\"");
        searches.addNewSearch("\"#house\"");
        
        Search usersSearch = new Search(searches);
        usersSearch.addNewSearch("\"#radio\"");

        //Nuevo hilo para los likes 
        Thread likingThread = new Thread(new Runnable() {
            public void run() {
                try {
					bot.startLiking(chronometer, twitter, searches);
				} catch (TwitterException e) {
					FileWriting.writeException(e.getErrorMessage());
					e.printStackTrace();
				} catch (InterruptedException e) {
					FileWriting.writeException(e.getMessage());
					e.printStackTrace();
				}
            }
        });
        //Nuevo hilo para las busquedas
        Thread followingThread = new Thread(new Runnable() {
        	public void run(){
        		try {
					bot.startFollowing(chronometer, twitter, searches, usersToFollow);
        		} catch (TwitterException e) {
					FileWriting.writeException(e.getErrorMessage());
					e.printStackTrace();
				} catch (InterruptedException e) {
					FileWriting.writeException(e.getMessage());
					e.printStackTrace();
				}
        	}
        });
        
        //TODO
        Thread mailingThread = new Thread(new Runnable() {
        	public void run(){
        		try {
        			bot.startGettingMails(twitter, usersSearch, usersToMail);
  
				} catch (InterruptedException e) {
					FileWriting.writeException(e.getMessage());
					e.printStackTrace();
				}
        	}
        });*/
        
        
        
        //likingThread.start();
        //followingThread.start();
        //mailingThread.start();
        //twitter.sendDirectMessage(MyBot.JoaquinSanchizID, "Hola bro");      
        
        
        
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
