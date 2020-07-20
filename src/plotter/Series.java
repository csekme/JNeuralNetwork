package plotter;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Adatsorozatot összefogó, és annak néhány tulajdonságát leíró osztály
 * @see Plotter
 * @author Csekme Krisztián |KSQFYZ
 */
public class Series implements Serializable {

 	private static final long serialVersionUID = -8572065413435917398L;
	
 	private Color clPoligons = new Color(255, 0, 0);
    private String title;
    public List<Point> polygons = new ArrayList<>();

    public Color getClPoligons() {
        return clPoligons;
    }

    public void setClPoligons(Color clPoligons) {
        this.clPoligons = clPoligons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
