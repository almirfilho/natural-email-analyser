/**
 * Esta classe gera conteudo HTML (tags)
 * @author almirfilho, thiago alencar, rodrigo costa
 */

package application;

public final class HtmlTag {

	/* ---------------------------------------
	 * Metodos
	 ---------------------------------------*/
	
	/**
	 * @param str: String de entrada
	 * @return String: tag <p> contendo str
	 */
	public static final String p( String str ){
		
		if( !str.equalsIgnoreCase("") )
			return "<p>" + str + "</p>\n";
		else
			return "";
	}
	
	/**
	 * @param str: String de entrada
	 * @param email: String com email
	 * @return String: tag <a> contendo str linkando para email
	 */
	public static final String a( String str, String email ){
		
		return "<a href=\"mailto:" + email + "\">" + str + "</a>";
	}
	
	/**
	 * @param str: String de entrada
	 * @return String: tag <html> contendo str
	 */
	public static final String html( String str ){
		
		return	"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" + 
				str + 
				"\n</html>";
	}
	
	/**
	 * @param str: String de entrada
	 * @return String: tag <body> contendo str
	 */
	public static final String body( String str ){
		
		return "<body>\n<div>\n" + str + "\n</div>\n</body>";
	}
	
	/**
	 * @return String: tag <head> contendo titulo e estilosda pagina
	 */
	public static final String head(){
		
		return	"<head>\n" +
				"	<title>Emails</title>\n" +
				"	<style type=\"text/css\">\n" +
				"		body{ font-family: Helvetica, Arial, sans-serif; }\n" +
				"		div{ width: 600px; margin: auto; }" +
				"		a:hover{ background: #333; color: #fff; }" +
				"	</style>\n" +
				"</head>\n";
	}
	
}