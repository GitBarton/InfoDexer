package com.birto.infodexer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Util {
   static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   static List<String> fichierExtension = new ArrayList<>(List.of("PDF", "MS-Excel","MS-Word","MS-PowerPoint", "XML", "JSON", "ZIP"));
   final static String newline = "\n";
   
    public static String formatLocalDateTime(LocalDateTime dateToFormat) {         
        return dateToFormat.format(FORMATTER);    
    }
    
    

    
    
    
}
