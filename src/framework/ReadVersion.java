package framework;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * A segédosztály segítségével a futtatható JAR esetén ki tudjuk olcasni a manifesztációban
 * tárolt implementációs adatokat
 * @author Csekme Krisztián | KSQFYZ
 *
 */
public class ReadVersion {
      
    public static String getManifestInfo() {
        @SuppressWarnings("rawtypes")
		Enumeration resEnum;
        try {
            resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                try {
                    URL url = (URL)resEnum.nextElement();
                    InputStream is = url.openStream();
                    if (is != null) {
                        Manifest manifest = new Manifest(is);
                        Attributes mainAttribs = manifest.getMainAttributes();
                        String version = mainAttribs.getValue("Implementation-Version");
                        if(version != null) {
                            return version;
                        }
                    }
                }
                catch (Exception e) {
                    //Ellimináljuk nincs jelentősége
                }
            }
        } catch (IOException e1) {
            // Ellimináljuk a rosszul megadott manifeszt adatok nem érdekesek
        }
        return null; 
    }
    }