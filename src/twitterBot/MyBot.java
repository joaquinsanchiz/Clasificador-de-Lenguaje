package twitterBot;

import java.util.List;


import java.util.Calendar;
import java.util.HashMap;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Clase que representa nuestro bot de likes.
 * @author joaquinsanchiz
 *
 */
public class MyBot {
	
	/**
	 * Duracion del break en minutos.
	 */
	int LIKE_BREAKTIMEDURATION = 1;
	/**
	 * Break cada tantos segundos.
	 */
	int LIKE_BREAKTIME = 300;
	/**
	 * Tiempo de espera si ya has dado a like.
	 */
	int ALREADYLIKEDTIME = 5;
	/**
	 * Parada entre cada like.
	 */
	int LIKE_EVERY_SECS = 1;
	/**
	 * Umbral de likes antes de BREAKTIME.
	 */
	int LIKE_UMBRAL = 20; //Likes cada BREAKTIME time
	
	
	/**
	 * Pequeño break cada tantos segundos.
	 */
	int FOLLOW_SMALLBREAKTIME = 300; //Secs
	/**
	 * Duracion del pequeño break.
	 */
	int FOLLOW_SMALLBREAKTIME_DURATION = 15; //Mins
	/**
	 * Gran break cada tantos segundos.
	 */
	int FOLLOW_LARGEBREAKTIME = 1800; //Secs
	/**
	 * Duracion del gran break.
	 */
	int FOLLOW_LARGEBREAKTIME_DURATION = 60; //Mins
	/**
	 * Espera entre cada follow.
	 */
	int FOLLOW_EVERY_MINS = 4;
	
	static final long JoaquinSanchizID = 952542673;
	
	
	
