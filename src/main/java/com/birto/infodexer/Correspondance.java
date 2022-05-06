package com.birto.infodexer;

import java.util.concurrent.atomic.AtomicInteger;
 
 
public class Correspondance {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    int id;
    String cotePertinence;
    IRecherche rechercheId;

    //Constructeur
    public Correspondance(String cotePertinence, IRecherche rechercheId) {
        this.id = Correspondance.ID_GENERATOR.getAndIncrement();
        this.cotePertinence = cotePertinence;
        this.rechercheId = rechercheId;
    }

}
