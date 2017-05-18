
package twitterBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene todos los patrones de busqueda que nuestro bot analizará. Será utilizado por el bot para obtener los Status.
 * @author joaquinsanchiz
 *
 */
public class Search {
	
	private List<String> searches = new ArrayList<>(); //Busquedas  //TODO:Implementar en estático
	private List<Integer> liked = new ArrayList<>(); //Numero de likes por busqueda
	int randomIterator = 0;
	
	public Search(){
		/*
		 * 
		 */
	}
	
	public Search(Search rhs){
		for (int i = 0; i < rhs.searches.size(); i++){
			this.addNewSearch(rhs.getSearch(i));
		}
	}
	
	/**
	 * Constructor a partir de cadena
	 * @param newSearch
	 */
	public Search(String newSearch){
		this();
		this.addNewSearch(newSearch);
	}
	
	/**
	 * Constructor a partir de array de listas
	 * @param searches
	 */
	public Search(ArrayList<String> searches){
		this();
		this.addArrayOfSearches(searches);
	}
	
	/**
	 * Constructor a partir de fichero
	 * @param file fichero de entrada a capturar
	 * @param flag
	 */
	public Search(String file, boolean flag){
		this();
		this.addFromFile(file);
	}
	/**
	 * Devuelve el iterador aleatorio
	 * @return iterador aleatorio
	 * @see getRandomSearch()
	 */
	public int getRandomIterator(){
		return this.randomIterator;
	}
	
	/**
	 * Devuelve el numero de likes para dicha busqueda
	 * @param index Busqueda a comprobar
	 * @return Numero de likes
	 */
	public int getLikes (int index){
		return liked.get(index);
	}
	
	public List<Integer> getLiked(){
		return this.liked;
	}
	
	/**
	 * Aumenta en una cantidad los likes para dicha busqueda
	 * @param index
	 */
	public void setLiked(int index){
		this.liked.set(index, this.getLikes(index) + 1);
	}
	
	/**
	 * Obtiene la busqueda del vector de busquedas
	 * @param index Index para el vector
	 * @return Busqueda
	 */
	public String getSearch(int index){
		return searches.get(index);
	}
	/**
	 * Devuelve una busqueda aleatoria dentro del vector de busquedas
	 * @return Busqueda aleatoria
	 */
	public String getRandomSearch(){
		this.randomIterator = (int) (searches.size()*Math.random());
		return searches.get(this.getRandomIterator());
	}
	
	public List<String> getSearches(){
		return this.searches;
	}
	
	/**
	 * Añade una nueva busqueda al vector de busquedas
	 * @param search Busqueda
	 */
	public void addNewSearch(String search){
		
		this.getSearches().add(search);
		this.getLiked().add(0);
	}
	/**
	 * Añade un array de busquedas a nuestro vector de busquedas
	 * @param searches Vector de busquedas a importar
	 */
	public void addArrayOfSearches(ArrayList<String> searches){
		for (int i = 0; i < searches.size(); i++){
			this.addNewSearch(searches.get(i));
		}
	}
	
	/**
	 * Añade busquedas desde fichero
	 * @param file Fichero con busquedas separadas por espacios TODO:SIN COMPROBAR (TENER EN CUENTA LA SYNTAX DE BUSQUEDA)
	 */
	public void addFromFile(String file){
		  File archive = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	    	 archive = new File(file);
	         fr = new FileReader(archive);
	         br = new BufferedReader(fr);

	         String line = null;
	         while((line = br.readLine()) != null){
	            String [] searches = line.split("\\s+");
	            for(int i = 0; i < searches.length; i++){
	            	this.addNewSearch(searches[i]);
	            }
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	}

	@Override
	public String toString() {
		String searchToString = "";
		for (int i = 0; i < this.getSearches().size(); i++){
			searchToString += this.getSearch(i) + " [" + this.getLikes(i) + "], "; 
		}
		return searchToString;
	}
	

}