	public void startingCorpus(Chronometer chronometer, Twitter twitter, Search searches) throws InterruptedException{
		TwitterOutConsole.startRoutine("Corpus routine");
		HashMap<String,String> corpus = new HashMap<String,String>();
		
		while(true){
			try{
				int corpusCounter = 0;
				chronometer.reset();
				chronometer.start();
				
				while(chronometer.currentTimeInSeconds() < this.LIKE_BREAKTIME){
					Query query = new Query(searches.getRandomSearch());
					
					TwitterOutConsole.printQuery(searches, "Corpus routine");
					QueryResult result = twitter.search(query);
					List<Status> tweets = result.getTweets();
					
					for(Status tweet: tweets){
						try{
							if(Filter.passCorpusFilter(tweet)){
								if(corpus.size() < 5000){
									if(!corpus.containsKey(tweet.getText())){
										if(!tweet.isRetweet()){
											
											String tweetAdapted = tweet.getText().replaceAll("\n", " ");
											tweetAdapted = tweetAdapted.replaceAll("\"", "");
											if(tweetAdapted.split("\\s+").length > 2){
												corpus.put(tweet.getText(), tweet.getLang());
												FileWriting.writeCorpusLine(tweetAdapted, tweet.getLang());
												TwitterOutConsole.getTweetInfo(tweet);
												
												corpusCounter++;
												TwitterOutConsole.printTwitterLogsln("  >|Corpus counter: " + corpusCounter);
												Thread.sleep(1000*this.LIKE_EVERY_SECS);
											}
											else Thread.sleep(1000*this.ALREADYLIKEDTIME);
										}
										else{
											
											String tweetAdapted = tweet.getRetweetedStatus().getText().replaceAll("\n", " ");
											tweetAdapted = tweetAdapted.replaceAll("\"", "");
											if(tweetAdapted.split("\\s+").length > 2){
												corpus.put(tweet.getText(), tweet.getRetweetedStatus().getLang());
												FileWriting.writeCorpusLine(tweetAdapted, tweet.getRetweetedStatus().getLang());
												TwitterOutConsole.getTweetInfo(tweet.getRetweetedStatus());
												TwitterOutConsole.printTwitterLogsln("  >|Was a retweet");
												
												corpusCounter++;
												TwitterOutConsole.printTwitterLogsln("  >|Corpus counter: " + corpusCounter);
												Thread.sleep(1000*this.LIKE_EVERY_SECS);
											}
											else Thread.sleep(1000*this.ALREADYLIKEDTIME);
										}
										
									}
									else Thread.sleep(1000*this.ALREADYLIKEDTIME);;
								}
								else{
									twitter.sendDirectMessage(JoaquinSanchizID, "Corpus acabado");
								}
							}
						}
						catch(TwitterException e){
							if(e.getErrorMessage().contains("rate limit"))
								Thread.sleep(1000*60*60);
						}
					}
				}
	        	TwitterOutConsole.startBreak("Corpus routine", this.LIKE_BREAKTIMEDURATION);
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
			catch(NullPointerException e){
				TwitterOutConsole.printError("Null pointer exception");
			}
		}
	}
	
	
	
	/**
	 * Metodo que computa los likes del bot
	 * @param chronometer Cronometro para el tiempo
	 * @param twitter Twitter
	 * @param searches Busquedas
	 * @throws TwitterException
	 * @throws InterruptedException
	 */
	public void startLiking(Chronometer chronometer, Twitter twitter, Search searches) throws TwitterException, InterruptedException{

		TwitterOutConsole.startRoutine("Liking Routine");

		while(true){
			try{
				int likeCounter = 0;
	        	chronometer.reset();
	        	chronometer.start();
	        	while(chronometer.currentTimeInSeconds() < this.LIKE_BREAKTIME && likeCounter < this.LIKE_UMBRAL){
			        Query query = new Query(searches.getRandomSearch()); 
			        
					TwitterOutConsole.printQuery(searches, "Liking routine");

			        QueryResult result = twitter.search(query); 
		            List<Status> tweets = result.getTweets();
		            
		            long prevID = 0;
		            String prevText = "";
		            
		            for (Status tweet : tweets) {
		            	boolean passed=false;
		            	
		            	try{
		            		if(passed = Filter.passLikeFilter(tweet) && (likeCounter <= this.LIKE_UMBRAL) && (prevID != tweet.getUser().getId() && !prevText.equals(tweet.getText()))){
		            			
			            		twitter.createFavorite(tweet.getId());  
			            		
			            		prevID = tweet.getUser().getId(); 
			            		prevText = tweet.getText();
			            		
			            		TwitterOutConsole.getTweetInfo(tweet);
			            		
			                    likeCounter++;
			                    TwitterOutConsole.printTwitterLogsln("  >|Like counter: " + likeCounter);
			            		
			            		searches.setLiked(searches.getRandomIterator()); 
			            		
		    	            	Thread.sleep(1000*this.LIKE_EVERY_SECS); //Un like cada 30 segundos	            	
		            		}
		            		else if(passed){
		            			TwitterOutConsole.printTwitterLogsln("");
		            			TwitterOutConsole.printTwitterLogsln("Passed the filter but: ");
		            			TwitterOutConsole.getTweetInfo(tweet);
		            		}
		            	}
		            	catch(TwitterException e){
		            		if(e.getErrorMessage().contains("rate limit"))
	            				Thread.sleep(1000*60*60);
		            		
		            		if(e.getStatusCode() == 403){  //Si ya le he dado a like
		            			
		            			TwitterOutConsole.printTwitterLogsln("Already liked, wait 2 secs");
		            			
		            			Thread.sleep(1000*this.ALREADYLIKEDTIME);
		            			break;
		            		}
		            	}
		            }
	        	}
	        	
	        	TwitterOutConsole.startBreak("Liking routine", this.LIKE_BREAKTIMEDURATION);

			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
			catch(NullPointerException e){
				TwitterOutConsole.printError(e.getMessage());

			}
        }
	}
	
	/**
	 * Metodo que realiza un seguimiento aleatorio de los usuarios de tweets aleatorios de una busqueda.
	 * @param chronometer Cronómetro para el control del tiempo
	 * @param twitter Twitter
	 * @param searches Busquedas de tweets para el seguimiento
	 * @param users Usuarios a los que hemos seguido
	 * @throws TwitterException 
	 * @throws InterruptedException
	 */
	public void startFollowing(Chronometer chronometer, Twitter twitter, Search searches, UsersToFollow users) throws TwitterException, InterruptedException{
		
		TwitterOutConsole.startRoutine("Following routine");

		Chronometer localChronometer = new Chronometer();
		while(true){
			try{
			
				int breaksCounter = 0;
				localChronometer.reset();
				localChronometer.start();
				
				while(localChronometer.currentTimeInSeconds() < this.FOLLOW_LARGEBREAKTIME + (this.FOLLOW_SMALLBREAKTIME_DURATION*breaksCounter)){ //Long break every 30 mins
					
					chronometer.reset();
					chronometer.start();
					
					while(chronometer.currentTimeInSeconds() < this.FOLLOW_SMALLBREAKTIME){ 
						
						Query query = new Query(searches.getRandomSearch());
						QueryResult result = twitter.search(query); //Manejar rate limit exception
						TwitterOutConsole.printQuery(searches, "Following routine");
						List<Status> tweetToFollow = result.getTweets();
						
						if(!tweetToFollow.isEmpty()){
							
							Status tweet = tweetToFollow.get((int) (tweetToFollow.size()*Math.random()));
							boolean inList = users.isInList(tweet.getId());
							
							if(Filter.passFollowFilter(tweet) && !inList){
								try{
									
									twitter.createFriendship(tweet.getUser().getId());
									
									TwitterOutConsole.startFollowingInfo(tweet);
									
									users.addNewUser(tweet.getUser().getId());
									Thread.sleep(1000*60*this.FOLLOW_EVERY_MINS);
		
								}
								catch(TwitterException e){
									
									e.printStackTrace();
									TwitterOutConsole.printError(e.getErrorMessage());
								}
							}
							else{
								
								TwitterOutConsole.printTwitterLogsln(tweet.getUser().getName() + " didnt passed filter.");
								
							}
						}
					}
					
					breaksCounter++;
					TwitterOutConsole.startBreak("Following routine", FOLLOW_SMALLBREAKTIME_DURATION);
	
				}
				
				TwitterOutConsole.startBreak("Following routine", this.FOLLOW_LARGEBREAKTIME_DURATION);
				//this.startUnfollowing(twitter, users); //Unfollows the followed users
				
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
		}
	}
	
	
	/**
	 * Metodo que deja de seguir a todos los usuarios anteriormente seguidos.
	 * @param twitter
	 * @param users
	 * @throws TwitterException
	 * @throws InterruptedException 
	 */
	public void startUnfollowing(Twitter twitter, UsersToSave users) throws TwitterException, InterruptedException{
		
		TwitterOutConsole.startRoutine("Unfollowing routine");

		while(!users.isEmpty()){
			try{
				Thread.sleep(1000*5);
				long id = users.popUser();
				TwitterOutConsole.printTwitterLogsln("  >|Unfollowing id:" + id);
				twitter.destroyFriendship(id);
				System.out.println("");
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
		}
	}
	
	
	public void startDirectMessaging(Twitter twitter, Search searches, Messages messages) throws InterruptedException{ //TODO:test
		
		TwitterOutConsole.startRoutine("DirectMessaging routine");
		
		while (true){
			try{
				
		        Query query = new Query(searches.getRandomSearch()); 
		        QueryResult result = twitter.search(query); 
				TwitterOutConsole.printQuery(searches, "DiretctMessaging routine");
	            List<Status> tweets = result.getTweets();
	            
	            for (Status tweet : tweets) {
	            	if(Filter.passDMFilter(tweet)){
	            		try{
	            			twitter.createFavorite(tweet.getId());
	            			String msg = messages.sendMessage(twitter, tweet);
	            			TwitterOutConsole.sendingMessageInfo(msg, tweet);
	            			Thread.sleep(1000*60*Messages.MESSAGE_EVERY);

	            			
	            		}
	            		catch(TwitterException e){
	            			TwitterOutConsole.printError(e.getErrorMessage());
	            			
	            		} catch (InterruptedException e) {
							e.printStackTrace();
						}
	            	}
	            }
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
		}
	}
	
	public void startGettingMails(Twitter twitter, Search searches, UsersToMail users) throws InterruptedException{ //TODO:develop
		
		TwitterOutConsole.startRoutine("GettingMails routine");
		
		while (true){
			try{
				
		        Query query = new Query(searches.getRandomSearch()); 
		        QueryResult result = twitter.search(query); 
				TwitterOutConsole.printQuery(searches, "GettingMails routine");
	            List<Status> tweets = result.getTweets();
	                      
	            for (Status tweet : tweets) {
	            	if(Filter.passGetMailFilter(tweet)){
	            		
	            		String mail = Filter.findMailInBio(tweet.getUser());
	            		if(!mail.equals("null")){
		            		TwitterOutConsole.printTwitterUserln(">|Got Mail: " + mail + " from " + tweet.getUser().getName());
		            		UsersToMail.insertNewUser(mail, tweet.getUser().getName());
		            		Thread.sleep(1000*5);
	            		}
	            		else{
	            			Thread.sleep(1000*2);
	            		}
	            		
	            	}
	            	else{
	            		Thread.sleep(1000);
	            	}
	            }
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
				
			}
		}
		
	}
}
