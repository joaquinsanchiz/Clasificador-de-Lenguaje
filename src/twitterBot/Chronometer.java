package twitterBot;
public final class Chronometer{
    private long begin, end;
 
    public void start(){
        begin = System.currentTimeMillis();
    }
    
    public double currentTimeInSeconds(){
    	return (System.currentTimeMillis() - begin) / 1000.0;
    }
 
    public void stop(){
        end = System.currentTimeMillis();
    }
    
    public void reset(){
    	this.stop();
    	this.begin = 0;
    	this.end = 0;
    }
 
    public long getTime() {
        return end-begin;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public double getSeconds() {
        return (end - begin) / 1000.0;
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
}