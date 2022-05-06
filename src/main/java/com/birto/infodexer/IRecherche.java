package com.birto.infodexer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class IRecherche {
    
   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
   int rechercheId; 
   String chaineRecherche;
     
   
   LocalDateTime créeLe;
   LocalDateTime finaliséLe;
   long duréeExecution; 
  

    public static AtomicInteger getID_GENERATOR() {
        return ID_GENERATOR;
    }


    public IRecherche(String chaineRecherche,  LocalDateTime créeLe) {
        this.rechercheId = IRecherche.ID_GENERATOR.getAndIncrement();
        this.chaineRecherche = chaineRecherche;
        this.créeLe = créeLe;
    }

    public void setChaineRecherche(String chaineRecherche) {
        this.rechercheId = IRecherche.ID_GENERATOR.getAndIncrement();
        this.chaineRecherche = chaineRecherche;        
    }

    
      public IRecherche() {    
           this.rechercheId = IRecherche.ID_GENERATOR.getAndIncrement();
    }

    public void setCréeLe(LocalDateTime créeLe) {
        this.créeLe = créeLe;
    }    

    
    public void setFinaliséLe(LocalDateTime finaliséLe) {
        this.finaliséLe = finaliséLe;
    }

    
    public void setDuréeExecution(LocalDateTime crée, LocalDateTime finalisé ) {
        this.duréeExecution = Duration.between(crée, finalisé).toMillis();
                
    }
    
    
    public long getDuréeExecution() {
        return duréeExecution;
    }          

    public int getRechercheId() {
        return rechercheId;
    }

    public String getChaineRecherche() {
        return chaineRecherche;
    }

    public LocalDateTime getCréeLe() {
        return créeLe;
    }

    public LocalDateTime getFinaliséLe() {
        return finaliséLe;
    }
  
    
    @Override
    public String toString() {
        return "IRecherche{" + "rechercheId=" + rechercheId + ", chaineRecherche=" + chaineRecherche + ", cr\u00e9eLe=" + créeLe + ", finalis\u00e9Le=" + finaliséLe + ", dur\u00e9eExecution en millisecondes=" + duréeExecution + '}';
    }

    
}



