package plotter;

import java.awt.Color;
import java.io.Serializable;

/**
 * Nagypontosságú kétdimeziós adatpontokat leíró osztály
 * @author Csekme Krisztián | KSQFYZ
 */
public class Point implements Serializable {

	private static final long serialVersionUID = -2118016722231282661L;
	
	Plotter.PoligonForm form;
	
	double x;
    double y;
    Double size;
    
    
    boolean opaque = false;
    boolean showValue = false;
    
    Color color = null;
    
    public Point(double x, double y, Plotter.PoligonForm form) {
        this.x = x;
        this.y = y;
        this.form = form;
    }
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.form = Plotter.PoligonForm.CIRCLE;
    }
    
    public Point(double x, double y, Color color) {
    	this.color = color;
        this.x = x;
        this.y = y;
        this.form = Plotter.PoligonForm.CIRCLE;
    }
    
    public Point(double x, double y, Color color, Plotter.PoligonForm form, boolean opaque) {
      	this.color = color;
        this.x = x;
        this.y = y;
        this.form = form;
        this.opaque = opaque;
    }
    
    public Point(double x, double y, Color color, Plotter.PoligonForm form) {
    	this.color = color;
        this.x = x;
        this.y = y;
        this.form = form;
    }
    
    public void setColor(Color color) {
    	this.color = color;
    }
    
    public Color getColor() {
    	return this.color;
    }
    
    public boolean hasColor() {
    	return this.color!=null;
    }

	public boolean isOpaque() {
		return opaque;
	}

	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}
	
    
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isShowValue() {
		return showValue;
	}

	public void setShowValue(boolean showValue) {
		this.showValue = showValue;
	}

	@Override
	public String toString() {
		return "x="+x+" y="+y;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}
	
	
    
}
