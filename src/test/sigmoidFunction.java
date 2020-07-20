package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import neuralnetwork.Neuron;
import neuralnetwork.Sigmoid;
class sigmoidFunction {

	double[] vector = new double[11];
	
	@BeforeEach
	void setUp() throws Exception {
		
		for (int i=0; i<vector.length; i++) {
			vector[i] = (-1*(vector.length / 2)) + i;
		}
	}	
	
	@Test
	void test() {
		
		Neuron n = new Neuron();
		n.activationModule = new Sigmoid();		
		for (int i=	0; i<vector.length; i++) {
			System.out.println( vector[i] + ":=" + n.activate(vector[i]) );
		}		
		//fail("Not yet implemented");
	}

}
