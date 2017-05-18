package corpusBot;

import java.util.List;


import java.util.Calendar;
import java.util.HashMap;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Clase que representa nuestro bot.
 * @author joaquinsanchiz
 *
 */
public class MyBot {
	
	/**
	 * Duracion del break en minutos.
	 */
	int SEARCH_BREAKTIMEDURATION = 1;
	/**
	 * Tiempo de espera si ya has incluido el estado.
	 */
	int ALREADYSEARCHEDTIME = 5;
	/**
	 * Parada entre cada estado.
	 */
	int SEARCH_EVERY_SECS = 1;
	
	static final long JoaquinSanchizID = 952542673;
	
	
	/**
	 * Método que comienza la búsqueda y escritura del corpus, hasta generar 1000 entradas.
	 * @param chronometer Cronómetro para el control de rateTime
	 * @param twitter
	 * @param searches	Consultas a realizar
	 * @throws InterruptedException
	 */
	public void startingCorpus(Chronometer chronometer, Twitter twitter, Search searches) throws InterruptedException{
		TwitterOutConsole.startRoutine("Corpus routine");
		HashMap<String,String> corpus = new HashMap<String,String>();
		int corpusCounter = 0;

		while(true){
			try{
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
								if(corpus.size() < 1000){
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
												Thread.sleep(1000*this.SEARCH_EVERY_SECS);
											}
											else Thread.sleep(1000*this.ALREADYSEARCHEDTIME);
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
												Thread.sleep(1000*this.SEARCH_EVERY_SECS);
											}
											else Thread.sleep(1000*this.ALREADYSEARCHEDTIME);
										}
										
									}
									else Thread.sleep(1000*this.ALREADYSEARCHEDTIME);;
								}
								else{
									twitter.sendDirectMessage(JoaquinSanchizID, "Corpus acabado");
									return;
								}
							}
						}
						catch(TwitterException e){
							if(e.getErrorMessage().contains("rate limit"))
								Thread.sleep(1000*60*60);
						}
					}
				}
	        	TwitterOutConsole.startBreak("Corpus routine", this.SEARCH_BREAKTIMEDURATION);
			}
			catch(TwitterException e){
				TwitterOutConsole.printError(e.getErrorMessage());
			}
			catch(NullPointerException e){
				TwitterOutConsole.printError("Null pointer exception");
			}
		}
	}
	
}
