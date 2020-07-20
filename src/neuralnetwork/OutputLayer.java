package neuralnetwork;

import java.util.ArrayList;

/**
 * A neurális hálózat kimeneti rétege OUTPUT
 * @author Csekme Krisztián | KSQFYZ
 */
public class OutputLayer extends Layer {

	private static final long serialVersionUID = -3956612607396562966L;

	//Célvektor
    double[] target = null;
    
    //Rejtett konstruktor
	private OutputLayer() {}
	
	/**
	 * <p>Gyártófüggvény, létrehozza az output layer-t, és hozzáadja a neurális hálózat layer tárolójához</p>
	 * @return létrejött kimeneti réteg
	 */
	public static OutputLayer create() {
		OutputLayer layer = new OutputLayer();
		layer.neurons = new ArrayList<>();
		NeuralNetwork.getInstance().getLayers().add(layer);
		return layer;
		}
	
	/**
	 * <p>Az elvont Layer működéshez hozzáadódik a hálózat kimenetelének dimenzionális beállítása.</p>
	 * <p>Kimeneti vektor méret beállítása.</p>
	 */
	@Override
    public void addNeuron(int number, Neuron.Activation a, Double bias, boolean biasWeightable) {
        for (int i=0; i<number; i++) {
            neurons.add(new Neuron(a, bias, biasWeightable));
        }
        numberOfOutput(number);
    }
    
	/**
	 * Beállítja a kimeneti vektor méretét.
	 * @param n kimeneti vektor mérete
	 * @see #target
	 */
    public void numberOfOutput(int n) {
        target = new double[n];
    }

    /**
     * Célvektor beállítása
     * @param t célvektor
     * @see #target
     */
    public void setTargetVector(double[] t) {

        if (target != null) {
            if (target.length != t.length) {
                throw new RuntimeException("A vektor dimenziója nem megfelelő");
            }
            for (int i = 0; i < target.length; i++) {
                target[i] = t[i];
            }
        }
    }
    
    /**
     * Visszaadja a célvektort
     * @return célvektor
     * @see #target
     */
    public double[] getTargetVector() {
    	return target;
    }

}
