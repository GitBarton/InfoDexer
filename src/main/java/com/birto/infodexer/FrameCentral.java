package com.birto.infodexer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class FrameCentral extends JFrame {

    //Top section Panneau
    JPanel topSectionPnl;

    //Colonne 1 - Top
    JPanel menufichierPanelpnl;
    JPanel hamburberPnl;
    ImageIcon hamburgIco;
    JButton ajouterFichierbtn;
    JButton enleverFichierbtn;
    JButton hamburgerMenubtn;
    JPanel fichierOptionsPnl;

    //Colonne 2 - Top
    JPanel barreRecherchePanelpnl;
    JTextField searchStringtxf;
    JButton lancerRecherchebtn;
    JLabel optionsRecherchelbl;
    JLabel appTitrelbl;

    //Colonne 3 - Top
    JPanel contentTypeFiltreurPanelpnl;
    JLabel sélectionExtensiontypelbl;
    JScrollPane contentTypeFiltreurscrpne;
    JList<String> contentTypelst;
    JButton cacherExtensionFiltrebtn;

    // Milieu section - Panneau Resultats
    JPanel resultSectionpnl;
    CardLayout cardLayoutResultsSection;
    JPanel noResultsEmptypnl;
    JPanel resultspnl;

    // Bas section 
    JPanel basSectionPnl;
    JButton réinitialiserFormbtn;
    JButton exporterCorrespondance;

    public FrameCentral() throws IOException {
        initComponents();
    }

    private void initComponents() throws IOException {

        //JFRAME Properties    
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Info-Dexer");

        //  Top section Panneau #1 ********************************                      
        GridBagLayout gridBagLayoutTopSectionPnl = new GridBagLayout();

        topSectionPnl = new JPanel(gridBagLayoutTopSectionPnl);    //debug    topSectionPnl.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));        

        // Début ********************************  COL 1 - Top ********************************  
        menufichierPanelpnl = new JPanel();   ////debug menufichierPanelpnl.setBorder(new TitledBorder("menufichierPanelpnl"));
        menufichierPanelpnl.setBorder(BorderFactory.createLoweredBevelBorder());
        menufichierPanelpnl.setLayout(new BoxLayout(menufichierPanelpnl, BoxLayout.Y_AXIS));

        FlowLayout flowL = new FlowLayout(FlowLayout.CENTER);
        hamburberPnl = new JPanel(flowL);                           ////debug  hamburberPnl.setBorder(new TitledBorder("hamburberPnl"));        
        fichierOptionsPnl = new JPanel(flowL);                      ////debug  fichierOptionsPnl.setBorder(new TitledBorder("fichierOptionsPnl"));

        // Menu Hamburger (Fichier Options)********************************  
        hamburgIco = new ImageIcon(scaleImage(getImageHamburg(), 25, 25));
        hamburgerMenubtn = new javax.swing.JButton("Index", hamburgIco);
        hamburgerMenubtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        hamburgerMenubtn.setHorizontalTextPosition(SwingConstants.CENTER);
        hamburgerMenubtn.setIconTextGap(2);
        hamburgerMenubtn.setFont(new java.awt.Font("sansserif", 0, 8));
        hamburberPnl.add(hamburgerMenubtn);

        //les 2 boutons ********************************  
        // btn 1 - Ajouter
        JLabel ajouterlbl = new JLabel("Fichier (+)", SwingConstants.CENTER);
        ajouterFichierbtn = new JButton();
        ajouterFichierbtn.setLayout(new BorderLayout());
        ajouterFichierbtn.setFont(new java.awt.Font("sansserif", 0, 7));
        ajouterFichierbtn.add(ajouterlbl);
        ajouterFichierbtn.setVerticalTextPosition(SwingConstants.CENTER);
        ajouterFichierbtn.setVisible(false);                    // cacher au lancement

        fichierOptionsPnl.add(ajouterFichierbtn);

        // btn 2 - Retirer fichier 
        JLabel enleverlbl = new JLabel("Fichier (-)", SwingConstants.CENTER);
        enleverFichierbtn = new JButton();
        enleverFichierbtn.setLayout(new BorderLayout());
        enleverFichierbtn.setFont(new java.awt.Font("sansserif", 0, 7));
        enleverFichierbtn.add(enleverlbl);
        enleverFichierbtn.setHorizontalTextPosition(SwingConstants.CENTER);
        enleverFichierbtn.setVisible(false);                // cacher au lancement

        fichierOptionsPnl.add(enleverFichierbtn);

        // Ajouter les deux panels au conteneur en boxlayout.     
        menufichierPanelpnl.add(hamburberPnl);
        menufichierPanelpnl.add(fichierOptionsPnl);

        //CONSTRAINTS - MenufichierPanelpnl       // makeConstraints(int weight, int height, int x-axis placement, int y-axis placement , double weightx, double weighty) {
        GridBagConstraints gbcMenufichierPanelpnl = makeConstraints(1, 2, 0, 0, 0.5, 0.5);
        gbcMenufichierPanelpnl.anchor = GridBagConstraints.NORTHWEST;
        gbcMenufichierPanelpnl.fill = GridBagConstraints.NONE;
        gridBagLayoutTopSectionPnl.setConstraints(menufichierPanelpnl, gbcMenufichierPanelpnl);

        //Ajouter MenufichierPanelpnl to au JPanel Top
        topSectionPnl.add(menufichierPanelpnl);

        // Fin  ********************************  COL 1 - Top ********************************       
     
        
// Début ********************************  COL 2 - Top ********************************        
        barreRecherchePanelpnl = new JPanel();
        barreRecherchePanelpnl.setLayout(new BoxLayout(barreRecherchePanelpnl, BoxLayout.Y_AXIS));       //  barreRecherchePanelpnl.setBorder(new TitledBorder("barreRecherchePanelpnl"));

         // Ajouter espace  
        barreRecherchePanelpnl.add(Box.createRigidArea(new Dimension(20, 50)));

        //TextArea pour entrer la chaine de recherche.
        searchStringtxf = new JTextField(50);
        searchStringtxf.setToolTipText("Entrer la chaîne de caractère"); // searchStringtxf.setBorder(new EmptyBorder(0, 25, 0, 25));     
        searchStringtxf.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(0, 10, 0, 50)));
        searchStringtxf.setSize(30, 20);
        searchStringtxf.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        searchStringtxf.setPreferredSize(new Dimension(80, 20));
        barreRecherchePanelpnl.add(Box.createRigidArea(new Dimension(0, 50)));
        barreRecherchePanelpnl.add(searchStringtxf);
        barreRecherchePanelpnl.add(Box.createRigidArea(new Dimension(0, 10)));

        //Button Rechercher
        lancerRecherchebtn = new JButton();
        lancerRecherchebtn.setText("Rechercher");
        lancerRecherchebtn.setBorder(new CompoundBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), new EmptyBorder(10, 15, 10, 15)));
        barreRecherchePanelpnl.add(lancerRecherchebtn);

        // Label - Options Avancées de recherche
        optionsRecherchelbl = new JLabel();
        optionsRecherchelbl.setFont(new Font("sansserif", 1, 10));
        optionsRecherchelbl.setForeground(new Color(51, 51, 255));
        optionsRecherchelbl.setText("Options Avancées");
        optionsRecherchelbl.setPreferredSize(new Dimension(25, 10));
        optionsRecherchelbl.setBorder(new EmptyBorder(0, 10, 0, 0));

        barreRecherchePanelpnl.add(Box.createHorizontalGlue());
        barreRecherchePanelpnl.add(optionsRecherchelbl);

        //Ajouter la colonne 2 au JPanel TOP
        //Constraints to Column 2 du TOP (barreRecherchePanelpnl) 
        GridBagConstraints gbcBarreRecherchePanelpnl = makeConstraints(4, 2, 1, 0, 1.5, 1.5);
        gbcBarreRecherchePanelpnl.anchor = GridBagConstraints.WEST;
        gbcBarreRecherchePanelpnl.fill = GridBagConstraints.BOTH;
        gridBagLayoutTopSectionPnl.setConstraints(barreRecherchePanelpnl, gbcBarreRecherchePanelpnl);

        topSectionPnl.add(barreRecherchePanelpnl);

        // Fin ********************************  COL 2 - Top  ********************************   
    
        
        // Début ********************************  COL 3 - Top ********************************   
        contentTypeFiltreurPanelpnl = new JPanel(new BorderLayout());
        contentTypeFiltreurPanelpnl.setBorder(new CompoundBorder(new EmptyBorder(5, 25, 5, 25), new LineBorder(Color.BLACK)));

        // Jlabel de la boîte contenant le  JList
        sélectionExtensiontypelbl = new JLabel("Sélection par Type", JLabel.CENTER);
        sélectionExtensiontypelbl.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        sélectionExtensiontypelbl.setText("Sélection par Type");
        contentTypeFiltreurPanelpnl.add(sélectionExtensiontypelbl, BorderLayout.PAGE_START);

        // Ajouter Jlist / Le model est ajouter at runtime par le FrameControlleur via un ListModel    
        contentTypelst = new JList<>();
        contentTypelst.setFont(new java.awt.Font("sansserif", 0, 10));
        contentTypelst.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contentTypeFiltreurscrpne = new JScrollPane();
        contentTypeFiltreurscrpne.setViewportView(contentTypelst);
        contentTypeFiltreurscrpne.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 20, 10), new EtchedBorder()));
        contentTypeFiltreurscrpne.getViewport().setViewPosition(new Point(0, 0));
        contentTypeFiltreurPanelpnl.add(contentTypeFiltreurscrpne, BorderLayout.CENTER);

        // Ajouter bouton "cacher"
        cacherExtensionFiltrebtn = new JButton();
        cacherExtensionFiltrebtn.setText("Cacher");
        cacherExtensionFiltrebtn.setBorder(new EmptyBorder(0, 40, 10, 40));
        cacherExtensionFiltrebtn.setFont(new java.awt.Font("sansserif", 0, 10));
        cacherExtensionFiltrebtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        contentTypeFiltreurPanelpnl.add(cacherExtensionFiltrebtn, BorderLayout.SOUTH);

        //Ajouter JPanel Colonne 3 au JPanel TOP
        // CONSTRAINTS    
        GridBagConstraints gbcContentTypeFiltreurPanelpnl = makeConstraints(1, 2, 5, 0, 0, 0);
        gbcContentTypeFiltreurPanelpnl.anchor = GridBagConstraints.EAST;
        gbcContentTypeFiltreurPanelpnl.fill = GridBagConstraints.BOTH;
        gridBagLayoutTopSectionPnl.setConstraints(contentTypeFiltreurPanelpnl, gbcContentTypeFiltreurPanelpnl);

        topSectionPnl.add(contentTypeFiltreurPanelpnl);

        // Fin  ********************************  COL 3 - Top ********************************   
        // IMPORTANT  | Ajouter Top Section Panel au JPanel Primaire (Root)
        this.add(topSectionPnl, BorderLayout.NORTH);

