package com.birto.infodexer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CheckIndex;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Office;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class TikaIndexer {

    private IndexWriterConfig config;
    private IndexWriter writer;
    private boolean DEBUG = false;
    private Directory indexDirectory;
    private static final Set<String> textualMetadataFields = new HashSet<String>();
    public long débutTempsIndexation;
    public long finTempsIndexation;
              

    static {
        //Propriétés de références en matière de métadonnées - Si ceux-ci sont trouvés lors de l'analyser des fichiers à indexer, ils sont ajoutés à l'index..
        textualMetadataFields.add(TikaCoreProperties.TITLE.getName());
        textualMetadataFields.add(TikaCoreProperties.MODIFIED.getName());
        textualMetadataFields.add(TikaCoreProperties.LANGUAGE.getName());
        textualMetadataFields.add(TikaCoreProperties.FORMAT.getName());
        textualMetadataFields.add(TikaCoreProperties.DESCRIPTION.getName());
        textualMetadataFields.add(TikaCoreProperties.CONTENT_TYPE_HINT.getName());
        textualMetadataFields.add(Office.KEYWORDS.getName());

    }

    public TikaIndexer(String indexDirectoryLocationString, boolean activedebugging) throws IOException {
        this.DEBUG = activedebugging;

        //Répertoire de l'index (assignation) 
        indexDirectory = FSDirectory.open(Paths.get(indexDirectoryLocationString));
        
        //Créer l'indexeur        // could be nice to use a FrenchAnalyser or with FrenchStop Words
        config = new IndexWriterConfig(new StandardAnalyzer()).setOpenMode(OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(indexDirectory, config);
        //Testing the printing for the textualMetadataFields debug avec ==> for (String p : textualMetadataFields) { System.out.println("Testing the textualMetadataFields in constructor " + p); }
    }

    public IndexWriter.DocStats createIndex(String dataDir) throws Exception {
        
        débutTempsIndexation = new Date().getTime();
        
       File[] files = new File(dataDir).listFiles();      //get all files in the data directory
       for (File f : files) {
            //System.out.println("CreateINdex Methos a file got listed !!!");
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()) {
                indexFile(f); //Calls the indexing here (see below)                
            }
        }
        finTempsIndexation = new Date().getTime();
        return writer.getDocStats();

    }

    public void indexFile(File file) throws IOException, Exception {
      //  System.out.println("Attempting to Index " + file.getCanonicalPath());
        Document document = getDocument(file);
     //   System.out.println(writer.getDocStats());
        writer.addDocument(document);
    }

    public Document getDocument(File f) throws Exception {
        BodyContentHandler handler = new BodyContentHandler(-1);  //Parameters: writeLimit - maximum number of characters to include in the string, or -1 to disable the write limit
        Metadata metadata = new Metadata();
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, f.getName());
        AutoDetectParser parser = new AutoDetectParser();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        parser.parse(bis, handler, metadata, new ParseContext());  //debug     System.out.println(bis.toString());  //       System.out.println("Handler toString = (empty?)" + handler.toString());        // Get the Content : NoT Stored BUT tokenized (analyzed)
            
        Document doc = new Document();
        doc.add(new TextField(Constants.CONTENTS, handler.toString(), Field.Store.NO));

        // Get the Metadata Element that matched the Set in textualMetadataFields : Not Stored BUT tokenized (analyzed)
        for (String metadataName : metadata.names()) {
            String value = metadata.get(metadataName);

            System.out.println("--Current Value= " + value + "Current MetadataName : " + metadataName);

            if (TikaIndexer.textualMetadataFields.contains(metadataName)) {
                doc.add(new TextField(Constants.CONTENTS, value, Field.Store.NO));
            }
            
            if (metadataName.equals(Constants.CONTENT_TYPE)){  
                
                doc.add(new StringField(Constants.CONTENT_TYPE, value, Field.Store.YES));
                   System.out.println("value being added = " + value);
                   System.out.println("metadataname is = " + metadataName);
            }
               doc.add(new StoredField(metadataName, value));               

            if (DEBUG) { System.out.println(" " + metadataName + ": " + value);  }
        }              
        
        doc.add(new StringField(Constants.FILE_PATH, f.getCanonicalPath(), Field.Store.YES));           
        doc.add(new StringField(Constants.FILE_NAME, f.getName(), Field.Store.YES));
        
        if (DEBUG) {   System.out.println("adding on the filename StringField the file_Name Field = "+ f.getName() );        System.out.println(doc.get(Constants.CONTENT_TYPE)); }
                
        return doc;
    }

    public void commit() throws CorruptIndexException, IOException {
        writer.commit();
    }

    public IndexWriterConfig getConfig() {
        return config;
    }

    public IndexWriter getWriter() {
        return writer;
    }

    public Directory getIndexDirectory() {
        return indexDirectory;
    }

    public void  wakeIndexWriter () throws IOException {
        if (writer.isOpen() != true ) {
            config = new IndexWriterConfig(new StandardAnalyzer()).setOpenMode(OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(indexDirectory, config);
        } 
        
    }

    public String checkIndexStatus(CheckIndex.Status status) {
        String msg;
        if (status == null) {
            msg = "?";
        }
        else if (status.clean) {
            msg = "OK";
        }
        else if (status.toolOutOfDate) {
            msg = "ERROR: Can't check - tool out-of-date";
        }
        else {
            StringBuilder sb = new StringBuilder("BAD:");
            if (status.missingSegments) {
                sb.append(" Missing segments.");
            }
            if (status.numBadSegments > 0) {
                sb.append(" numBadSegments=");
                sb.append(status.numBadSegments);
            }
            if (status.totLoseDocCount > 0) {
                sb.append(" totLoseDocCount=");
                sb.append(status.totLoseDocCount);
            }
            msg = sb.toString();
        }
        return msg;
    }

    public boolean checkIndexExist() throws IOException {
        boolean isExistantIndex = DirectoryReader.indexExists(indexDirectory);        
        return isExistantIndex;
    }
    
    
    
    
    
    
}
