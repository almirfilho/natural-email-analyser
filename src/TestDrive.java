import java.io.File;
import application.EmailFinder;
import gate.Gate;
import gate.Factory;
import gate.util.GateException;

public class TestDrive {
	
	public static void main(String[] args) {

		// sentando diretorios e arquivos do GATE
		Gate.setGateHome( new File("/Applications/GATE-5.2.1/bin") );
		Gate.setPluginsHome( new File("/Applications/GATE-5.2.1/plugins") );
		Gate.setSiteConfigFile( new File("/Applications/GATE-5.2.1/gate.xml") );
		
		try{
			
			// iniciando GATE e a aplicacao
			System.out.println( "Iniciando GATE...\n" );
			Gate.init();
			System.out.println( "\nGATE Iniciado!\n" );
			EmailFinder app = new EmailFinder( "/Users/almirfilho/Desktop/documentos", Factory.newCorpus( "corpus" ) );
			app.run();
			
		} catch( GateException e ){
			
			System.out.println( "Nao foi possivel iniciar o Gate." );
			
		}
	}

}
