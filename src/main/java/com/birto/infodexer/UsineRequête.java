package com.birto.infodexer;

import java.util.Objects;



// Sert a de Wrapper les critère d'une recherche abec SearchIndex ( ) ??
// Sert pour maintenir un dictionnaire des recherches effectuées et Logger des recheches. 


public class UsineRequête {
    
int nombreRésultatSauter = 20;    
private String stringSearch  ;
FiltreurExtension filtreurExtension;
private Boolean hasFilter;


    public UsineRequête(String stringSearch) {
        this.stringSearch = stringSearch;
        setHasFilter();
    }

    public UsineRequête(String stringSearch, FiltreurExtension filtreurExtension) {
        this.stringSearch = stringSearch;
        this.filtreurExtension = filtreurExtension;
        setHasFilter();
    }

    public UsineRequête(String stringSearch, FiltreurExtension filtreurExtension, int nombreRésultatSauter) {
        this.nombreRésultatSauter = nombreRésultatSauter;
        this.stringSearch = stringSearch;
        this.filtreurExtension = filtreurExtension;
        setHasFilter();
    }

    public UsineRequête(String stringSearch, int nombreRésultatSauter) {
        this.nombreRésultatSauter = nombreRésultatSauter;
        this.stringSearch = stringSearch;
        setHasFilter();
    }
 
        
    public int getNombreRésultatSauter() {
        return nombreRésultatSauter;
    }

    public String getStringSearch() {
        return stringSearch;
    }

    public FiltreurExtension getFiltreurExtension() {
        return filtreurExtension;
    }
    
    
    public void setHasFilter() {
    hasFilter = Objects.nonNull(filtreurExtension);           
 
    }   
    
    public Boolean getHasFilter() {
        return hasFilter;
    }   
    
}
