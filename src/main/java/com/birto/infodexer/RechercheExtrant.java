package com.birto.infodexer;

import org.apache.lucene.search.TopDocs;

public class RechercheExtrant<T> {                                                       // idea source: https://stackoverflow.com/questions/457629/how-to-return-multiple-objects-from-a-java-method

    private final TopDocs docs;
    private final IRecherche recherche;

    public RechercheExtrant(TopDocs docs, IRecherche recherche) {
        this.docs = docs;
        this.recherche = recherche;
    }

    public TopDocs getDocs() {
        return docs;
    }

    public IRecherche getRecherche() {
        return recherche;
    }
}
