package com.birto.infodexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;



public class Searcher {

    IndexSearcher indexSearcher;
    DirectoryReader indexReader;
    QueryParser queryParser;
    Analyzer analyzer;
    Map<IRecherche, Integer> rechercheDictionnaire;
    String indexDirectoryLocationString;
    
    
      //  private final SearcherManager searcherManager; //test
    

    public Searcher(String indexDirectoryLocationString) throws IOException {
      
        rechercheDictionnaire = new HashMap<IRecherche, Integer>();       
        this.indexDirectoryLocationString = indexDirectoryLocationString;
        
                                                                               //  System.out.println("DEBUGGER --indexSearcher info: " + indexSearcher.toString());
        
      //  searcherManager = new SearcherManager(indexReader, null);
               
        analyzer = new StandardAnalyzer();
        
        
    }

    
    
    public RechercheExtrant searchIndex(UsineRequête requêtePréUsinée, IndexWriter indexWriter, LocalDateTime time) throws IOException, ParseException {
              
        indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryLocationString)));                                        //System.out.println("DEBUGGER --indexReader info: " + indexReader.maxDoc());
        indexSearcher = new IndexSearcher(indexReader);

        Query queryComplete;
        IRecherche recherche;

        if (!requêtePréUsinée.getHasFilter()) {                                             // Cette rechercne ne contient pas de filtreur d'extension (devient donc une simple Automatique with QeryParser)
             queryParser = new QueryParser(Constants.CONTENTS, new StandardAnalyzer());

            try {
                queryComplete = queryParser.parse(requêtePréUsinée.getStringSearch());
            }
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Oups, Votre requête à causée une défaillance: \nMessage d'erreur: " + ex.getMessage() + "\nVotre Requête est:" + requêtePréUsinée.getStringSearch());
                System.exit(1);
                return null;
            }

            printQueryType(queryComplete);

            // FactoryPattern pourRecherche ...     //prepare la Recherche pour le dictionnaire (et logs éventuellement)   
            recherche = IRechercheFactory.créerIRecherche("simple");
            recherche.setChaineRecherche(requêtePréUsinée.getStringSearch());
            recherche.setCréeLe(time);

        }
        else {    // Check if there is a filtreur - filtreur is NULL by default...  //        System.out.println("YAY !!! Y'a un filtreur d'Extension dans la construction de la requette via UsineRecherhe");

            //need to catch ParseException ** to be implemented
            Query mainQuery = null;
            try {
                mainQuery = new QueryParser(Constants.CONTENTS, new StandardAnalyzer()).parse(requêtePréUsinée.getStringSearch());
            }

            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Oups, Votre requête à causée une défaillance: \nMessage d'erreur: " + ex.getMessage() + "\nVotre Requête est:" + requêtePréUsinée.getStringSearch());
                System.exit(1);
            }

            Query contentTypeFilterQuery = new TermQuery(new Term(Constants.CONTENT_TYPE, requêtePréUsinée.getFiltreurExtension().getExtensionInclure())); // cela va prendre la chaine a utiliser pour la recherche dans content-type

            //  doit utiliser BooleanQuery  
            Builder queryAvecFiltreBuilder = new BooleanQuery.Builder();
            queryAvecFiltreBuilder.add(mainQuery, BooleanClause.Occur.MUST);
            queryAvecFiltreBuilder.add(contentTypeFilterQuery, BooleanClause.Occur.MUST);
            queryComplete = queryAvecFiltreBuilder.build();
            //System.out.println("***watch the tostrong ont the complete queryy??****  " + queryComplete.toString());        System.out.println("\nSearching for '" + requêtePréUsinée.getStringSearch() + "' using BooleanQuery");
            printQueryType(queryComplete);

            //prepare la Recherche pour le dictionnaire (et logs éventuellement)               
            recherche = IRechercheFactory.créerIRecherche("filtrer");

            recherche.setChaineRecherche(requêtePréUsinée.getStringSearch());
            recherche.setCréeLe(LocalDateTime.now());
            if (recherche instanceof SimpleFiltrerRecherche) {       //utilise un test IF avec Cast subterfuge pour s'assurer que la méthode est bien visible.
                ((SimpleFiltrerRecherche) recherche).setFiltre(requêtePréUsinée.getFiltreurExtension());
            }
        }

        // Règle métier
        int nbreHitsDemandé = (requêtePréUsinée.nombreRésultatSauter == 20) ? 20 : requêtePréUsinée.getNombreRésultatSauter();

        TopDocs topdocs = indexSearcher.search(queryComplete, nbreHitsDemandé);
        // NICE TO HAVE  ----- > logger.info("For {}, Top Docs has {} hits; reading Lucene results", topDocs.scoreDocs.length);

        //Ajoute Recherche + ensuite toute les Hits dans l'entreé "recherche" comme des coorespondances. //
        recherche.setFinaliséLe(LocalDateTime.now());
        recherche.setDuréeExecution(recherche.getCréeLe(), recherche.getFinaliséLe());
        rechercheDictionnaire.put(recherche, Math.toIntExact(topdocs.totalHits.value));  //Ajouter au dictionnaire des recherches 
       
        //rechercheDictionnaire.forEach((key, value) -> System.out.println(key + ":" + value));        

        return new RechercheExtrant(topdocs, recherche);

    }

    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
        this.indexReader.close();
    }

        public void printQueryType(Query q) {
        System.out.println("Type of query: " + q.getClass().getSimpleName() + q.toString(Constants.CONTENT_TYPE + q.toString(Constants.CONTENTS)));
    }
        
            

}