package twitterBot;

import java.io.*;

public class FileWriting {
	
	static final String exceptionFile = "outputFiles/exceptionFile.txt";
	static final String mailsFile = "outputFiles/mailFile.txt";
	static final String usersFile = "outputFiles/usersFile.txt";
	static final String corpusFile = "outputFiles/corpusFile.csv";


	
	public static void writeInFile(String file, String input, String userName){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
        	
            fichero = new FileWriter(file, true);
            pw = new PrintWriter(fichero);
            if(!userName.equals("null"))
            	pw.println("\"" + input + "\"," + userName);
            else
            	pw.println(input);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	public static void writeException(String exception){
		FileWriting.writeInFile(FileWriting.exceptionFile, exception, "null");
	}
	public static void writeMail(String mail, String userName){
		FileWriting.writeInFile(mailsFile, mail, userName);
	}
	public static void writeUser(long userID){
		FileWriting.writeInFile(usersFile, Long.toString(userID), "null");
	}
	public static void writeCorpusLine(String tweet, String language){
		FileWriting.writeInFile(corpusFile, tweet, language);
	}
}
