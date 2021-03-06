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

    
    
    public RechercheExtrant searchIndex(UsineRequ??te requ??tePr??Usin??e, IndexWriter indexWriter, LocalDateTime time) throws IOException, ParseException {
              
        indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryLocationString)));                                        //System.out.println("DEBUGGER --indexReader info: " + indexReader.maxDoc());
        indexSearcher = new IndexSearcher(indexReader);

        Query queryComplete;
        IRecherche recherche;

        if (!requ??tePr??Usin??e.getHasFilter()) {                                             // Cette rechercne ne contient pas de filtreur d'extension (devient donc une simple Automatique with QeryParser)
             queryParser = new QueryParser(Constants.CONTENTS, new StandardAnalyzer());

            try {
                queryComplete = queryParser.parse(requ??tePr??Usin??e.getStringSearch());
            }
            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Oups, Votre requ??te ?? caus??e une d??faillance: \nMessage d'erreur: " + ex.getMessage() + "\nVotre Requ??te est:" + requ??tePr??Usin??e.getStringSearch());
                System.exit(1);
                return null;
            }

            printQueryType(queryComplete);

            // FactoryPattern pourRecherche ...     //prepare la Recherche pour le dictionnaire (et logs ??ventuellement)   
            recherche = IRechercheFactory.cr??erIRecherche("simple");
            recherche.setChaineRecherche(requ??tePr??Usin??e.getStringSearch());
            recherche.setCr??eLe(time);

        }
        else {    // Check if there is a filtreur - filtreur is NULL by default...  //        System.out.println("YAY !!! Y'a un filtreur d'Extension dans la construction de la requette via UsineRecherhe");

            //need to catch ParseException ** to be implemented
            Query mainQuery = null;
            try {
                mainQuery = new QueryParser(Constants.CONTENTS, new StandardAnalyzer()).parse(requ??tePr??Usin??e.getStringSearch());
            }

            catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Oups, Votre requ??te ?? caus??e une d??faillance: \nMessage d'erreur: " + ex.getMessage() + "\nVotre Requ??te est:" + requ??tePr??Usin??e.getStringSearch());
                System.exit(1);
            }

            Query contentTypeFilterQuery = new TermQuery(new Term(Constants.CONTENT_TYPE, requ??tePr??Usin??e.getFiltreurExtension().getExtensionInclure())); // cela va prendre la chaine a utiliser pour la recherche dans content-type

            //  doit utiliser BooleanQuery  
            Builder queryAvecFiltreBuilder = new BooleanQuery.Builder();
            queryAvecFiltreBuilder.add(mainQuery, BooleanClause.Occur.MUST);
            queryAvecFiltreBuilder.add(contentTypeFilterQuery, BooleanClause.Occur.MUST);
            queryComplete = queryAvecFiltreBuilder.build();
            //System.out.println("***watch the tostrong ont the complete queryy??****  " + queryComplete.toString());        System.out.println("\nSearching for '" + requ??tePr??Usin??e.getStringSearch() + "' using BooleanQuery");
            printQueryType(queryComplete);

            //prepare la Recherche pour le dictionnaire (et logs ??ventuellement)               
            recherche = IRechercheFactory.cr??erIRecherche("filtrer");

            recherche.setChaineRecherche(requ??tePr??Usin??e.getStringSearch());
            recherche.setCr??eLe(LocalDateTime.now());
            if (recherche instanceof SimpleFiltrerRecherche) {       //utilise un test IF avec Cast subterfuge pour s'assurer que la m??thode est bien visible.
                ((SimpleFiltrerRecherche) recherche).setFiltre(requ??tePr??Usin??e.getFiltreurExtension());
            }
        }

        // R??gle m??tier
        int nbreHitsDemand?? = (requ??tePr??Usin??e.nombreR??sultatSauter == 20) ? 20 : requ??tePr??Usin??e.getNombreR??sultatSauter();

        TopDocs topdocs = indexSearcher.search(queryComplete, nbreHitsDemand??);
        // NICE TO HAVE  ----- > logger.info("For {}, Top Docs has {} hits; reading Lucene results", topDocs.scoreDocs.length);

        //Ajoute Recherche + ensuite toute les Hits dans l'entre?? "recherche" comme des coorespondances. //
        recherche.setFinalis??Le(LocalDateTime.now());
        recherche.setDur??eExecution(recherche.getCr??eLe(), recherche.getFinalis??Le());
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