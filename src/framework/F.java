package framework;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import application.Program;
/**
 * Ebben az osztályban statikus konstansok vannak amiket könnyen elérhetünk bárhonnan az alkalmazásfejlesztés során
 * A gyors rapid fejlesztéshez szükséges általános függvények.
 * @author Csekme Krisztián
 * @version 1.0
 */
public class F {
 
	/* Erőforrás biztos üres string */
	public static final String EMPTY_STRING = "";
	
	/**
	 * Lebegőpontos számot megformázza a paraméterben kapott pontosságig
	 * és string formátumba adja vissza
	 * @param value átalakítandó szám
	 * @param accuracy a lebegőpontos szám pontossága (tízedes törtrész)
	 * @exception RuntimeException abban az esetben ha a pontosság kisebb mint 1
	 * @return megformázott lebegőpontos szám szövegként ábrázolva
	 */
	public static String doubleFormat(double value, int accuracy) {
		if (accuracy<1) {
			throw new RuntimeException("Minimum 1 tizedes pontosságot meg kell adni");
		}
		String acc="";
		for (int i=0; i<accuracy; i++) {
			acc+="#";
		}
		return new DecimalFormat("##." + acc).format(value);
	}
	
	
	/**
	 * Kiíratás standart out-ra System.out.println rövidítése
	 * @param value objektum a kimenetre
	 */
	public static void outln(Object value) {
		System.out.println(LocalDateTime.now() + "\t" + value);
	}
	
	/**
	 * Kiíratás standard err-re System.err.println rövidítése
	 * @param value objektum a kimenetre
	 */
	public static void errln(Object value) {
		System.err.println(LocalDateTime.now() + "\t" + value);
	}
	
	
    /**
     * Erőforrás betöltése
     * @param fileName src/main/resources alatt lévő erőforrás elérése
     * @return erőforrás fájl
     */
    public static File getFileFromResources(String fileName) {

        ClassLoader classLoader = Program.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
        	throw new IllegalArgumentException("a fájl nem található!");
        }
        
        return new File(resource.getFile());
        }
    
    /**
     * Erőforrás betöltése
     * @param fileName src/main/resources alatt lévő erőforrás elérése
     * @exception IllegalArgumentException a fájl nem található
     * @return erőforrás fájl
     */
    public static URL getURLFromResources(String fileName) {
		if (fileName.startsWith("/") || fileName.startsWith("\\")) {
			fileName = fileName.substring(1, fileName.length());
		}

		ClassLoader classLoader = Program.class.getClassLoader();

		URL resource = classLoader.getResource(fileName);

		if (resource == null) {
			throw new IllegalArgumentException("a fájl nem található!");
		}
		return resource;
	}


    /**
     * Hozzáférni a resources könyvtárhoz
     * @param filename keresendő fájl a resources könyvtárban
     * @return megadott fájl adatfolyam
     */
    public static InputStream accessStream(String filename) {
         
        // this is the path within the jar file
        InputStream input = Program.class.getResourceAsStream("/resources/" + filename);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Program.class.getClassLoader().getResourceAsStream("/resources/" + filename);
        }

        return input;
    }
    
    /**
     * A program futtatási helye
     * @return az elindított program base könyvtára
     */
    public static String getApplicationLocation() {
    	return System.getProperty("user.dir");
    }
    
    /**
     * A Desktop Api segítségével (http://java.sun.com/developer/technicalArticles/J2SE/Desktop/javase6/desktop_api/)
     * megnyitja szerkesztésre az átadott fájlt.
     * @param filename ezt nyitja meg
     */
    public static void openFileInOS(String filename) {
    	F.outln("Open file in OS: "+filename);
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    //desktop.open(new File(filename));
                	File f = new File(filename);
                	if (f.exists())
                		desktop.open(f.getAbsoluteFile());
                	else
                		System.out.println("Fájl nem található! "+f.getAbsoluteFile());
                		
                } catch (IOException e) {
                  e.printStackTrace();
                }
            } else {
              F.errln("Desktop nem támogatott!");   
            }
        } else {
        	F.errln("Desktop nem támogatott!");   
        }
    }
    
    /**
     * Felhasználó home könyvtára
     * @return A felhasználó home könyvtára
     */
    public static String getUserHome() {
    	return System.getProperty("user.home");
    }
    
    /**
     * Felhasználó dokumentum könyvtára
     * @return ../documents/
     */
    public static String getUserDocuments() {
    	return System.getProperty("user.home") + "/documents/";
    }
    
    /**
     * Visszaadja az asztal fájlrendszeri elérését
     * Angil illetve Magyar operációs rendszereken működik
     * @return A felhasználó asztalának útvonala
     */
    public static String getDesktopPath() {
        String p = System.getProperty("user.home") + "/Desktop";
        if (new File(p).exists()) {
            return p;
        } 
        p = System.getProperty("user.home") + "/Asztal";
        
        return p;
    }
 
    /**
     * JVM szemétgyűjtő explicit hívása 
     */
    public static void FREE() {
		System.gc();
		System.runFinalization(); 
    }
    
    
    /**
     * Visszaadja az erőforrás fájljait
     * @param folder megadott erőforrás könyvtár
     * @return fájlista
     */
    public static File[] getResourceFolderFiles (String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        System.err.println(path);
        return new File(path).listFiles();
    }
}