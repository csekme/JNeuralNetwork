package neuralnetwork;

import java.util.ArrayList;

/**
 * Rejtett neuronokat összefogó layer 
 * @author Csekme Krisztián | KSQFYZ
 * @see Layer
 */
public class HiddenLayer extends Layer {
	private static final long serialVersionUID = 2171878140001244149L;
    
	private HiddenLayer() {}
	
	/**
	 * <p>Gyártófüggvény, létrehozza a rejtett réteget, és hozzáadja a neurális hálózat layer tárolójához</p>
	 * @return létrejött kimeneti réteg
	 */
	public static HiddenLayer create() {
		HiddenLayer layer = new HiddenLayer();
		layer.neurons = new ArrayList<>();
		NeuralNetwork.getInstance().getLayers().add(layer);
		return layer;
	}
	
}
