package neuralnetwork;
import java.io.Serializable;

/**
 * Aktivációs modul felületét meghatározó elvont osztály
 * @author Csekme Krisztián | KSQFYZ
 * @see Neuron
 * {@link ReLU}
 * {@link Linear}
 * {@link SoftPlus}
 * {@link Sigmoid}
 * {@link TanH}
 */
public abstract class ActivationModule implements Serializable {

	private static final long serialVersionUID = -6809688568131244425L;

	/**
	 * Combobox feltöltésekhez
	 */
	public static final String[] activationNames = { "Sigmoid", "Hiperbolikus tangens", "ReLU", "SoftPlus", "Lineáris" };
	
	/**
	 * Sigmoid
	 */
	public static final String SIGMOID = "Sigmoid";
	/**
	 * Hiperbolikus tangens
	 */
	public static final String HIPER = "Hiperbolikus tangens";
	
	/**
	 * ReLU
	 */
	public static final String RELU = "ReLU";
	
	/**
	 * SoftPlus
	 */
	public static final String  SOFTPLUS = "SoftPlus";
	
	/**
	 * Lineáris
	 */
	public static final String  LINEAR = "Lineáris";
	
	/**
	 * Nincs aktivációs függvénye
	 */
	public static final String NO_ACTIVATION = "Nincs";
	
	/**
	 * Activációs függvény
	 * @param sum A Neuron net értéke
	 * @return Aktivált net érték
	 * @see Neuron
	 */
	public double activate(double sum){return 0;}
    
    /**
     * Aktivációs függvény deriváltja
     * @param sum A Neuron net értéke
     * @return derivált net érték
     * @see Neuron
     */
	public double derivate(double sum){return 0;}
}
