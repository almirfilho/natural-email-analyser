/**
 * Esta classe simula um banco de dados que contem as informacoes (emails) dos funcionarios
 * @author almirfilho, thiago alencar, rodrigo costa
 */

package application;

import java.util.Hashtable;

public final class DataBase {

	/* ---------------------------------------
	 * Atributos
	 ---------------------------------------*/
	
	private Hashtable<String, String> table;
	
	/* ---------------------------------------
	 * Construtor
	 ---------------------------------------*/
	
	public DataBase(){
		
		// populando a tabela
		this.table = new Hashtable<String, String>();
		this.table.put( "Jake", "jake@mail.com" );
		this.table.put( "Paul McCartney", "paul@mail.com" );
		this.table.put( "John Lennon", "lennon@mail.com" );
		this.table.put( "Ringo Starr", "ringo@mail.com" );
		this.table.put( "George Harrison", "harrison@mail.com" );
		this.table.put( "Geroge Martin", "georgemartin@mail.com" );
		this.table.put( "Pete Best", "pete@mail.com" );
		this.table.put( "Brian Epstein", "brian@gmail.com" );
		this.table.put( "Buddy Holly", "buddy@mail.com" );
		this.table.put( "Allan Williams", "allan@mail.com" );
		this.table.put( "Philip Norman", "norman@mail.com" );
//		this.table.put( "", "" );
	}
	
	/* ---------------------------------------
	 * Metodos
	 ---------------------------------------*/
	
	/**
	 * @param name: nome do funcionario
	 * @return boolean: se funcionario esta presente na tabela
	 */
	public boolean hasPerson( String name ){
		
		return this.table.containsKey( name );
	}
	
	/**
	 * @param name: nome do funcionario
	 * @return String: email do funcionario
	 */
	public String getEmail( String name ){
		
		return this.table.get( name );
	}
	
}