//********************************   FIN SECTION 1   ********************************   
//********************************   ********************************   *************
        

//********* Section 2 Panneau - Résultats ************************************    
        //Créer le panneau qui contient des JPanel qui sont alternables via un CardLayout 
        cardLayoutResultsSection = new CardLayout(0, 0);

        resultSectionpnl = new JPanel(cardLayoutResultsSection);
        resultSectionpnl.setPreferredSize(new Dimension(900, 400)); //  resultSectionpnl.setBorder(new TitledBorder("resultSectionpnl (parent-top)"));

        //empty Jpanel pour les résultas          
        noResultsEmptypnl = new JPanel();
        noResultsEmptypnl.setBackground(Color.GRAY); // noResultsEmptypnl.setBorder(new TitledBorder("noResultsEmptypnl (child-0-NoRez)"));
        noResultsEmptypnl.setBorder(new EtchedBorder(2));
        noResultsEmptypnl.setSize(800, 350);

        resultSectionpnl.add(noResultsEmptypnl, "vide");

        // L'autre contiendra les valeurs et sera généré dynamiquement... 
        resultspnl = new JPanel();   // resultspnl.setBackground(Color.ORANGE); resultspnl.setBorder(new TitledBorder("resultspnl (child-1-Rez)"));
        resultspnl.setBorder(new EtchedBorder(2));
        noResultsEmptypnl.setSize(800, 350);

        resultSectionpnl.add(resultspnl, "non-vide");

        // IMPORTANT  | Ajouter Top Section Panel au JPanel Primaire (Root)
        this.add(resultSectionpnl, BorderLayout.CENTER);

