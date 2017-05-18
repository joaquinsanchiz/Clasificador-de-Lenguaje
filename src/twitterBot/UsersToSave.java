package twitterBot;

import java.util.Stack;

/**
 * Clase contenedora de usuarios.
 * @author joaquinsanchiz
 *
 */
public class UsersToSave {
	/**
	 * Pila de usuarios seguidos
	 */
	private Stack<Long> users = new Stack<>();
	
	/**
	 * Inserta un nuevo usuario en la pila
	 * @param user Usuario a insertar.
	 */
	public void addNewUser(long user){
		if(!this.isInList(user))
			this.users.push(user);
	}
	/**
	 * Saca el ultimo usuario de la lista
	 * @param user
	 */
	public long popUser(){
		return this.users.pop();
	}
	public long getUser(int index){
		return this.users.get(index);
	}
	
	public UsersToSave(UsersToSave rhs){
		for(int i = 0; i < rhs.users.size(); i++){
			this.addNewUser(rhs.getUser(i));
		}
	}
	
	public UsersToSave(){
		
	}
	
	public boolean isInList(long user){
		if(this.users.contains(user)){
			return true;
		}
		return false;
	}
	public boolean isEmpty(){
		return this.users.isEmpty();
	}
	

}
