package math;
/**
 * Lineáris algebra műveletek.
 * Mátrix, és vektor műveletrkre létrehozott osztály
 * @author Csekme Krisztián | KSQFYZ
 * @see Interpolation
 */
public class LinearMath {

    /**
     * Elem hozzáadása a vektorhoz a pos paraméter gondoskodik a hozzáfűzendő elem pozíciójáról 
     * @param V1 A bővítendő vektor
     * @param element Hozzáadandó elem
     * @param pos true esetén az új elem a lista elejére kerül false esetén a végére
     * @return elemmel bővített vektor
     */
    public static double[] addElementToVector(double V1[], double element, boolean pos) {
         
        double[] res = new double[V1.length + 1];
        for (int i=pos?1:0; i<(pos?res.length:V1.length); i++) {          
            
            res[i] = V1[i-(pos?1:0)];
        }
        res[pos?0:V1.length] = element;
        return res;
    }
    
    /**
     * Két egyező méretű vektor összeadása
     * @param v1 paraméterben érkező egyik vektor
     * @param v2 paraméterben érkező másik vektor
     * @return a két vektor összege
     * @exception RuntimeException amennyiben a vektorok nem egyező méretűek
     */
    public static double[] addVector(double[] v1, double[] v2) {
        if (v1.length!=v2.length) {
            throw new RuntimeException("Különbözik a vektorok dimenziója");
        }
        
        for (int i=0; i<v1.length; i++) {
            v1[i]+=v2[i];
        }
        
        return v1;
    }
    
    /**
     * <b>Vektor szorzása skalárral mennyiséggel</b>
     * @param V1 a szorzandó vektor
     * @param scalar a skalár mellyel a vektor szorzásra kerül
     * @return V1 vektor a szorzott kalármennyiséggel
     */
    public static double[] multiplyWithScalar(double V1[], double scalar) {
        for (int i=0; i<V1.length; i++) {
            V1[i]*=scalar;
        }
        return V1;
    }
    
    /**
     * <b>Az alábbi függvény két vektor skaláris szorzatát számolja ki</b>
     * @param V1 elsőszámú vektor dupla pontosságú tömb
     * @param V2 másodszámú vektor dupla pontosságú tömb
     * @exception RuntimeException a vektorok számosságának különbsége esetén
     * @return skaláris szorzat eredménye dupla pontossággal
     */
    public static double scalarProduct(double V1[], double V2[]) {
        double sp = 0.0;
        if (V1.length != V2.length) {
            throw new RuntimeException("A vektorok nem egyforma számosságúak");
        }
        
        for (int r=0; r<V1.length; r++) {
            sp += V1[r] * V2[r];
        }
        return sp;
    }

    /**
     * Mátrix szorzása mátrixal A = (n x k); B = (k x m)
     * @param A jelű mátrix
     * @param B jelű mátrix
     * @return összeszorzott mátrixok
     */
    public static double[][] multiplyMatrix(double A[][], double B[][]) {

		int An = A.length;
		int Ak = A[0].length;
		int Bn = B.length;
		int Bk = B[0].length;

		if (Ak != Bn) {
			throw new RuntimeException("Mátrixok dimenziója nem egyező!");
		}
		double m[][] = new double[An][Bk];
		for (int z = 0; z < An; z++) {
			for (int n = 0; n < Bk; n++) {
				for (int k = 0; k < Ak; k++) {
					m[z][n] += A[z][k] * B[k][n];
				}
			}
		}
		return m;
    }
    
    /**
     * Mátrix elforgatása transzponálása
     * @param A jelű mátrix amit transzponálni kell
     * @return Transzponált mátrix
     */
    public static double[][] transpose(double A[][]) {
        int n = A.length;
        int k = A[0].length;
        double[][] B = new double[k][n];
        for (int i=0; i<n; i++) {
            for (int j=0; j<k; j++) {
                B[j][i] = A[i][j];
            }
        }
        return B;
    }

    /**
     * Két mátrix összeadása 
     * @param A jelű mátrix
     * @param B jelű mátrix
     * @return Összeadott mátrix
     */
    public static double[][] addMatrix(double A[][], double B[][]) {

        int An = A.length;
        int Ak = A[0].length;
        int Bn = B.length;
        int Bk = B[0].length;

        if (An != Bn || Ak != Bk) {
            throw new RuntimeException("Mátrixok dimenziója nem egyezik!");
        }

        double m[][] = new double[A.length][A[0].length];
        for (int n = 0; n < A.length; n++) {
            for (int k = 0; k < A[0].length; k++) {
                m[n][k] = A[n][k] + B[n][k];
            }
        }

        return m;
    }

