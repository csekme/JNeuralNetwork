package plotter;

import java.io.Serializable;

/**
 * Tartományokat leíró osztály (min/max) (tól/ig)
 * @author Csekme Krisztián |KSQFYZ
 */
public class Range implements Serializable {
 
	private static final long serialVersionUID = 6448443206402447921L;
	
	private double from;
    private double to;
    private double div;
    
    public Range(float from, float to) {
        this.from = from;
        this.to = to;
    }
    
    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }
    /**
     * Kiszámítjuk a pixel nagyságot a tartományra
     * @param componentSideSize a komponens oldal (szélessége/magassása) egész számként
     * @return 
     */
    public double calculateDiv (int componentSideSize) {
        this.div = componentSideSize / ( Math.abs( from - to ) );
        return div; 
    }
    
    /**
     * Megvizsgáljuk hogy van-e kirajzolandó origó
     * @return 
     */
    public boolean hasOrigo() {
        return from<=0 && to>=0;
    }
    
    /**
     * Pixel távolság a tartomány kezdetétől
     * @param value
     * @return  
     */
    public Double pixelFrom(double value) {
        if (value>=from && value<=to) {
            return (double) ( Math.abs(value-from) * div);
        }
        return null; 
    }
    
    public void setRange(double from, double to) {
        this.from = from;
        this.to = to;
    }
    
    public double getDiv() {
        return this.div;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }
 
    
    
}
