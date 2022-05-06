package com.birto.infodexer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Session {
    private Path pathToActiveDocs, pathToBaseDocs, pathToIndex, pathToJournaux;
    private String nomUsager;
    private String os;
    private LocalDateTime débutSession, finSession;
    private boolean isActiveDocsEmpty;

          
    public Session(LocalDateTime débutSession, String nomUsager, String os) {
        this.nomUsager = nomUsager;
        this.débutSession = débutSession;
        this.os = os;
        setpathActiveDocs(Constants.ACTIVE_DOCS_PATH); // will set the pathToActiveDocs
        setPathToBaseDocs(Constants.BASE_DOCS_PATH);  // will set the pathToBaseDocs
        setPathToIndex(Constants.INDEX_PATH);
        setPathToJournaux(Constants.JOURNAL_PATH);
    }

    
   // https://stackoverflow.com/questions/22605666/java-access-files-in-jar-causes-java-nio-file-filesystemnotfoundexception
    
    /*
    https://stackoverflow.com/questions/11012819/how-can-i-access-a-folder-inside-of-a-resource-folder-from-inside-my-jar-file  
    
    final String path = "sample/folder";
final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

if(jarFile.isFile()) {  // Run with JAR file
    final JarFile jar = new JarFile(jarFile);
    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
    while(entries.hasMoreElements()) {
        final String name = entries.nextElement().getName();
        if (name.startsWith(path + "/")) { //filter according to the path
            System.out.println(name);
        }
    }
    jar.close();
} else { // Run with IDE
    final URL url = Launcher.class.getResource("/" + path);
    if (url != null) {
        try {
            final File apps = new File(url.toURI());
            for (File app : apps.listFiles()) {
                System.out.println(app);
            }
        } catch (URISyntaxException ex) {
            // never happens
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    */
    
    
    
   /* 
    
    public File t () {
    
        try {
            final Map<String, String> env = new HashMap<>();
            final String[] array = uri.toString().split("!");
            final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
            final Path path = fs.getPath(array[1]);
        }
        catch (IOException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }   
    
    */
    
    
    public  Path stringToPath(String str) {      
                
   testFileGetPath(str);        
        
//Util to convert from String to Path for the FSDIrectory.Open
               String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
               System.out.println("jarPath===="+jarPath);
               System.out.println("jarPath===="+jarPath);
               System.out.println("jarPath===="+jarPath);
      
               
               
        URI uri = null;
        try {        
               
       uri  = SessionControlleur.class.getResource(str).toURI();
       
       
        }
        catch (URISyntaxException ex) {
            System.out.println("uri:" + uri);
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    
     return Paths.get(uri);    //Convert the string to a Path
    }
    
    
    public void testFileGetPath(String str_test){
    
    File file = null;
    String resource = str_test;
URL res = getClass().getResource(resource);

if (res.getProtocol().equals("jar")) {
    try {
        InputStream input = getClass().getResourceAsStream(resource);
        file = File.createTempFile("tempfile", ".tmp");
                
        System.out.println(file.getAbsolutePath());
        
        
    } catch (IOException ex) {
       
    }
} else {
    //this will probably work in your IDE, but not from a JAR
    file = new File(res.getFile());
}

if (file != null && !file.exists()) {
    throw new RuntimeException("Error: File " + file + " not found!");
}    
          
    }
    

    public Path getPathToIndex() {
        return pathToIndex;
    }

    public void setPathToIndex(String strLocation) {
        this.pathToIndex = stringToPath(strLocation);
    }

    public Path getPathToJournaux() {
        return pathToJournaux;
    }

    public void setPathToJournaux(String strLocation) {
        this.pathToJournaux = stringToPath(strLocation);
    }

    public Path getPathToBaseDocs() {
        return pathToBaseDocs;
    }

    public void setPathToBaseDocs(String strLocation) {
        this.pathToBaseDocs = stringToPath(strLocation);
    }

    public Path getpathActiveDocs() {
        return pathToActiveDocs;
    }

    public void setpathActiveDocs(String strLocation) {
        this.pathToActiveDocs = stringToPath(strLocation);
    }

    private static boolean isDirEmpty(final Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

    public boolean isActiveDocsEmpty() throws URISyntaxException, IOException {
        // How can I test if ActiveDocs Exists and not empty?
        try {
            System.out.println("Let\'s check if the activeDocs is empty or not :: " + isDirEmpty(pathToActiveDocs));
        }
        catch (IOException ex) {
            Logger.getLogger(SessionControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDirEmpty(pathToActiveDocs);
    }

    public void setIsActiveDocsEmpty(boolean isActiveDocsEmpty) throws IOException {
        this.isActiveDocsEmpty = isDirEmpty(pathToActiveDocs);
    }

    public String getNomUsager() {
        return nomUsager;
    }
    public void setNomUsager(String nomUsager) {
        this.nomUsager = nomUsager;
    }
    public LocalDateTime getDébutSession() {
        return débutSession;
    }
    public void setDébutSession(LocalDateTime débutSession) {
        this.débutSession = débutSession;
    }
    public LocalDateTime getFinSession() {
        return finSession;
    }
    public void setFinSession(LocalDateTime finSession) {
        this.finSession = finSession;
    }
    public String getOs() {        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }

}
