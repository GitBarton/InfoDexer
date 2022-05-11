package com.birto.infodexer;

import java.time.LocalDateTime;


// Utilisation d'un SimpleFactory "pattern"
public class IRechercheFactory {
    
    static IRecherche rechercheFactory = null;
    
    
    public static IRecherche créerIRecherche (String type) {

        if (type.equals("simple")) {
            rechercheFactory = new SimpleRecherche();
        }
        else if (type.equals("filtrer")) {
            rechercheFactory = new SimpleFiltrerRecherche();
        }
        return rechercheFactory;
    }

}
