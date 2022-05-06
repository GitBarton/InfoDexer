
package com.birto.infodexer;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends javax.swing.JFrame {

   
    public MainFrame() {
        initComponents();
    }

 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menufichierPanelpnl = new javax.swing.JPanel();
        enleverFichierbtn = new javax.swing.JButton();
        ajouterFichierbtn = new javax.swing.JButton();
        hamburgerMenubtn = new javax.swing.JButton();
        searchStringtxf = new javax.swing.JTextField();
        lancerRecherchebtn = new javax.swing.JButton();
        optionsRecherchelbl = new javax.swing.JLabel();
        contentTypeFiltreurPanelpnl = new javax.swing.JPanel();
        cacherExtensionFiltrebtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        contentTypelst = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        correspondancesSectionLayeredPane = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menufichierPanelpnl.setBorder(null);
        menufichierPanelpnl.setEnabled(false);

        enleverFichierbtn.setFont(new java.awt.Font("sansserif", 0, 8)); // NOI18N
        enleverFichierbtn.setText("Enlever (-)");
        enleverFichierbtn.setVisible(false);

        ajouterFichierbtn.setFont(new java.awt.Font("sansserif", 0, 8)); // NOI18N
        ajouterFichierbtn.setText("Ajouter (+)");
        ajouterFichierbtn.setActionCommand("addFichier");
        ajouterFichierbtn.setVisible(false);

        hamburgerMenubtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hamburger_50.png"))); // NOI18N
        hamburgerMenubtn.setToolTipText("Menu Ajout/Retrait de fichiers");
        hamburgerMenubtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hamburgerMenubtnActionPerformed(evt);
            }
        });

        searchStringtxf.setToolTipText("Entrer la chaîne de caractère");
        searchStringtxf.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        searchStringtxf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStringtxfActionPerformed(evt);
            }
        });

        lancerRecherchebtn.setText("Rechercher");
        lancerRecherchebtn.setActionCommand("");
        lancerRecherchebtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lancerRecherchebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lancerRecherchebtnActionPerformed(evt);
            }
        });

        optionsRecherchelbl.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        optionsRecherchelbl.setForeground(new java.awt.Color(51, 51, 255));
        optionsRecherchelbl.setText("Options avancées");

        contentTypeFiltreurPanelpnl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        contentTypeFiltreurPanelpnl.setVisible(false);

        cacherExtensionFiltrebtn.setText("Cacher");
        cacherExtensionFiltrebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cacherExtensionFiltrebtnActionPerformed(evt);
            }
        });

        contentTypelst.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        contentTypelst.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(contentTypelst);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("Sélection par Type");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout contentTypeFiltreurPanelpnlLayout = new javax.swing.GroupLayout(contentTypeFiltreurPanelpnl);
        contentTypeFiltreurPanelpnl.setLayout(contentTypeFiltreurPanelpnlLayout);
        contentTypeFiltreurPanelpnlLayout.setHorizontalGroup(
            contentTypeFiltreurPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentTypeFiltreurPanelpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentTypeFiltreurPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentTypeFiltreurPanelpnlLayout.createSequentialGroup()
                        .addComponent(cacherExtensionFiltrebtn)
                        .addGap(28, 28, 28))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentTypeFiltreurPanelpnlLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        contentTypeFiltreurPanelpnlLayout.setVerticalGroup(
            contentTypeFiltreurPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentTypeFiltreurPanelpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cacherExtensionFiltrebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menufichierPanelpnlLayout = new javax.swing.GroupLayout(menufichierPanelpnl);
        menufichierPanelpnl.setLayout(menufichierPanelpnlLayout);
        menufichierPanelpnlLayout.setHorizontalGroup(
            menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                .addGroup(menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ajouterFichierbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enleverFichierbtn))
                    .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(hamburgerMenubtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addGroup(menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                        .addComponent(lancerRecherchebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionsRecherchelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(searchStringtxf, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentTypeFiltreurPanelpnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menufichierPanelpnlLayout.setVerticalGroup(
            menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(searchStringtxf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(optionsRecherchelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lancerRecherchebtn)))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menufichierPanelpnlLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(hamburgerMenubtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(menufichierPanelpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ajouterFichierbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(enleverFichierbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(menufichierPanelpnlLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(contentTypeFiltreurPanelpnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        enleverFichierbtn.getAccessibleContext().setAccessibleName("");
        ajouterFichierbtn.getAccessibleContext().setAccessibleName("");
        lancerRecherchebtn.getAccessibleContext().setAccessibleName("recherchebtn");
        optionsRecherchelbl.getAccessibleContext().setAccessibleName("optionsAvancéeslbl");

        jScrollPane4.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setWheelScrollingEnabled(false);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(2);
        jTextArea3.setText("Résultats Obtenues:\nTemps Exécution:     ");
        jTextArea3.setAutoscrolls(false);
        jTextArea3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane4.setViewportView(jTextArea3);

        correspondancesSectionLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Rien à afficher...");

        correspondancesSectionLayeredPane.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout correspondancesSectionLayeredPaneLayout = new javax.swing.GroupLayout(correspondancesSectionLayeredPane);
        correspondancesSectionLayeredPane.setLayout(correspondancesSectionLayeredPaneLayout);
        correspondancesSectionLayeredPaneLayout.setHorizontalGroup(
            correspondancesSectionLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, correspondancesSectionLayeredPaneLayout.createSequentialGroup()
                .addContainerGap(372, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(360, 360, 360))
        );
        correspondancesSectionLayeredPaneLayout.setVerticalGroup(
            correspondancesSectionLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(correspondancesSectionLayeredPaneLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(jLabel4)
                .addContainerGap(252, Short.MAX_VALUE))
        );

        jLabel3.setText("Pagination (to be implemented)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(menufichierPanelpnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(correspondancesSectionLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(337, 337, 337))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(menufichierPanelpnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correspondancesSectionLayeredPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12))
        );

        jLabel3.getAccessibleContext().setAccessibleName("paginationlbl");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lancerRecherchebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lancerRecherchebtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lancerRecherchebtnActionPerformed

    private void searchStringtxfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStringtxfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchStringtxfActionPerformed

    private void hamburgerMenubtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hamburgerMenubtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hamburgerMenubtnActionPerformed

    private void cacherExtensionFiltrebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cacherExtensionFiltrebtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cacherExtensionFiltrebtnActionPerformed

    public JButton getAjouterFichierbtn() {
        return ajouterFichierbtn;
    }

    public void setAjouterFichierbtn(JButton ajouterFichierbtn) {
        this.ajouterFichierbtn = ajouterFichierbtn;
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

    public JLayeredPane getCorrespondancesSectionLayeredPane() {
        return correspondancesSectionLayeredPane;
    }

    public void setCorrespondancesSectionLayeredPane(JLayeredPane correspondancesSectionLayeredPane) {
        this.correspondancesSectionLayeredPane = correspondancesSectionLayeredPane;
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

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ajouterFichierbtn;
    private javax.swing.JButton cacherExtensionFiltrebtn;
    private javax.swing.JPanel contentTypeFiltreurPanelpnl;
    private javax.swing.JList<String> contentTypelst;
    private javax.swing.JLayeredPane correspondancesSectionLayeredPane;
    private javax.swing.JButton enleverFichierbtn;
    private javax.swing.JButton hamburgerMenubtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton lancerRecherchebtn;
    private javax.swing.JPanel menufichierPanelpnl;
    private javax.swing.JLabel optionsRecherchelbl;
    private javax.swing.JTextField searchStringtxf;
    // End of variables declaration//GEN-END:variables

    public JButton getCacherExtensionFiltrebtn() {
        return cacherExtensionFiltrebtn;
    }

    public void setCacherExtensionFiltrebtn(JButton cacherExtensionFiltrebtn) {
        this.cacherExtensionFiltrebtn = cacherExtensionFiltrebtn;
    }

    public JTextField getSearchStringtxf() {
        return searchStringtxf;
    }

    public void setSearchStringtxf(JTextField searchStringtxf) {
        this.searchStringtxf = searchStringtxf;
    }
}