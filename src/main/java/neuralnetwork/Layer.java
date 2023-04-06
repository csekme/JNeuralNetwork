package neuralnetwork;
import java.io.Serializable;
import java.util.List;
/**
 * <p>Neurális hálózat rétegének felületét tartalmát leíró elvont osztály.</p>
 * <p>A modularitás végett a neuron aktivációs függvényként ebből az osztályból</p>
 * <p>készíti el valósítja meg a konkrét Aktivációs függvény-t.</p>
 * @author Csekme Krisztián | KSQFYZ
 * @see Linear
 * @see ReLU
 * @see Sigmoid
 * @see SoftPlus
 * @see TanH
 */
public abstract class Layer  implements Serializable {

	private static final long serialVersionUID = -6118165678351631120L;

	protected List<Neuron> neurons;
    private double[] deltas;
    
 	/**
 	 * <p>Neuron gyártó függvény mely az újonnan létrehozott neuron referenciáját visszaadja</p>   
 	 * <p>Az újonnan létrehozott neuron bekerül a réteg neurons tárolójába</p>
 	 * @return létrehozott Neuron
 	 * @see Neuron
 	 */
    public Neuron addNeuron() {
    	Neuron neuron = new Neuron();
    	neurons.add(neuron);
    	return neuron;
    }
    
    /**
 	 * <p>Explicit módon létrehozott neuron referenciát beteszi réteg neurons tárolójába</p>   
 	 * @param neuron Hozzáadandó neuron
 	 * @see Neuron
 	 */
    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }
 
    /**
 	 * <p>Neuron gyártó függvény mely <b>n</b> számú neuront hoz létre</p>   
 	 * <p>Az újonnan létrehozott neuronok bekerülnek a réteg neurons tárolójába</p>
 	 * @param number n számú neuron hozzáadása
 	 * @see Neuron 
 	 */
    public void addNeuron(int number) {
        for (int i=0; i<number; i++) {
            neurons.add(new Neuron());
        }
    }
    
    /**
     * <p>Neuron gyártó függvény mely <b>n</b> számú neuront hoz létre az általunk megadott</p>
     * <p>aktivációs függvény beállítással</p>
     * @param number n számú neuron hozzáadása
     * @param a ACTIVATION
     * @see Neuron
     */
    public void addNeuron(int number, Neuron.Activation a) {
        for (int i=0; i<number; i++) {
            neurons.add(new Neuron(a));
        }
    }
    
    /**
     * <p>Neuron gyártó függvény mely <b>n</b> számú neuront hoz létre az általunk megadott</p>
     * <p>aktivációs függvény beállítással, eltolással, illetve annak súlyozhatóságával</p>
     * @param number n számú neuron hozzáadása
     * @param a ACTIVATION
     * @param bias eltolás
     * @param biasWeightable a bias eltolás súlytényezője állítható
     * @see Neuron
     */
    
    public void addNeuron(int number, Neuron.Activation a, Double bias, boolean biasWeightable) {
        for (int i=0; i<number; i++) {
            neurons.add(new Neuron(a, bias, biasWeightable));
        }
    }
    
    /**
     * <p> Tanítóalgoritmus számára fentartott eltérés vektor, mely az adott réteg kimenete, és az elvárt </p>
     * <p> kimenet deltáját adja meg </p>
     * @return delta vektor
     */
    public double[] getDeltas() {
        return deltas;
    }

    /**
     * <p> Tanítóalgoritmus számára fentartott eltérés vektor beállító, mely az adott réteg kimenete, és az elvárt </p>
     * <p> kimenet deltáját állítja be </p>
     * FIGYELEM! a metódust jellemzően a tanítóalgoritmus használja, a manuális hangolása nem ajánlott.
     * @param deltas számított delta vektor
     */
    public void setDeltas(double[] deltas) {
        this.deltas = deltas;
    }
    
    /**
     * <p> Visszaadja a neuronokat melyeket a réteg tartalmaz. Csak olvasható a </p>
     * <p> beállítására csak neuronok hozzáadásával lehetséges.</p>
     * @return neuronokat tartalmazó "konténer"
     */
    public List<Neuron> getNeurons() {
        return neurons;
    }
    
    /**
     * Visszaadja a réteg neuronjainak kimenetét.
     */
	@Override
	public String toString() {
		String out = "";
		for (int c=0; c<neurons.size(); c++) {
			out+="n"+(c+1)+"="+neurons.get(c).y;
		}
		return out;
	}

}
