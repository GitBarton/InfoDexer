package com.birto.infodexer;

import java.util.HashMap;
import java.util.Map;

//Permet de filtrer le type de fichiers fournis par le moteur de recherhce
public class FiltreurExtension {

    private String extensionInclure; // Il s'agit du format facilement lisible pour un humain. 
    Map<String, String> contentType = new HashMap<String, String>();  

    
    public FiltreurExtension(String filtre) {
        setContentType();
        setExtentionInclure(filtre);
    }

    
    public void setExtentionInclure(String filtre) {

        if (filtre.contains("MS-Word")) {
            this.extensionInclure = amalgameurPourMSWord();
            System.out.println("this.extensionInclure =" + this.extensionInclure);
            return; //break it out... 
        }
        //else
        this.extensionInclure = contentType.get(filtre);     // trouve la valeur dans la Map des filtreur de contenu         

        if (extensionInclure == null) {
            System.out.println("com.birto.infodexer.FiltreurExtension.setExtentionInclure() / A PROBLEM WITH INSTANCIATION OCCURRED");;
        }

    }

    
    final public void setContentType() {
        this.contentType.put("PDF", "application/pdf");
        this.contentType.put("MS-Excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        this.contentType.put("XML", "application/xml");
        this.contentType.put("JSON", "application/json");
        this.contentType.put("ZIP", "application/zip");
        this.contentType.put("MS-Word (.docx)", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        this.contentType.put("MS-Word (.doc)", "application/msword");
        this.contentType.put("MS-PowerPoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        // to be implemented *** contentType.put("TXT", "text/plain");
    }

    
    public String amalgameurPourMSWord() {
        String combinaisonExtensionWord = contentType.get("MS-Word (.docx)") + " OR " + contentType.get("MS-Word (.doc)");
        return combinaisonExtensionWord;
    }

    public String getExtensionInclure() {
        return extensionInclure;
    }

    public void setExtensionInclure(String extensionInclure) {
        this.extensionInclure = extensionInclure;
    }
}
