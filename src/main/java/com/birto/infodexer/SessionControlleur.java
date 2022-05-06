package com.birto.infodexer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.queryparser.classic.ParseException;



// J"aai changer Session intiaittion dans le métode main pour une session quiest partie 
//de l'instance de sessionCOntrolleur et je vais ajouter un référence dans le constructeut de fRameCotroleur desessionsControllerui... 


public class SessionControlleur {
    
    private Session session;

    static Boolean readyToOperate = false;
    // FrameCentral view = new FrameCentral();
  

    
    
    public static void main(String[] args) throws URISyntaxException, ParseException, IOException {

        SessionControlleur sessionControlleur = new SessionControlleur();
        sessionControlleur.session = new Session(LocalDateTime.now(), System.getProperty("user.name"), System.getProperty("os.name"));             //Prépare la Session avec les paramètres clés...      // DEBUG**  System.out.println(s.getDébutSession() + " " + s.getNomUsager() + " " + s.getOS());

        //ActiveDocsDirectory est-il vide? Si oui, on copie baseDocsInit dedans
        try {            
            
            if (sessionControlleur.session.isActiveDocsEmpty()) {
                copierBaseDocstoActiveDocs(sessionControlleur.session.getPathToBaseDocs(), sessionControlleur.session.getpathActiveDocs());

                final JOptionPane pane = new JOptionPane();
                Timer t = new Timer(4000, (ActionEvent e) -> {                    
                    JOptionPane.getRootFrame().dispose();
                });
                t.start();
                JOptionPane.showMessageDialog(pane, "Premier Lancement de l'application... Nous allons copier des fichiers de bases et les indexer pour vous.\nVeuillez patientez...", "Info-Dexer (Copie des fichiers)", 1);
                t.stop();
            }
        
        }
        catch (IOException ex) {
            Logger.getLogger(SessionControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        /// INDEXING COMMENCE ICI
        TikaIndexer tikaIndexeur = new TikaIndexer(sessionControlleur.session.getPathToIndex().toString(), false);      // Indexeur Init

        if (!tikaIndexeur.checkIndexExist()) {                                                       // Vérifier si index existe ou non.

            try {
                /*INDEX.CREATE*/ tikaIndexeur.createIndex(sessionControlleur.session.getpathActiveDocs().toString());    //debug   System.out.println("session.getpathActiveDocs().toString() = " + session.getpathActiveDocs().toString());
            }
            catch (Exception ex) {
                Logger.getLogger(SessionControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
                                                                                                       //debug   System.out.println("----------------------------------------------------------------------------------------------");        System.out.println("Nombre de document dans l'index: " + tikaIndexeur.getWriter().getDocStats().numDocs);        System.out.println("----------------------------------------------------------------------------------------------");
                                                                                                        
           
           
   //            CheckIndex checkInd = new CheckIndex( tikaIndexeur.getIndexDirectory(), tikaIndexeur.getWriter().IndexWriter.WRITE_LOCK_NAME ) ;
          
   //          tikaIndexeur.getWriter().close();                                                                                           //debug    System.out.println("----------------------------------------------------------------------------------------------\\\r\\nÉtat de l'index actuel est: " + tikaIndexeur.checkIndexStatus(checkInd.checkIndex()));  System.out.println("----------------------------------------------------------------------------------------------");         System.out.println("Index créé en : " + (tikaIndexeur.finTempsIndexation - tikaIndexeur.débutTempsIndexation) + " milliseconde.");
          
        }
        else {
            System.out.println("Index est déjà en place - réindexation NON requise");
        }
        
//*************************************  /// INDEXING FINIT ICI ////**********************************************************        


          //Searcher instantiation 
        Searcher moteurRecherche = new Searcher(sessionControlleur.session.getPathToIndex().toString());
        
        //Safety
        SessionControlleur.readyToOperate = true; // To be fully implemented... 
        

// ***************************************************************** GUI ***********************************************   
        /* Create and display the form */       
        EventQueue.invokeLater(() -> {
            try {
            
            FrameControlleur fControl = new FrameControlleur(tikaIndexeur, new FrameCentral(), moteurRecherche,sessionControlleur);                           
            fControl.initView();
            fControl.initControlleur();   
            }
            catch (IOException e) {
                System.out.println(e.getMessage());               
            }
        });
           }

    
    //Il s'agit d'une méthode permettant de copier les documents (fichiers clés pour l'application) lors du lancement initial de l'application - DUMP all files from baseDocs into ActiveDocs directory. 
    public static void copierBaseDocstoActiveDocs(Path source, Path dest) {

        //at runtime if if true that is empty (ActiveDocs)...then copy all the stuff into baseDocs
        try {
            FileUtils.copyDirectory(source.toFile(), dest.toFile());
            System.out.println("The copying of baseDocs into ActiveSDocs is being atempted");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
