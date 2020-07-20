package neuralnetwork;

/**
 * Sigmoid aktivációs függvény és annak deriváltja
 * @author Csekme Krisztián | KSQFYZ
 * @see ActivationModule
 */
public class Sigmoid extends ActivationModule {

	private static final long serialVersionUID = 216245881676432995L;

	@Override
    public double activate(double sum) {
    	return (1/( 1 + Math.pow(Math.E,(-1*sum))));  	
    }
    
    @Override
    public double derivate(double sum) {
        return activate(sum) * (1- activate(sum));
    }
}
