package com.birto.infodexer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.lucene.queryparser.classic.ParseException;


public class SessionControlleur {

    private Session session;
    
    public static void main(String[] args) throws URISyntaxException, ParseException, IOException {

        SessionControlleur sessionControlleur = new SessionControlleur();
        sessionControlleur.session = new Session(LocalDateTime.now(), System.getProperty("user.name"), System.getProperty("os.name"));             //Prépare la Session avec les paramètres clés...      // DEBUG**  System.out.println(s.getDébutSession() + " " + s.getNomUsager() + " " + s.getOS());

        //Premier lancement de l'application? 
        if (sessionControlleur.session.isEstPremierLancement()) {
            final JOptionPane pane = new JOptionPane();
            Timer t = new Timer(4000, (ActionEvent e) -> {
                JOptionPane.getRootFrame().dispose();
            });
            t.start();
            JOptionPane.showMessageDialog(pane, "Premier Lancement de l'application... Nous allons copier des fichiers de bases et les indexer pour vous.\nVeuillez patientez...", "Info-Dexer (Copie des fichiers)", 1);
            t.stop();
        }

        /// INDEXING COMMENCE ICI
        TikaIndexer tikaIndexeur = new TikaIndexer(sessionControlleur.session.getPathToIndex().toString(), true);      // Indexeur Init

        if (!tikaIndexeur.checkIndexExist()) {                                                       // Vérifier si index existe ou non.

            try {/*INDEX.CREATE*/

                tikaIndexeur.createIndex(sessionControlleur.session.getPathToActiveDocs().toString());    //debug   System.out.println("session.getpathActiveDocs().toString() = " + session.getpathActiveDocs().toString());
                tikaIndexeur.commit();
            }

            catch (Exception ex) {
                Logger.getLogger(SessionControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }

            //  CheckIndex checkInd = new CheckIndex( tikaIndexeur.getIndexDirectory(), tikaIndexeur.getWriter().IndexWriter.WRITE_LOCK_NAME ) ;
            //  tikaIndexeur.getWriter().close(); 
        }
        else {
            System.out.println("Index est déjà en place - réindexation NON requise");
        }
//*************************************  /// INDEXING FINIT ICI ////**********************************************************        
    
        //Searcher instantiation 
        Searcher moteurRecherche = new Searcher(sessionControlleur.session.getPathToIndex().toString());

        
// ***************************************************************** GUI ***********************************************   
        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            try {

                FrameControlleur fControl = new FrameControlleur(tikaIndexeur, new FrameCentral(), moteurRecherche, sessionControlleur);
                fControl.initView();
                fControl.initControlleur();

            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getMessage());
            }
        });
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
