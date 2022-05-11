package com.birto.infodexer;

import com.birto.infodexer.Constants.ActionFichier;
import java.time.LocalDateTime;


public class FileAction {

  private  ActionFichier typeAction;
  private  LocalDateTime dateAction;
  private String nomFichier;
  private Boolean estSuccès = false;

    public FileAction(ActionFichier typeAction,String nomFichier) {
        this.typeAction = typeAction;
        this.dateAction = LocalDateTime.now();
        this.nomFichier = nomFichier;        
    }
  
  
    public ActionFichier getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(ActionFichier typeAction) {
        this.typeAction = typeAction;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Boolean getEstSuccès() {
        return estSuccès;
    }

    public void setEstSuccès(Boolean estSuccès) {
        this.estSuccès = estSuccès;
    }
    
    
    
    
}