    /**
     * Mátrix beszorzása skalár mennyiséggel
     * n*k mátrix szorzata skalárral
     * @param A jelű mátrix
     * @param scalar Skaláris mennyiség
     * @return Skalárral beszorzott mátrix
     */
    public static double[][] multiplyWithScalar(double A[][], double scalar) {

        double m[][] = new double[A.length][A[0].length];

        for (int n = 0; n < A.length; n++) {
            for (int k = 0; k < A[0].length; k++) {
                m[n][k] = A[n][k] * scalar;
            }
        }

        return m;
    }

    /**
     * Mátrix kinyomtatása sztenderd kimenetre
     * @param A jelű mátrix
     */
    public static void printMatrix(double A[][]) {
        System.out.println("Matrix:");
        System.out.println("-------------------------------------");
        for (double[] A1 : A) {
            for (int k = 0; k < A[0].length; k++) {
                System.out.print(A1[k] + "\t");
            }
            System.out.println();
        }
    }
    
    /**
     * Vektor kinyomtatása sztenderd kimenetre
     * @param title Vektor elnevezése
     * @param V a kiírandó vektor
     */
    public static void printVector(String title, double[] V) {
    	
        System.out.print(title + ": ");
        for (int k = 0; k < V.length; k++) {
                System.out.print(V[k]);
                if (k<V.length-1) {
                	System.out.print(", ");
                }
            }
            System.out.println();   
    }
    
    /**
     * Vektor átalakítása szöveggé
     * @param V az átalakítandó vektor
     * @return Szöveggé alakított vektor
     */
    public static String vectorToString(double[] V) {
    	String v="";
    	for (int k = 0; k < V.length; k++) {
            v+=(V[k]);
            if (k<V.length-1) {
            	v+=(",");
            }
        }
    	return v;
    }

    /**
     * Kiszámítja az Euklideszi távolságát két vektornak 
     * (forrás: Dr. Pintér István Intelligens rendszerek oktatási segédlet)
     * @param A 1. vektor jellemzően az alap
     * @param B 2. vektor
     * @exception RuntimeException a vektorok dimenziójának nem egyezősége esetén
     * @return Euklideszi távolság
     */
    public static double euclideanDistance(double A[], double B[]) {
        if (A.length != B.length) {
            throw new RuntimeException("A vektorok dimenziója nem egyezik!");
        }
        double r = 0.0;
        int i=0;
        while (i<A.length) {
            r+= (A[i]-B[i]) * (A[i]-B[i]);
            i++;
        }
        return Math.sqrt(r);
    }
    
    /**
     * Kiszámítja a Manhattan távolságát két vektornak 
     * @param A 1. vektor jellemzően az alap
     * @param B 2. vektor
     * @exception RuntimeException a vektorok dimenziójának nem egyezősége esetén
     * @return Manhattan távolság
     */
    public static double manhattanDistance(double A[], double B[]) {
        if (A.length != B.length) {
            throw new RuntimeException("A vektorok dimenziója nem egyezik!");
        }
        double r = 0.0;
        int i=0;
        while(i<A.length) {
            r+=Math.abs( A[i] - B[i] );
            i++;
        }
        return r;
    }
    
    /**
     * Kiszámítja két vektor maximális távolságát
     * @param A 1. vektor jellemzően az alap
     * @param B 2. vektor
     * @exception RuntimeException a vektorok dimenziójának nem egyezősége esetén
     * @return Vektorok maximális távolsága
     */
    public static double maxDistance(double A[], double B[]) {
        if (A.length != B.length) {
            throw new RuntimeException("A vektorok dimenziója nem egyezik!");
        }
        int i=0;
        while(i<A.length) {
            A[i] = Math.abs( A[i] - B[i] );
            i++;
        }
        return Utils.max(A);
    }
    
    
	/**
	 * Vektor elemeit egymás mellé téve egy long számot ad
	 * pl.: 1,5,3,5 ből lesz: 1535
	 * @param vector átalakítandó számsorozat 
	 * @return számmá átalakított vektor
	 */
	public static long makeVectorToSerial(double[] vector) {
		long res=0;
		int dec=1;
		for (int i=vector.length-1; i>=0; i--) {
			res+=vector[i]*dec;			
			for (int d=0; d<((int)(Math.log(vector[i]))); d++) {
				dec*=10;
			}
		}		  
		return res;
	}
}
