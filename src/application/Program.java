package application;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.F;
import framework.ReadVersion;

/**
 * Az alkalmazás egyetlen belépési ponttal rendelkező indító osztálya
 * @author Csekme Krisztián | KSQFYZ
 * @see App
 */
public class Program {
	
	//Logger
	static Logger log = LogManager.getLogger(Program.class.getName());
      
    //Szín beállítások
    public static Color accentColor = new Color(13,127,217);
    public static Color borderColor = new Color(200, 120, 215); 
    
    //Build és verzió adatok
    public static String build = "";
    public static final String VERSION = "1.0.0"; 
    
    /**
     * Belépési pont, az alkalmazás az argumentumokra nem reagál
     * @param args the command line arguments
     */
    @SuppressWarnings("resource")
	public static void main(String args[]) {
    	
    	log.warn("Start application");
    	
    	//GIT commitok számának kiolvasása, és felhasználása build szám megállapítására
        File bf = new File( F.getApplicationLocation() + "/deploy/git.txt" );
        if (bf.exists()) {
            BufferedReader read = null;
            try {
                read = new BufferedReader(new FileReader(bf));
                build = read.readLine();
                
            } catch (FileNotFoundException e) {
                 build =  ReadVersion.getManifestInfo();
            } catch (IOException e) {
                 build =  ReadVersion.getManifestInfo();
            } finally {
                if (read!=null) {
                    try {
                        read.close();
                    } catch (IOException e) { 
                    }
                }
            }
        } else {
             build =  ReadVersion.getManifestInfo();
        }
        
        //Look and feel beállítások
        
		UIDefaults uiDefaults = UIManager.getDefaults();
		uiDefaults.put("TitledBorder.titleColor", new javax.swing.plaf.ColorUIResource(borderColor));
		
		try {
            UIManager.setLookAndFeel( "com.formdev.flatlaf.FlatIntelliJLaf" );
        } catch (Exception e) {
        	log.warn(e.getMessage());
        }
              
        /* A főablak példányosítása és megjelenítése */
        java.awt.EventQueue.invokeLater(() -> {
            App.getInstance().setVisible(true);
        });
    }
}
