package math;
/**
 * Interpolációs matematikai függvényeket megvalósító osztály
 * @author Csekme Krisztián | KSQFYZ
 */
public class Interpolation {

	/**
	 * Lagrange interpoláció
	 * https://mathworld.wolfram.com/LagrangeInterpolatingPolynomial.html
	 * @param x a teljes x1..xn értéktartomány
	 * @param y a teljes x-hez tartozó y1..yn értéktartomány
	 * @param X a keresett x-hez tartozó Y
	 * @return Y az interpoláció eredménye
	 */
	public static double Lagrange(double[] x, double[] y, double X) {
		double sum = 0;
		double prod = 1;
		
	    for (int i = 0; i < x.length; i++) {
	        for (int j = 0; j < x.length; j++) {
	            if (j != i) {
	                prod *= (X - x[j]) / (x[i] - x[j]);
	            }
	        }
	        sum += prod * y[i];
	        prod = 1;
	    }
	    
	    return sum;
	}
	
	/**
	 * Lineáris interpoláció, két szomszédos pont között (x1:x2) intervallumon a keresett Y értékének meghatározására
	 * @param x1 "bal oldali" szomszédos x pont
	 * @param y1 "bal oldali" szomszédos y pont
	 * @param x2 "jobb oldali" szomszédos x pont
	 * @param y2 "jobb oldali" szomszédos y pont
	 * @param X keresett Y-hoz tartozó X pont
	 * @return keresett Y
	 */
	public static double linear(double x1, double y1, double x2, double y2, double X) {
		
		return y1 + ( ( (X-x1) * (y2-y1) ) / (x2-x1)  );
		
	}
}
