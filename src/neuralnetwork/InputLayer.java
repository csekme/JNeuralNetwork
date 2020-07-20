package neuralnetwork;

import java.util.ArrayList;

/**
 * A neurális hálózat bemeneti rétege
 * @author Csekme Krisztián |KSQFYZ
 * @see Layer
 */
public class InputLayer extends Layer{
 
	private static final long serialVersionUID = 2536588270539460791L;

	private InputLayer() {}
	
	/**
	 * <p>Gyártófüggvény, létrehozza az input layer-t, és hozzáadja a neurális hálózat layer tárolójához</p>
	 * <p>a függvény előzetesen leüríti a layer tárolót</p>
	 * @return létrejött bemeneti réteg
	 */
	public static InputLayer create() {
		
		NeuralNetwork.getInstance().getLayers().clear();
		InputLayer layer = new InputLayer();
		layer.neurons = new ArrayList<>();
		NeuralNetwork.getInstance().getLayers().add(layer);
		return layer;
	}
	
}
