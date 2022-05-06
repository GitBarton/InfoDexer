package com.birto.infodexer;

import java.time.LocalDateTime;

public class SimpleFiltrerRecherche extends IRecherche {
    
    private FiltreurExtension filtre;

    public SimpleFiltrerRecherche(){}; 

    public FiltreurExtension getFiltre() {
        return filtre;
    }

    public void setFiltre(FiltreurExtension filtre) {
        this.filtre = filtre;
    }
       
    
}
