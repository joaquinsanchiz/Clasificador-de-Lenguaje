package twitterBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UsersToMessage extends UsersToSave{
	
	public UsersToMessage(){
		super();
		this.initUsers();
	}
	
	public void initUsers(){
		File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (FileWriting.mailsFile);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null){
	            long userID = Long.parseLong(linea);
	            this.addNewUser(userID);
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	}
	public void addNewUser(long userID){
		
		this.addNewUser(userID);
		FileWriting.writeUser(userID);
		
	}

}
