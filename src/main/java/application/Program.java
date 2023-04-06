package application;
import neuralnetwork.DataSet;
import neuralnetwork.GradiantTrainer;
import neuralnetwork.HiddenLayer;
import neuralnetwork.InputLayer;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;
import neuralnetwork.OutputLayer;
import neuralnetwork.Trace;
import neuralnetwork.Trainer;

/**
 * Az alkalmazás egyetlen belépési ponttal rendelkező indító osztálya
 * @author Csekme Krisztián | KSQFYZ
 * @see App
 */
public class Program {

    //Build és verzió adatok
    public static String build = "";
    public static final String VERSION = "1.1.0";
    
    /**
     * Belépési pont, az alkalmazás az argumentumokra nem reagál
     * @param args the command line arguments
     */
	public static void main(String args[]) {
    	
		System.out.println("Neural Network Java SE Implementation.");
		System.out.println("VERSION=" + VERSION);
		

    	NeuralNetwork nn = NeuralNetwork.create();
    	InputLayer.create().addNeuron(2, Neuron.Activation.SIGMOID, 1.0, true);
    	HiddenLayer.create().addNeuron(3, Neuron.Activation.SIGMOID, 1.0, true);
    	OutputLayer.create().addNeuron(1, Neuron.Activation.SIGMOID, 1.0, true);
    	
    	nn.initWeights();
 
    	Trainer tr = new GradiantTrainer();
    	nn.setTrainer(tr);
    	tr.getDataSet().add(DataSet.create(new double[] {0,0},new double[] {0}));
    	tr.getDataSet().add(DataSet.create(new double[] {0,1},new double[] {1}));
    	tr.getDataSet().add(DataSet.create(new double[] {1,0},new double[] {1}));
    	tr.getDataSet().add(DataSet.create(new double[] {1,1},new double[] {0}));
    	tr.setEpoch(20000);
    	tr.setRandomSequence(true);
    	tr.run();
    	tr.join();
    	
    	Trace.trace("\n\nResult:");
    	nn.stimulus(new double[] {0, 0});
    	Trace.trace(OutputLayer.getInstance().toString());
    	nn.stimulus(new double[] {0, 1});
    	Trace.trace(OutputLayer.getInstance().toString());
    	nn.stimulus(new double[] {1, 0});
    	Trace.trace(OutputLayer.getInstance().toString());
    	nn.stimulus(new double[] {1, 1});
    	Trace.trace(OutputLayer.getInstance().toString());

    	
    	
    }
}