//********************************   FIN SECTION 2   ********************************   
//********************************   ********************************   *************


//********* Section 3 Panneau - Buttons en bas de page ***************************    
        basSectionPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        basSectionPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        réinitialiserFormbtn = new JButton("Réinitialiser");
        réinitialiserFormbtn.setVisible(true);
        réinitialiserFormbtn.setEnabled(false);

        exporterCorrespondance = new JButton("Exporter");
        exporterCorrespondance.setVisible(false);

        basSectionPnl.add(réinitialiserFormbtn);
        basSectionPnl.add(exporterCorrespondance);

        // IMPORTANT  | Ajouter Top Section Panel au JPanel Primaire (Root)
        this.add(basSectionPnl, BorderLayout.SOUTH);

//********************************   FIN SECTION 3   ********************************   
//***********************************************************************************
      

setVisible(true);
repaint();
    }

          
 private Image scaleImage(Image image, int w, int h) {
    Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    return scaled;
}           
  
 public Image getImageHamburg () throws IOException      {
       Image  imgBurger = ImageIO.read(getClass().getResource("/Hamburger_50.png"));       
       return imgBurger; }          
    

    public JPanel getHamburberPnl() { 
        return hamburberPnl;
    }

    public void setHamburberPnl(JPanel hamburberPnl) {
        this.hamburberPnl = hamburberPnl;
    }

    public JPanel getFichierOptionsPnl() {
        return fichierOptionsPnl;
    }

    public void setFichierOptionsPnl(JPanel fichierOptionsPnl) {
        this.fichierOptionsPnl = fichierOptionsPnl;
    }       

    public JButton getRéinitialiserFormbtn() {
        return réinitialiserFormbtn;
    }

    public void setRéinitialiserFormbtn(JButton réinitialiserFormbtn) {
        this.réinitialiserFormbtn = réinitialiserFormbtn;
    }

    public JButton getExporterCorrespondance() {
        return exporterCorrespondance;
    }

    public void setExporterCorrespondance(JButton exporterCorrespondance) {
        this.exporterCorrespondance = exporterCorrespondance;
    }
       
    public JPanel getResultSectionpnl() {
        return resultSectionpnl;
    }

    public void setResultSectionpnl(JPanel resultSectionpnl) {
        this.resultSectionpnl = resultSectionpnl;
    }

    public JPanel getNoResultsEmptypnl() {
        return noResultsEmptypnl;
    }

    public void setNoResultsEmptypnl(JPanel noResultsEmptypnl) {
        this.noResultsEmptypnl = noResultsEmptypnl;
    }

    public JPanel getResultspnl() {
        return resultspnl;
    }

    public void setResultspnl(JPanel Resultspnl) {
        this.resultspnl = Resultspnl;
    }

    public JPanel getBasSectionPnl() {
        return basSectionPnl;
    }

    public void setBasSectionPnl(JPanel basSectionPnl) {
        this.basSectionPnl = basSectionPnl;
    }

    public JButton getAjouterFichierbtn() {
        return ajouterFichierbtn;
    }

    public void setAjouterFichierbtn(JButton ajouterFichierbtn) {
        this.ajouterFichierbtn = ajouterFichierbtn;
    }

    public JPanel getBarreRecherchePanelpnl() {
        return barreRecherchePanelpnl;
    }

    public void setBarreRecherchePanelpnl(JPanel barreRecherchePanelpnl) {
        this.barreRecherchePanelpnl = barreRecherchePanelpnl;
    }

    public JPanel getContentTypeFiltreurPanelpnl() {
        return contentTypeFiltreurPanelpnl;
    }

    public void setContentTypeFiltreurPanelpnl(JPanel contentTypeFiltreurPanelpnl) {
        this.contentTypeFiltreurPanelpnl = contentTypeFiltreurPanelpnl;
    }

    public JList<String> getContentTypelst() {
        return contentTypelst;
    }

    public void setContentTypelst(JList<String> contentTypelst) {
        this.contentTypelst = contentTypelst;
    }

    public JButton getEnleverFichierbtn() {
        return enleverFichierbtn;
    }

    public void setEnleverFichierbtn(JButton enleverFichierbtn) {
        this.enleverFichierbtn = enleverFichierbtn;
    }

    public JButton getHamburgerMenubtn() {
        return hamburgerMenubtn;
    }

    public void setHamburgerMenubtn(JButton hamburgerMenubtn) {
        this.hamburgerMenubtn = hamburgerMenubtn;
    }

    public JButton getCacherExtensionFiltrebtn() {
        return cacherExtensionFiltrebtn;
    }

    public void setCacherExtensionFiltrebtn(JButton cacherExtensionFiltrebtn) {
        this.cacherExtensionFiltrebtn = cacherExtensionFiltrebtn;
    }

    public JLabel getSélectionExtensiontypelbl() {
        return sélectionExtensiontypelbl;
    }

    public void setSélectionExtensiontypelbl(JLabel sélectionExtensiontypelbl) {
        this.sélectionExtensiontypelbl = sélectionExtensiontypelbl;
    }

    public JScrollPane getContentTypeFiltreurscrpne() {
        return contentTypeFiltreurscrpne;
    }

    public void setContentTypeFiltreurscrpne(JScrollPane contentTypeFiltreurscrpne) {
        this.contentTypeFiltreurscrpne = contentTypeFiltreurscrpne;
    }

    public JTextField getjTextField1() {
        return searchStringtxf;
    }

    public void setjTextField1(JTextField jTextField1) {
        this.searchStringtxf = jTextField1;
    }

    public JButton getLancerRecherchebtn() {
        return lancerRecherchebtn;
    }

    public void setLancerRecherchebtn(JButton lancerRecherchebtn) {
        this.lancerRecherchebtn = lancerRecherchebtn;
    }

    public JPanel getMenufichierPanelpnl() {
        return menufichierPanelpnl;
    }

    public void setMenufichierPanelpnl(JPanel menufichierPanelpnl) {
        this.menufichierPanelpnl = menufichierPanelpnl;
    }

    public JLabel getOptionsRecherchelbl() {
        return optionsRecherchelbl;
    }

    public void setOptionsRecherchelbl(JLabel optionsRecherchelbl) {
        this.optionsRecherchelbl = optionsRecherchelbl;
    }

    public JLabel getAppTitrelbl() {
        return appTitrelbl;
    }

    public void setAppTitrelbl(JLabel appTitrelbl) {
        this.appTitrelbl = appTitrelbl;
    }

    public JPanel getTopSectionPnl() {
        return topSectionPnl;
    }

    public void setTopSectionPnl(JPanel topSectionPnl) {
        this.topSectionPnl = topSectionPnl;
    }

    public CardLayout getCardLayoutResultsSection() {
        return cardLayoutResultsSection;
    }

    public void setCardLayoutResultsSection(CardLayout cardLayoutResultsSection) {
        this.cardLayoutResultsSection = cardLayoutResultsSection;
    }
       
        
    public JTextField getSearchStringtxf() {
        return searchStringtxf;
    }

    public void setSearchStringtxf(JTextField searchStringtxf) {
        this.searchStringtxf = searchStringtxf;
    }

    
    
    /**
     * 
     * 
 Generate constraints for Swing components
	  * @param w       desired component width
	  * @param h       desired component height
	  * @param x       desired location in x-axis
	  * @param y       desired location in y-axis
	  * @param weightx desired weight in terms of x-axis
	  * @param weighty desired weight in terms of y-axis
     * @return 
	  */
	public static GridBagConstraints makeConstraints(int w, int h, int x, int y, double weightx, double weighty) {
		GridBagConstraints constraints = new GridBagConstraints();
		      
		constraints.gridwidth = w;
		constraints.gridheight = h;
            
		constraints.gridx = x;
		constraints.gridy = y;
            
		constraints.weightx = weightx;
		constraints.weighty = weighty;           
		return constraints;                   
	}                         
}
    
 
         

    
