package framework.ribbonmenu;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
public class Q {
 
	public static final byte  LIST = 0;
	public static final byte  SCREEN = 1;
	public static final byte  CHOOSE = 2;
	public static final byte  SHOW = 3;
	public static final byte  INSERT = 4;
	public static final byte  UPDATE = 5;
	public static final byte  DELETE = 6;
	
	public static final ImageIcon star_cell = Q.accessImageFile("star/star_cellrenderer.png");
	
	public static final String EMPTY_STRING = "";
	

	public static void outln(Object value) {
		System.out.println(LocalDateTime.now() + "\t" + value);
	}

	public static void errln(Object value) {
		System.err.println(LocalDateTime.now() + "\t" + value);
	}
	

   
	public static File getFileFromResources(String fileName) {

		ClassLoader classLoader = Q.class.getClassLoader();

		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		}
		return new File(resource.getFile());

	}
    
 
	public static URL getURLFromResources(String fileName) {
		if (fileName.startsWith("/") || fileName.startsWith("\\")) {
			fileName = fileName.substring(1, fileName.length());
		}

		ClassLoader classLoader = Q.class.getClassLoader();

		URL resource = classLoader.getResource(fileName);

		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		}
		return resource;

	}

    /**
     * Erőforrás könyvtárból /images/ kinyerni a képeket
     * @param filename
     * @return
     */
    public static ImageIcon accessImageFile(String filename) {
    	@SuppressWarnings("resource")
		InputStream in = accessStream(filename);
    	BufferedImage im = null;
    	try {
			im = ImageIO.read(in);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in!=null)
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	ImageIcon imageIcon = new ImageIcon(im);
    	 
    	return imageIcon;
    }
    

    /**
     * Hozzáférés a resources könyvtárhoz
     * @param filename
     * @return
     */
    public static InputStream accessStream(String filename) {
         
        // this is the path within the jar file
        InputStream input = Q.class.getResourceAsStream("/resources/" + filename);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Q.class.getClassLoader().getResourceAsStream("/resources/" + filename);
        }

        return input;
    }
    
    /**
     * A program futtatási helye
     * @return
     */
    public static String getApplicationLocation() {
    	return System.getProperty("user.dir");
    }
    
    /**
     * A Desktop Api segĂ­tsĂ©gĂ©vel (http://java.sun.com/developer/technicalArticles/J2SE/Desktop/javase6/desktop_api/)
     * megnyitja szerkesztĂ©sre az Ăˇtadott fĂˇjlt.
     * @param filename ezt nyitja meg
     * @throws Exception
     */
    public static void openFileInOS(String filename) {
    	Q.outln("Open file in OS: "+filename);
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
              Q.errln("Desktop nem támogatott!");   
            }
        } else {
        	Q.errln("Desktop nem támogatott!");   
        }
    }
    
    /**
     * Felhasználó home könyvtára
     * @return
     */
    public static String getUserHome() {
    	return System.getProperty("user.home");
    }
    
 
    public static String getUserDocuments() {
    	return System.getProperty("user.home") + "/documents/";
    }
    
    /**
     * Garbage Collector explicit indítása
     */
    public static void FREE() {
		System.gc();
		System.runFinalization(); 
    }
}
