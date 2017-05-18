package twitterBot;

public class UsersToMail{
	
	public static void insertNewUser(String mail, String userName){
		FileWriting.writeMail(mail, userName);
	}

}
