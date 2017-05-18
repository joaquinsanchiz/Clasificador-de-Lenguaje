package twitterBot;

import java.util.Calendar;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterOutConsole {
	  public static final String ANSI_RESET = "\u001B[0m";
	  /**
	   * Color Negro, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_BLACK = "\u001B[30m";
	  /**
	   * Color Rojo, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_RED = "\u001B[31m";
	  /**
	   * Color Verde, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_GREEN = "\u001B[32m";
	  /**
	   * Color Amarillo, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_YELLOW = "\u001B[33m";
	  /**
	   * Color Azul, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_BLUE = "\u001B[34m";
	  /**
	   * Color Purpura, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_PURPLE = "\u001B[35m";
	  /**
	   * Color Cyan, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_CYAN = "\u001B[36m";
	  /**
	   * Color Blanco, se coloca al inicio de la cadena
	   */
	   public static final String ANSI_WHITE = "\u001B[37m";
	   
	   
	   
	   public static void printError(String error) throws InterruptedException{
		   error += " at: "  + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
		   System.err.println(error);
			FileWriting.writeException(error);
	        try {
				TwitterFactory.getSingleton().sendDirectMessage(MyBot.JoaquinSanchizID, error);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (TwitterException e) {
				e.printStackTrace();
			}

			if(error.contains("temporarily locked")){
				System.exit(0);
			}
			if(error.contains("limit exceeded")){
				Thread.sleep(1000*60*60*24);
			}
	   }
	   public static void printTwitterText(String text){
		   System.out.print(TwitterOutConsole.ANSI_CYAN + text);
	   }
	   public static void printTwitterUser(String text){
		   System.out.print(TwitterOutConsole.ANSI_GREEN + text);
	   }
	   public static void printTwitterLang(String text){
		   System.out.print(TwitterOutConsole.ANSI_YELLOW + text);
	   }
	   public static void printTwitterLogs(String text){
		   System.out.print(TwitterOutConsole.ANSI_PURPLE + text);
	   }
	   public static void printTwitterEventln(String text){
		   System.out.println(TwitterOutConsole.ANSI_RED + text);
	   }
	  
	   public static void printTwitterTextln(String text){
		   System.out.println(TwitterOutConsole.ANSI_CYAN + text);
	   }
	   public static void printTwitterUserln(String text){
		   System.out.println(TwitterOutConsole.ANSI_GREEN +text);
	   }
	   public static void printTwitterLangln(String text){
		   System.out.println(TwitterOutConsole.ANSI_YELLOW + text);
	   }
	   public static void printTwitterLogsln(String text){
		   System.out.println(TwitterOutConsole.ANSI_PURPLE + text);
	   }
	   
	   public static void startRoutine(String routineName){
			TwitterOutConsole.printTwitterLogsln("");
			TwitterOutConsole.printTwitterEventln("-Starting "+ routineName + " at: " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
			TwitterOutConsole.printTwitterLogsln("");
		}
		
		public static void startBreak(String routineName, int breakTime) throws InterruptedException{
			System.out.println("");
	    	TwitterOutConsole.printTwitterEventln("-"+ routineName + " " + breakTime + " mins break" + " at: "  + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
	    	Thread.sleep(1000*60*breakTime); //Paro 20 minutos por cada 5 minutos -> 
			TwitterOutConsole.printTwitterEventln("-Back to "+ routineName + " routine" + " at: "  + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
			System.out.println("");
		}
		
		public static void getTweetInfo(Status tweet){
			
			System.out.println("");
			TwitterOutConsole.printTwitterText(">Tweet: ");
	        System.out.println(tweet.getText()); 
	        TwitterOutConsole.printTwitterUserln("  >|From @" + tweet.getUser().getName());
	        TwitterOutConsole.printTwitterLangln("  >|Lang: " + tweet.getLang());
	        
		}
		
		public static void printQuery(Search searches, String routine){
			TwitterOutConsole.printTwitterLogsln("");
	        TwitterOutConsole.printTwitterEventln("--Current query at "+ routine + ": " + searches.getSearch(searches.getRandomIterator())); 
			TwitterOutConsole.printTwitterLogsln("");
		}
		
		public static void sendingMessageInfo(String msg, Status tweet){
			TwitterOutConsole.printTwitterTextln(" >|Sending message: " + msg);
			TwitterOutConsole.printTwitterUserln(" >|To: " + tweet.getUser().getName());
		}
		public static void startFollowingInfo(Status tweet){
			TwitterOutConsole.printTwitterUserln("  >|Following @" + tweet.getUser().getName());
		}

}
