/**
 * @author almirfilho, thiago alencar, rodrigo costa
 * 
 */

package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import gate.*;
import gate.creole.*;
import gate.util.GateException;
import gate.util.InvalidOffsetException;

public final class EmailFinder {
	
	/* ---------------------------------------
	 * Atributos
	 ---------------------------------------*/
	
	private Corpus corpus;
	
	private SerialAnalyserController annie;
	
	private ArrayList<String> names;
	
	private ArrayList<String> emails;
	
	private DataBase db;
	
	/* ---------------------------------------
	 * Construtor
	 ---------------------------------------*/
	
	public EmailFinder( String directoryPath, Corpus corpus ){
		
		this.corpus = corpus;
		this.db = new DataBase();
		
		try {
			
			// carregando documentos no corpus
			System.out.print( "Garregando Corpus..." );
			this.corpus.populate( new URL( "file://" + directoryPath ), null, null, false );
			System.out.print( " OK!\n" );
			
			// iniciando ANNIE
			this.initAnnie();
			
		} catch( MalformedURLException e ){
			
			System.out.println( "URL incorreta." );
			
		} catch( IOException e ){
			
			System.out.println( "Nao foi possivel ler diretorio." );
			
		} catch( ResourceInstantiationException e ){
			
			System.out.println( "Ocorreu um erro." );
			
		} catch( GateException e ){
			
			System.out.println( "Ocorreu um erro ao tentar carregar Annie." );
		}
	}
	
	/* ---------------------------------------
	 * Metodos
	 ---------------------------------------*/
	
	/**
	 * Roda a aplicacao
	 */
	public void run(){
		
		try {

			// iniciando execucao do ANNIE
			System.out.print( "\n\nProcessando..." );
			this.annie.execute();
			System.out.print( " OK!\n" );
			
			// obtendo todos os documentos do corpus
			Iterator documents = corpus.iterator();
		    
		    while( documents.hasNext() ){
		    	
		    	// para cada documento, substitui os nomes e emails pelos links e gera um novo documento
		    	Document currentDocument = (Document) documents.next();
		    	this.selectNames( currentDocument, "Person" );
		    	//this.selectNames( currentDocument, "emailAddress" );
		    	this.createNewDocument( currentDocument, "/Users/almirfilho/Desktop/htmls/" );
		    }
			
		} catch( ExecutionException e ){
			
			System.out.println( "Nao foi possivel executar Annie." );
		}
	}
	
	/**
	 * Inicia ANNIE
	 */
	private void initAnnie() throws MalformedURLException, GateException {
		
		System.out.println( "Iniciando ANNIE..." );
		
		// carrega o plugin Annie
		Gate.getCreoleRegister().registerDirectories( new File( Gate.getPluginsHome(), "ANNIE" ).toURI().toURL() );
		
		// criando um controller para executar o Annie
		this.annie = (SerialAnalyserController) Factory.createResource( "gate.creole.SerialAnalyserController", Factory.newFeatureMap(), Factory.newFeatureMap(), "ANNIE" );
		
		// carregando todos os Processing Resources do Annie
		for( int i = 0; i < ANNIEConstants.PR_NAMES.length; i++ ){
			
			ProcessingResource pr = (ProcessingResource) Factory.createResource( ANNIEConstants.PR_NAMES[ i ], Factory.newFeatureMap() );
			this.annie.add( pr );
		}
		
		// setando o corpus
		this.annie.setCorpus( this.corpus );
		
		System.out.println( "ANNIE Iniciado!" );
	}
	
	/**
	 * cria um novo documento HTML com os links incluidos
	 * @param document: Documento de entrada
	 */
	private void createNewDocument( Document document, String directoryPath ){
		
		System.out.print( "\nCriando HTMLs..." );
		
		// pega conteudo do documento
		String content = document.getContent().toString();
		
		// substitui os nomes do documento por links
		for( String name : this.names )
			if( this.db.hasPerson( name ) )
				content = content.replaceAll( name, HtmlTag.a( name, this.db.getEmail( name ) ) );
		
		// substitui os emails do documento por links
//		for( String email : this.emails )
//			content = content.replaceAll( email, HtmlTag.a( email, email ) );
		
		// criando conteudo HTML
		String[] paragraphs = content.split( "\n" );
		String contentHtml = "";
		
		for( String paragraph : paragraphs )
			contentHtml += HtmlTag.p( paragraph );
		
		contentHtml = HtmlTag.html( HtmlTag.head() + HtmlTag.body( contentHtml ) );
		
		try {
			
			// criando arquivo .html
			FileWriter file = new FileWriter( directoryPath + document.getName() + ".html" );
			file.write( contentHtml );
			file.close();
			
		} catch( IOException e ){
			
			System.out.println( "Nao foi possivel criar novo documento." );
		}
		
		System.out.print( " OK!" );
	}
	
	/**
	 * Identifica e recupera os nomes e emails de um documento
	 * @param document: Documento de entrada
	 */
	private void selectNames( Document document, String type ){
		
		// recuperando AnnotationSet de type
		AnnotationSet annotationSet = document.getAnnotations().get( type );
		
        Iterator<Annotation> annotations = annotationSet.iterator();
        Annotation annotation;
        Long ini, end;
        String name;
        ArrayList<String> array = new ArrayList<String>();

        // recuperando os nomes das Annotations
        while( annotations.hasNext() ){
        	
        	annotation = (Annotation) annotations.next();
        	ini = annotation.getStartNode().getOffset();
        	end = annotation.getEndNode().getOffset();
        	
        	try {
        		
        		// adicionando o nome ao array
        		name = document.getContent().getContent( ini, end ).toString();
        		array.add( name );
        		
        	} catch( InvalidOffsetException e ){
        		
        		System.out.println( "Invalid offset!" );
        	}
        }
        
        if( type.equals( "Person" ) )
        	this.names = array;
        
        else if( type.equals( "emailAddress" ) )
        	this.emails = array;
	}

}