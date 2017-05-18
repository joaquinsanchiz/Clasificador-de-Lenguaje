package twitterBot;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Messages {
	
	static final int MESSAGE_EVERY = 5;
	
	private String[] messages = {
			"sureto", 
			"bro", 
			"sobran", 
			"prueba",
			"nl"
			};
	
	public String getEsMessage(){
		return this.messages[0];
	}
	
	public String getDeMessage(){
		return this.messages[1];
	}
	
	public String getPtMessage(){
		return this.messages[2];
	}
	
	public String getDefMessage(){
		return this.messages[this.messages.length-1];
	}
	
	public String getNlMessage(){
		return this.messages[3];
	}
	
	public String sendMessage(Twitter twitter, Status tweet) throws TwitterException{
		String msg = "";
		switch(tweet.getLang()){
			case "es": msg += "Hi " + tweet.getUser().getName() + ", " + this.getEsMessage(); break;
			case "de": msg += "Hi " + tweet.getUser().getName() + ", " + this.getDeMessage(); break;
			case "pt": msg += "Hi " + tweet.getUser().getName() + ", " + this.getPtMessage(); break;				
			case "nl": msg += "Hi " + tweet.getUser().getName() + ", " + this.getNlMessage(); break;
			default:   msg += "Hi " + tweet.getUser().getName() + ", " + this.getNlMessage(); break;				
		}
		twitter.sendDirectMessage(tweet.getUser().getId(), msg);
		return msg;
	}

}
