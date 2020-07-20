package math;

/**
 * Segítő osztály, keresés rendezés matematikai konverziók
 * @author Csekme Krisztián | KSQFYZ
 */
public class Utils {


    /**
     * Maximum keresés
     * @param A vektor
     * @return A legnagyobb értéke
     */
    public static double max(double[] A) {
        if (A.length==1) {
            return A[0];
        }
        double max = A[0];
        int i = 1;
        while (i<A.length) {
            if (A[i]>max) {
                max = A[i];
            }
            i++;
        }
        return max;
    }
    

}
