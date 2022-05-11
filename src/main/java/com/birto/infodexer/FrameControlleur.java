package com.birto.infodexer;
//ref:: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/misc/ModalityDemoProject/src/misc/ModalityDemo.java

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;


public class FrameControlleur {

    private TikaIndexer indexer;
    FrameCentral view;
    Searcher moteurRecherche;
    SessionControlleur sessionControlleur;        
    static Boolean listIsEmpty = true;   
    DefaultCaret caret; 
    File destActiveDocs; 


    public FrameControlleur(TikaIndexer indexer, FrameCentral frameCentral, Searcher moteurRecherche,  SessionControlleur sessionControlleur) {
        this.indexer = indexer;
        this.view = frameCentral;
        this.moteurRecherche = moteurRecherche;
        this.sessionControlleur = sessionControlleur;
        this.destActiveDocs = new File(sessionControlleur.getSession().getPathToActiveDocs().toString());
    }
    

    public void initView() {
        // set the Jlist value to the contentType filter - dynamically       
        DefaultListModel<String> listeExtensionType = new DefaultListModel<>();
        listeExtensionType.addAll(Util.fichierExtension);
        view.getContentTypelst().setModel(listeExtensionType);

        view.getContentTypelst().setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

    }

    public void initControlleur() {

        //barre de recherche Listener pour SetEnabled(true or false)
        enableRechercheBtnDocumentListener();   // Visibilité dynamique du button "Rechercher" en fonction du contenu du TextArea

        // BOUTON RECHERCHER
        view.getLancerRecherchebtn().addActionListener((ActionEvent e) -> {

            try {
                revertEmptyResultPanel();
                swapCardJpanelToResults(prepCorrespondanceJPanelGenerator(moteurRecherche.searchIndex(prépareRechercheSéquence(), indexer.getWriter(), LocalDateTime.now(ZoneId.systemDefault())
                )));

            }
            catch (IOException | ParseException ex) {
                Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Label permettant l'accàs aux options avancées de recherche (filtrage par extension avec JList)
        view.getOptionsRecherchelbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                flipIsVisiblePanel(view.getContentTypeFiltreurPanelpnl());
            }
        });

        // ajout d'un listener qui cache le JList pour filtrer (iltrage par extension avec JList))...
        view.getCacherExtensionFiltrebtn().addActionListener(e -> view.getContentTypeFiltreurPanelpnl().setVisible(false));

        //JList 
        view.getContentTypelst().getSelectionModel().addListSelectionListener(new SharedListSelectionHandler()); // can be removed???? <-to test???

        //Hamburger Menu 
        view.getHamburgerMenubtn().addActionListener(e -> flipMenuFichierButtonsVisibility());

        //Ajouter Fichier bouton | Menu Hambrger
        view.getAjouterFichierbtn().addActionListener(e -> {
            try {
                fileIndexOperation(Constants.ActionFichier.Ajouter);
            }
            catch (InterruptedException | IOException ex) {
                Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Retirer Fichier bouton | Menu Hambrger
        view.getEnleverFichierbtn().addActionListener(e -> {
            try {
                fileIndexOperation(Constants.ActionFichier.Retirer);
            }
            catch (InterruptedException | IOException ex) {
                Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Button Réinitialiser le formulaire....
        view.getRéinitialiserFormbtn().addActionListener(e -> clearAll(view));

    }

    public File choisirFichier(Constants.ActionFichier actionSurFichier) { //EVent must be passed here? 

        JFileChooser jfc;
        int returnValue;

        switch (actionSurFichier) {

            case Ajouter:

                jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choisir un fichier à " + actionSurFichier.toString() + " de l'index");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                returnValue = jfc.showSaveDialog(view);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        System.out.println("Vous avez sélectionner : " + jfc.getSelectedFile());
                    }
                }
                break;

            case Retirer:

                //Il s'agit de forcer l'usager a choisir un fichier au sein du répertoire ActiveDocs et non à travers son FileSystem en entier
                FileSystemView fsv = new SingleRootFileSystemView(destActiveDocs);

                jfc = new JFileChooser(fsv);
                jfc.setDialogTitle("Choisir un fichier à " + actionSurFichier.toString() + " de l'index");
                jfc.setDialogType(JFileChooser.OPEN_DIALOG);
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                returnValue = jfc.showDialog(view, "Sélection");

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isFile()) {
                        System.out.println("Vous avez sélectionner : " + jfc.getSelectedFile());
                    }
                }
                break;

            default:
                JOptionPane.showMessageDialog(view, "Le fichier sélectionné n'est pas valide ou ne réside pas dans le répertoire des documents actifs de l'index.\nChemin vers ActiveDocs: " + destActiveDocs);
                return null;
        }

        return jfc.getSelectedFile();

    }

    public FileAction fileIndexOperation(Constants.ActionFichier action) throws InterruptedException, IOException {
        //Obtenir le fichier frace a une méthode dédiée (ChoisirFichier) 
        File file = choisirFichier(action);
        FileAction fileAction = null;

        if (file != null) {                                             //validation de non nullité
            fileAction = new FileAction(action, file.getName());
            getIndexer().wakeIndexWriter(); //reveille l'indexWriter

            System.out.println(file.getName());

            // {debug & test} get count of files being indexed? 
            int preIndexCountDocs = (int) getIndexer().getWriter().getDocStats().numDocs;
            int postIndexCountDocs;

            //voulez tjrs procédez avec ce fichier? 
            int reply = JOptionPane.showConfirmDialog(view, "Voulez-vous vraiment " + action + " le fichier suivant à l'index?\n Nom du fichier: " + fileAction.getNomFichier(), action + " FICHIER", JOptionPane.YES_NO_OPTION);

            // Oui Je le veux... peu importe ce que cela va comprendre dans le futur  ~:|-
            if (reply == JOptionPane.YES_OPTION) {

                switch (action) {

                    case Ajouter:  //************************************AJOUTER************************************

                        //Copier le nouveau fichier vers Active docs                      
                        try {
                            FileUtils.copyFileToDirectory(file, destActiveDocs);

                        }
                        catch (FileNotFoundException e) {
                            System.out.println("File not found: " + e.getMessage());
                        }
                        catch (IOException e) { // catch all IOExceptions not handled by previous catch blocks
                            System.out.println("General I/O exception: " + e.getMessage());
                        }

                        /*Action d'indexation du nouveau fichier  */ System.out.println("preIndexCountDocs: " + preIndexCountDocs); //Debug only

                        try {

                            System.out.println("--------------------------------------------------------------------------------------------- before FileUtils.getFile(destActiveDocs, file.getName()  ");
                            File nouvelleEntrée = FileUtils.getFile(destActiveDocs, file.getName());
                            System.out.println("--------------------------------------------------------------------------------------------- after FileUtils.getFile(destActiveDocs, file.getName()  " + destActiveDocs + " filename: " + file.getName());

                            System.out.println("Debugging nouvelleENtrée to ensure we get the right candidate: " + nouvelleEntrée.getPath() + nouvelleEntrée.getParent());

                            getIndexer().indexFile(nouvelleEntrée);

                        }
                        catch (Exception ex) {
                            Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        //{debug & test} Post count
                        postIndexCountDocs = (int) getIndexer().getWriter().getDocStats().numDocs;
                        System.out.println("postIndexCountDocs: devrait être [+1] " + postIndexCountDocs);

                        //Vérifier si incrémentation est faite? 
                        if (preIndexCountDocs != postIndexCountDocs /* a way to validate after the indexation that the file is PART OF THE INDEX would be even better" */) {

                            /*Fournir RetroAction via JOptionPane */
                            JOptionPane.showMessageDialog(
                                  null, "Fichier traité avec succès.\nNomd fu fichier: " + fileAction.getNomFichier() + "\nAction: " + fileAction.getTypeAction().toString(),
                                  action + " fichier!\nSUCCÈS.",
                                  JOptionPane.WARNING_MESSAGE
                            );
                            //Mettre le status de l'action comme étant un Succès
                            fileAction.setEstSuccès(true);
                        }

                        break;
                    // Fin de l'ajout d'un fichier -- Case *******************                    // Fin de l'ajout d'un fichier -- Case *******************

                    case Retirer: //************************************Retirer************************************

                        System.out.println("preCountDocs: devrait être [-1] " + preIndexCountDocs);
                        System.out.println("Le nom du fichier que nous allons effacer est Nom {getName}: " + file.getName());

                        // Retirer le document de dans Active Docs                         
                        try {
                            FileUtils.forceDelete(file);
                        }
                        catch (FileNotFoundException e) {
                            System.out.println("File not found: " + e.getMessage());
                        }
                        catch (IOException e) { // catch all IOExceptions not handled by previous catch blocks
                            System.out.println("General I/O exception: " + e.getMessage());
                        }

                        //remove from index: 
                        indexer.getWriter().deleteDocuments(new Term(Constants.FILE_NAME, file.getName()));
                        commitOndemand();

                        //{debug & test} Post count
                        postIndexCountDocs = (int) getIndexer().getWriter().getDocStats().numDocs;
                        System.out.println("postIndexCountDocs: devrait être [-1] " + postIndexCountDocs);

                        if (preIndexCountDocs != postIndexCountDocs /* Better implementation could be looked into */) {

                            /*Fournir RetroAction via JOptionPane */
                            JOptionPane.showMessageDialog(
                                  null, "Fichier traité avec succès.\nNom du fichier: " + fileAction.getNomFichier() + "\nAction: " + fileAction.getTypeAction().toString(),
                                  action + " fichier!\nSUCCÈS!",
                                  JOptionPane.WARNING_MESSAGE
                            );
                        } //If

                        //Mettre le status de l'action [Retirer] comme étant un Succès
                        fileAction.setEstSuccès(true);
                        break;

                    default:
                        System.out.println("Oh non - Un problème lors du traitement de l'action avec le fichier est survenue.");  //Implémenter - Journalisation de la défaillance FileAction - si le temps le permet... 

                }
                // Répond NON à la confirmation de l'action

            }

            else { //reply == JOptionPane.NO_OPTION ... au désir de Voulez-vous vraiment faire L'action en question
                return null;
            }

        }
        else { //   if (file == null)   
            JOptionPane.showMessageDialog(view, "Oups - Aucun fichier n'a été sélectionné...");
            System.out.println("No file selected");
            return null;    //Implémenter - Journalisation de la défaillance FileAction - si le temps le permet...   
        }

        // S'assurer que les changement sont bien appliqués... et toute de suite... 
        commitOndemand();

        return fileAction;
    }

    
    public void commitOndemand() {
        // S'assurer que les changement sont bien appliqués... et toute de suite... 
        if (getIndexer().getWriter().hasUncommittedChanges()) {
            try {
                getIndexer().getWriter().commit();
            }
            catch (IOException ex) {
                Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

        
    
        //Enlever le panneau avec correspondance et mettre le vide
    public void revertEmptyResultPanel (  ) {            
        view.getCardLayoutResultsSection().show( view.getResultSectionpnl(), "vide");                           
        view.revalidate();
        view.repaint();              
    }
          
   
    
      //Prendre le nouveau panneau et l'inclure dans le panneau fait pour montrer les résultats... 
    public void swapCardJpanelToResults(JScrollPane scrpane) {                                                          //https://stackoverflow.com/questions/10823382/how-to-show-different-cards-in-a-cardlayout

        view.getCardLayoutResultsSection().show(view.getResultSectionpnl(), "non-vide");
        view.getResultspnl().removeAll();
        view.getResultspnl().add(scrpane);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrpane.getVerticalScrollBar().setValue(0);
            }
        });
        view.revalidate();
        view.repaint();  
        
    }

    
    
    public UsineRequête prépareRechercheSéquence() {

        UsineRequête recherchePréUsinée;

        //prends chaîne de recherche
        String chaineRecherche = view.getSearchStringtxf().getText().trim();

        // Y=a-t=il un filtre de d'extension en jeu?
        Boolean hasContentTypeFilterSelected = !view.getContentTypelst().isSelectionEmpty();

        if (hasContentTypeFilterSelected) {

            //Construire ave monUsineRecherche... avec Filtreur extension... 
            recherchePréUsinée = new UsineRequête(chaineRecherche, new FiltreurExtension(view.getContentTypelst().getSelectedValue()));
            return recherchePréUsinée;
        }

        // Pas de filtre d'extension en jeu donc... c'est simple... 
        recherchePréUsinée = new UsineRequête(chaineRecherche);

        return recherchePréUsinée;

    }

    // RechercheExtrant contient (TopDocs docs, IRecherche recherche)
    public JScrollPane prepCorrespondanceJPanelGenerator(RechercheExtrant rechercheExtrant) {
            
        int countCorrespondance = (int) rechercheExtrant.getDocs().scoreDocs.length;                                                                                                                            
        
        String execTime = rechercheExtrant.getRecherche().getDuréeExecution() + " milisecondes.";    
        
        //debug seacrhtime issue: 
        System.out.println("créer le début"+ rechercheExtrant.getRecherche().créeLe );
        System.out.println("finalisé le fin: "+ rechercheExtrant.getRecherche().finaliséLe );        
        
                        
        String filtre = rechercheExtrant.getRecherche() instanceof SimpleFiltrerRecherche     
              ? ((SimpleFiltrerRecherche) rechercheExtrant.getRecherche()).getFiltre().getExtensionInclure() 
              : "aucun";        
                
        
        GridBagLayout gbaglayoutCorrespondance = new GridBagLayout();       

        JPanel correspondanceIntérieurpnl = new JPanel();     //   correspondanceIntérieurpnl.setBackground(Color.PINK);                
        correspondanceIntérieurpnl.setBorder(new LineBorder(Color.RED)); 
        correspondanceIntérieurpnl.setLayout(gbaglayoutCorrespondance);
 
       // Then  - ROw 1 = exctime + hit count
        JTextArea execEtHitstxt = new JTextArea();
        execEtHitstxt.setEditable(false);
        TitledBorder titleBorderCorresTop = new TitledBorder ("Sommaire des correspondances"); 
        titleBorderCorresTop.setTitleJustification(TitledBorder.CENTER);
        execEtHitstxt.setBorder(BorderFactory.createCompoundBorder(titleBorderCorresTop,  BorderFactory.createEmptyBorder(5, 5, 5, 5) ) );     
        execEtHitstxt.append("Chaine de texte recherchée: " + rechercheExtrant.getRecherche().getChaineRecherche()+"\n");
        execEtHitstxt.append("Nombre de correspondances présente: " + countCorrespondance + "\n");
        execEtHitstxt.append("Temps d'éxécution (ms): " + execTime + "\n");
      
        //Recherche sans filtrage par extension...           
        if (filtre.equals("aucun")) {     
            execEtHitstxt.append("Filtre d'extension utilisé (options avancées) : " + "Aucun\n");
           }   else {  execEtHitstxt.append("Filtre d'extension utilisé (options avancées) : " + filtre+"\n");               System.out.println("Filtre Utilisé :"+filtre);
        }
        
        //Constraints row 1        
        GridBagConstraints gbcExecHitsTxt = FrameCentral.makeConstraints (1, 1,     0, 0,    0.2, 0.5 );
        gbcExecHitsTxt.fill = GridBagConstraints.HORIZONTAL;
        gbcExecHitsTxt.anchor = GridBagConstraints.NORTH;
        gbaglayoutCorrespondance.setConstraints(execEtHitstxt, gbcExecHitsTxt);  
                         
        correspondanceIntérieurpnl.add(execEtHitstxt, gbcExecHitsTxt);

       
        // Pour chaque ScoreDocs        // get the list of item from preCorrespondanceDate     
        Map<String, String> input = new HashMap<>();

        int entrée = 1;        
        for (final ScoreDoc scoreDoc : rechercheExtrant.getDocs().scoreDocs) {

            JTextArea tempTextArea = new JTextArea();
            tempTextArea.setEditable(false);    //  tempTextArea.setBackground(Color.CYAN);
             TitledBorder titleBorderCorresBottom = new TitledBorder ("[Correspondance "+entrée+"]"); 
             titleBorderCorresBottom.setTitleJustification(TitledBorder.CENTER);
            tempTextArea.setBorder(BorderFactory.createCompoundBorder(titleBorderCorresBottom,  BorderFactory.createEmptyBorder(5, 5, 5, 5) ) );      
         

            input = prepCorrespondanceMap(scoreDoc);
            
            tempTextArea.append("Nom du fichier: " + input.get(Constants.FILE_NAME) + Util.newline); 
            tempTextArea.append("Chemin du fichier (origine): " + input.get(Constants.FILE_PATH) + Util.newline);
            tempTextArea.append("Type de document : " + input.get(Constants.CONTENT_TYPE) + Util.newline);
            tempTextArea.append("Score de Lucene: " + input.get("Score") + Util.newline);                                 
           
            
            GridBagConstraints gbcIterator = new GridBagConstraints();         
       
            gbcIterator = FrameCentral.makeConstraints (1, 1,     0, entrée,    0.5, 0.5 );
            gbcIterator.fill = GridBagConstraints.BOTH;
            gbcIterator.anchor = GridBagConstraints.NORTH;
            
            gbaglayoutCorrespondance.setConstraints(tempTextArea, gbcIterator);   
                         
            correspondanceIntérieurpnl.add(tempTextArea, gbcIterator); 
            correspondanceIntérieurpnl.setVisible(true);
            
                         
            entrée++; // augmenter l'itérateur et ensuite on ajoute autres entrées...  //  

        }
                        
       JScrollPane  resultscp = new JScrollPane (correspondanceIntérieurpnl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER  );    
       resultscp.getViewport().setPreferredSize(new Dimension(650, 700));
       resultscp.setAutoscrolls(false);
       return resultscp;     
    }
        

    public Map< String, String> prepCorrespondanceMap(ScoreDoc scoreDoc) {

        //get the ID right away
        int docId = scoreDoc.doc;

        //data structure prep..
        HashMap<String, String> datatoDisplay = new HashMap<String, String>();

        try {
            //Filepath
            datatoDisplay.put(Constants.FILE_PATH, moteurRecherche.indexReader.document(docId).get(Constants.FILE_PATH));
            System.out.println(" in prepCorrespndanceData path: =" + Constants.FILE_PATH + " & " + datatoDisplay.get(Constants.FILE_PATH));

            // Filename 
            datatoDisplay.put(Constants.FILE_NAME, moteurRecherche.indexReader.document(docId).get(Constants.FILE_NAME));
            System.out.println("in prepCorrespndanceData name= " + Constants.FILE_NAME + " & " + datatoDisplay.get(Constants.FILE_NAME));

            //Content-Type                   
            datatoDisplay.put(Constants.CONTENT_TYPE, moteurRecherche.indexReader.document(docId).get(Constants.CONTENT_TYPE));
            System.out.println("in prepCorrespndanceData contentType= " + Constants.CONTENT_TYPE + " & " + datatoDisplay.get(Constants.CONTENT_TYPE));

            //Score
            String scoreVal = Float.toString(scoreDoc.score);
            datatoDisplay.put("Score", scoreVal);
            System.out.println("in prepCorrespndanceData Score= " + "Score" + " & " + datatoDisplay.get("Score"));

        }
        catch (IOException ex) {
            Logger.getLogger(FrameControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }

        // return it... 
        return datatoDisplay;

    }

    
    public void flipMenuFichierButtonsVisibility() {

        if (view.getAjouterFichierbtn().isVisible() == true) {

            view.getAjouterFichierbtn().setVisible(false);
            view.getEnleverFichierbtn().setVisible(false);
            return;
        }
        view.getAjouterFichierbtn().setVisible(true);
        view.getEnleverFichierbtn().setVisible(true);
    }

    public void flipIsVisiblePanel(JPanel panelToflipVisible) {

        if (panelToflipVisible.isVisible() == true) {
            panelToflipVisible.setVisible(false);
         
            return;
        }
        panelToflipVisible.setVisible(true);
    }

        
    private void clearAll(Container aContainer) {
    for(Component c:aContainer.getComponents()) {
        if(c instanceof JTextField || c instanceof JTextArea){
            ((JTextComponent) c).setText("");
        }else if (c instanceof Container) {
             clearAll((Container) c);
          view.getContentTypelst().clearSelection();    
          revertEmptyResultPanel();            
        }
    }
}
        


    public void enableRechercheBtnDocumentListener() {
            
        view.getSearchStringtxf().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkBtn();
                checkEmptyForm();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkBtn();
                 checkEmptyForm();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkBtn();
                checkEmptyForm();
            }
        });
        //disable by default
        view.getLancerRecherchebtn().setEnabled(false);

    }
    
          

    private void checkBtn() {
        boolean value = !view.getSearchStringtxf().getText().trim().isEmpty();
        view.getLancerRecherchebtn().setEnabled(value);
    }
    
    
    private void checkEmptyForm() {
        boolean value = !view.getSearchStringtxf().getText().trim().isEmpty() || !FrameControlleur.listIsEmpty;       
        view.getRéinitialiserFormbtn().setEnabled(value);      
    }

    /**
     * @return the indexer
     */
    public TikaIndexer getIndexer() {
        return indexer;
    }

    /**
     * @param indexer the indexer to set
     */
    public void setIndexer(TikaIndexer indexer) {
        this.indexer = indexer;
    }
       

}

class SharedListSelectionHandler implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        int firstIndex = e.getFirstIndex();         int lastIndex = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting();
        
      // DEBUG...   System.out.println("Event for indexes "                       + firstIndex + " - " + lastIndex                        + "; isAdjusting is " + isAdjusting                        + "; selected indexes:");

        if (lsm.isSelectionEmpty()) {
             System.out.println(" <none>  is on!");
             FrameControlleur.listIsEmpty=true;
        } else {
            // Find out which indexes are selected.
            FrameControlleur.listIsEmpty=false;
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                     System.out.println(" " + i);
                }
            }
        }
         System.out.println("\n");
    }
    
    
}
