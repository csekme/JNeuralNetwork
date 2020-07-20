package framework.ribbonmenu;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Util {
    
	
    public static ImageIcon accessImageFile(String filename) {
    	@SuppressWarnings("resource")
		InputStream in = accessStream("images/" + filename);
    	BufferedImage im = null;
    	try {
			im = ImageIO.read(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (in!=null)
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	ImageIcon imageIcon = new ImageIcon(im);
    	 
    	return imageIcon;
    }

    public static InputStream accessStream(String filename) {
         
        // this is the path within the jar file
        InputStream input = Util.class.getResourceAsStream("/resources/" + filename);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Util.class.getClassLoader().getResourceAsStream("/resources/" + filename);
        }

        return input;
    }

	//Load font from source
	public static Font loadFont(String fontPath, float size) {
		InputStream is = Util.class.getResourceAsStream(fontPath);
		try {
			 Font f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
			 return f;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
