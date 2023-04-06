package test;
//import org.jblas.DoubleMatrix; //Ellenőrzésre szánt külső könyvtár
import math.LinearMath; //Saját fejlesztésü csomag

/**
 * 
 * 
 * ===========================================================================================================
 *  !!! FIGYELEM !!
 *  AZ OSZTÁLY KIKOMMENTEZVE MERT AZ ELLENŐRZÉSHEZ HASZNÁLT JBLAS FÜGGŐSÉG TÖRLÉSRE KERÜLT ANNAK MÉRETE VÉGETT
 *  AZ OSZTÁLY FUTTATÁSÁHOZ LE KELL TÖLTENI A http://jblas.org/ HELYRŐL.
 * ===========================================================================================================
 * 
 * 
 * A Compare osztály egyetlen main belépési ponttal rendelkezik, valamint néhány
 * segéd függvénnyel. A célja az hogy a http://www.jblas.org ingyenesen
 * letölthető Lineáris Algebra könyvtár segítségével tesztelni lehessen a saját
 * fejlesztésű math csomagot
 *
 * @author CsK
 * @see math.LinearMath
 * @date 2019.12.03
 */
@SuppressWarnings("unused")
public class Compare {
//
//    //Teszt futtatása
//    public static void main(String[] args) {
//
//        System.out.println("Összehasonlító teszt a jblas (http://www.jblas.org/) lineáris algebra Java könyvtár, és a saját fejlesztésű math csomag között.");
//        //2 dimenziós mátrixok a műveletek ellenőrzésére 
//        double[][] A = new double[][]{{3, -5, 3}, {3, 21, 12}, {0, 5, 9}};
//        double[][] B = new double[][]{{21, 4}, {8, 11}, {7, -9}};
//
//        // 1. Mátrix szorzása
//        System.out.println("\n1 Mátrix összeszorzása AxB:");
//        System.out.println("============================================================");
//        System.out.println("A=");
//        print(A);
//        System.out.println("B=");
//        print(B);
//        System.out.println("------------------------------------------------------------");
//
//        //jBlas mátrix szorzás
//        {
//            DoubleMatrix dA = new DoubleMatrix(A);
//            DoubleMatrix dB = new DoubleMatrix(B);
//            DoubleMatrix result = new DoubleMatrix(3, 2);
//            dA.mmuli(dB, result);
//            System.out.println("jblas eredménye:");
//            print(result.toArray2());
//        }
//        //saját mátrix szorzás
//        {
//            System.out.println("\nsaját megoldás eredménye:");
//            print(LinearMath.multiplyMatrix(A, B));
//        }
//        System.out.println("------------------------------------------------------------");
//
//        // 1. Mátrix szorzása
//        System.out.println("\n2 Euklédeszi távolság V1,V2:");
//        System.out.println("============================================================");
//        double[] V1 = new double[]{4, 5, 12, -3, 9};
//        double[] V2 = new double[]{8, 22, 1, 0, -2};
//        System.out.print("V1= ");
//        print(V1);
//        System.out.print("V2= ");
//        print(V2);
//
//        DoubleMatrix dA1 = new DoubleMatrix(V1);
//        DoubleMatrix dB1 = new DoubleMatrix(V2);
//
//        //jBlas
//        {
//            System.out.println("\njblas eredménye:");
//            System.out.println("d=" + dA1.distance2(dB1));
//        }
//        //saját
//        {
//            System.out.println("\nsaját megoldás eredménye:");
//            System.out.println("d=" + LinearMath.euclideanDistance(V1, V2));
//        }
//
//        //Mátrix transponálása
//        double[][] A3 = new double[][]{{3, -5, 3}, {3, 21, 12}, {0, 5, 9}, {5, 7, -4}};
//        DoubleMatrix dA3 = new DoubleMatrix(A3);
//        System.out.println("\n Mátrix transponálás:");
//        System.out.println("============================================================");
//        System.out.println("A3=");
//        print(A3);
//        //jBlas
//        {
//            System.out.println("\njblas eredménye:");
//            dA3.transpose();
//            print(dA3.transpose().toArray2());
//        }
//        //Saját megoldás
//        {
//            System.out.println("\nsaját megoldás eredménye:");
//            print(LinearMath.transpose(A3));
//        
//        }
//   
//        System.out.print("\n\n\n\r");
//    }
//
//    /**
//     * Mátrix kinyomtatása konzolra
//     */
//    public static void print(double A[][]) {
//        for (double[] A1 : A) {
//            for (int k = 0; k < A[0].length; k++) {
//                System.out.print(A1[k] + "\t");
//            }
//            System.out.println();
//        }
//    }
//
//    /**
//     * Vektor kinyomtatása konzolra
//     */
//    public static void print(double A[]) {
//        for (int k = 0; k < A.length; k++) {
//            System.out.print(A[k]);
//            if (k<A.length-1){
//                System.out.print(", ");
//            }
//        }
//        System.out.println();
//    }

}
