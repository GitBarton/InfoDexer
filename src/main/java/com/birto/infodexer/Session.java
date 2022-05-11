package com.birto.infodexer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Session {

    private final Path pathToActiveDocs;
    private final Path pathToIndex;
    private final Path pathToJournaux;
    private String nomUsager;
    private String os;
    private LocalDateTime débutSession, finSession;
    private boolean estPremierLancement = true;
    static String fileSeparator = System.getProperty("file.separator");

    final static String BASEDOCSINIT = "baseDocsInit";
    final static String ACTIVEDOCS = "activeDocs";
    final static String JOURNAL = "journal";
    final static String INDEX = "index";
    static String jarPath;

    public Session(LocalDateTime débutSession, String nomUsager, String os) {
        this.nomUsager = nomUsager;
        this.débutSession = débutSession;
        this.os = os;
        estPremierLancement = !checkfolderExist(ACTIVEDOCS); //si Active doc n'existe pas c'est le premier lancement... 

        System.out.println("If true isActiveDocsEmpty  = First time run..." + estPremierLancement);

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

    }

    public static void init() throws UnsupportedEncodingException, MalformedURLException, IOException, URISyntaxException {

        //JarPath
        jarPath = getJarLaunchPath();

        //Check the folder(s) outside the JAR exists and need to created:        
        if (!checkfolderExist(ACTIVEDOCS)) // If not existant create it and copy else break. 
        {
            createDir(ACTIVEDOCS);  //  copyBaseDocstoActive("baseDocsInit");  //copy the docs from BaseDocsInit while at it....

            //Donc il faur copier baseDocsInit dans activeDocs à partir du Jar
            File fileDestination = new File("./activeDocs/");
            URL url = new URL("jar:file:" + jarPath + "/InfoDexer-1.0-jar-with-dependencies.jar!/baseDocsInit/");
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            FileUtils.copyJarResourcesRecursively(fileDestination, jarConnection);  //Debug         System.out.println(jarPath.toString()  );  System.out.println("our separator is : " + fileSeparator   );  //     \                               
        }

        //Index   
        if (!checkfolderExist(INDEX)) // If not existant create it and copy else break. 
        {
            createDir(INDEX);
        }

        //journal        
        if (!checkfolderExist(JOURNAL)) // If not existant create it and copy else break. 
        {
            createDir(JOURNAL);
        }

    }

    public static boolean checkfolderExist(String folderRelPath) {

        File directory = new File(folderRelPath + fileSeparator);
        boolean alreadyExist = directory.exists();
        System.out.println("In method checkfolderExist with folderPath to check existence = " + directory);

        return alreadyExist;
    }

    public static void createDir(String dirName) {

        File directory = new File(dirName + fileSeparator);
        if (!directory.exists()) {
            directory.mkdir();

            boolean existsDir = Files.isDirectory(directory.toPath());
            System.out.println("--------------------------------------------------------------------------------------------- ");
            System.out.println("Result from test - if directory test dirName Path Exist = " + dirName + existsDir);
            System.out.println("--------------------------------------------------------------------------------------------- ");
        }
    }

    public static String getJarLaunchPath() throws UnsupportedEncodingException {
        URL url = Session.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }

    public boolean isEstPremierLancement() {
        return estPremierLancement;
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

    public String getOs() {
        return os;
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
