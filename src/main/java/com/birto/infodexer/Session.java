package com.birto.infodexer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.CodeSource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Session {

    private Path pathToActiveDocs;
   // private final Path pathToBaseDocs;
    private Path pathToIndex;
    private Path pathToJournaux;
    private String nomUsager;
    private String os;
    private LocalDateTime débutSession, finSession;
    private boolean isActiveDocsEmpty;
    static  String  fileSeparator = System.getProperty("file.separator");
 
    final  static String  BASEDOCSINIT = "baseDocsInit";
    final static String  ACTIVEDOCS = "activeDocs";
    final static String  JOURNAL = "journal";
    final static String  INDEX  = "index"; 
    static String jarPath;    
   

          
    public Session(LocalDateTime débutSession, String nomUsager, String os) {
        this.nomUsager = nomUsager;
        this.débutSession = débutSession;
        this.os = os;
        
        try {
            Session.init();
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pathToActiveDocs = Paths.get("./activeDocs/");
        pathToIndex = Paths.get("./index/");
        pathToJournaux = Paths.get("./journal/");
        
        
    //    setpathActiveDocs(Constants.ACTIVE_DOCS_PATH); // will set the pathToActiveDocs
    //    setPathToBaseDocs(Constants.BASE_DOCS_PATH);  // will set the pathToBaseDocs
    //    setPathToIndex(Constants.INDEX_PATH);
    //    setPathToJournaux(Constants.JOURNAL_PATH);
    }

    
    
    
    
     public static void  init ( ) throws UnsupportedEncodingException, MalformedURLException, IOException, URISyntaxException {
    
    //JarPath
    	jarPath = getJarLaunchPath();                  
 	        
        //Check the folder(s) outside the JAR exists and need to created:        
        
        if (!checkfolderExist(ACTIVEDOCS)) // If not existant create it and copy else break. 
        {   createDir(ACTIVEDOCS);  //  copyBaseDocstoActive("baseDocsInit");  //copy the docs from BaseDocsInit while at it....
       
        //Donc il faur copier baseDocsInit dans activeDocs à partir du Jar
        File fileDestination = new File("./activeDocs/");      
        URL url = new URL("jar:file:" + jarPath + "/InfoDexer-1.0-jar-with-dependencies.jar!/baseDocsInit/");
        JarURLConnection jarConnection = (JarURLConnection)url.openConnection();    	
        FileUtils.copyJarResourcesRecursively(fileDestination, jarConnection);  //Debug         System.out.println(jarPath.toString()  );  System.out.println("our separator is : " + fileSeparator   );  //     \                               
        }

        
        //Index   
        if (!checkfolderExist(INDEX)) // If not existant create it and copy else break. 
        {  createDir(INDEX);
        }

        
        //journal        
        if (!checkfolderExist(JOURNAL)) // If not existant create it and copy else break. 
        {   createDir(JOURNAL);
        }
     

    }
        
        
  
 public static boolean checkfolderExist (String folderRelPath ) {
            
      File directory = new File(folderRelPath + fileSeparator );
      boolean alreadyExist = directory.exists();       System.out.println("In method checkfolderExist with folderPath to check existence = "+ directory);
      
      return alreadyExist; 
 }
 
 
    public static void createDir(String dirName) {

        File directory = new File(dirName + fileSeparator);
        if (!directory.exists()) {
            directory.mkdir();
            
            boolean existsDir = Files.isDirectory(directory.toPath());  
            System.out.println("--------------------------------------------------------------------------------------------- ");
            System.out.println("Result from test - if directory test dirName Path Exist = "+ dirName + existsDir);
            System.out.println("--------------------------------------------------------------------------------------------- ");
        }
    }
    
@Deprecated
    public static void copyBaseDocstoActive(String fileName) throws URISyntaxException {
    
        CodeSource src = Session.class.getProtectionDomain().getCodeSource();

        if (src != null) {
            ZipInputStream zip = null;
            try {
                URL jar = src.getLocation();        
         
                
                System.out.println("Printing Codesource (url jar) location" + jar);
                zip = new ZipInputStream(jar.openStream());
               
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null) {
                        break;
                    }
                    String name = e.getName();       //System.out.println("name: " + name + "  fileName + /: " + fileName + fileSeparator);

                    if (name.equals(fileName + "/")) { //we have found baseDocsInit !!!

                        URL baseDocsInit = Session.class.getClassLoader().getResource("baseDocsInit/");
                     
                        System.out.println("baseDocsInit to path" + baseDocsInit.getPath());
                        System.out.println("baseDocsInit to String " + baseDocsInit.toString());

             //HERE IS THE ISSUE ***********                                                   
                      copyFromJar( baseDocsInit.toString() ,  Paths.get("/activeDocs/"));                      
                              
          //            copyDirectory(fileName + fileSeparator, ACTIVEDOCS + fileSeparator);

                    }
                }
            }
            catch (IOException ex) {
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    zip.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            System.out.println("ERROR in COPYBASEDOCSTOACTIVEDOCS");
        }    
    }
    
    
    //copyFromJar("/path/to/the/template/in/jar", Paths.get("/tmp/from-jar"))
    public static void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
    	System.out.println(source);
    	System.out.println(target);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");    	
    	System.out.println(Session.class.getClassLoader().getResource("/activeDocs/").toURI());
    	    	
    	System.out.println(Session.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath());
      
    	String jjarpath = Session.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
    	
      
    	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
   
      // Obtenir le URI du jar plus BaseDocs
      URI resource = Session.class.getClassLoader().getResource(jjarpath + '!' + target).toURI();    
    
    
    FileSystem fileSystem = FileSystems.newFileSystem(
            resource,
            Collections.<String, String>emptyMap()
    );

    final Path jarPath = fileSystem.getPath(source);

    Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

        private Path currentTarget;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        	System.out.println(jarPath.relativize(dir).toString());
        	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            currentTarget = target.resolve(jarPath.relativize(dir).toString());
            Files.createDirectories(currentTarget);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }

    });
}
           
    
    
     public static String getJarLaunchPath() throws UnsupportedEncodingException {
      URL url = Session.class.getProtectionDomain().getCodeSource().getLocation();
      String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
      String parentPath = new File(jarPath).getParentFile().getPath();
      return parentPath;
   }
        
    
    public static Path stringToPath(String str) throws URISyntaxException {

//Util to convert from String to Path for the FSDIrectory.Open
        URI uri = null;
        try {
            uri = Session.class.getClassLoader().getResource(str).toURI();
        }
        catch (URISyntaxException ex) {
            System.out.println("uri:" + uri);
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Paths.get(uri);    //Convert the string to a Path
    }

    
    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) 
    		  throws IOException {
	    Files.walk(Paths.get(sourceDirectoryLocation))
	      .forEach(source -> {
	          Path destination = Paths.get(destinationDirectoryLocation, source.toString()
	            .substring(sourceDirectoryLocation.length()));
	          try {
	              Files.copy(source, destination);
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      });
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

    public Path getPathToActiveDocs() {
        return pathToActiveDocs;
    }

    public Path getPathToIndex() {
        return pathToIndex;
    }

    public Path getPathToJournaux() {
        return pathToJournaux;
    }

}
