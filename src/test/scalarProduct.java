package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Random;
//import org.jblas.DoubleMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import math.LinearMath;

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
 * Unit test a skaláris szorzat végrehajtására
 * @author CsK
 *
 */
@SuppressWarnings("unused")
class scalarProduct {

//	final Random rnd = new Random();
//	final int numberOfTest = 1000000;
//	double A[];
//	double B[];
//	DoubleMatrix dA;
//	DoubleMatrix dB;
//	
//	@BeforeEach
//	void setUp() throws Exception {
//		int length = rnd.nextInt(100);
//		A = new double[length];
//		B = new double[length];
//		for (int i=0; i<length; i++) {
//			A[i] = -100 + rnd.nextDouble() * 200;
//			B[i] = -100 + rnd.nextDouble() * 200;	
//			
//		}
//		dA = new DoubleMatrix(A);
//        dB = new DoubleMatrix(B);
//	}
//
//	@Test
//	void test() throws Exception {
//		for (int i=0; i<numberOfTest; i++) {
//			setUp();
//			double res = dA.dot(dB);
//			assertEquals(res, LinearMath.scalarProduct(A, B));
//		}
//	}
}
