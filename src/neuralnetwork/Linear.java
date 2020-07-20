package neuralnetwork;

/**
 * Lineáris aktivációs függvény és annak deriváltja
 * @author Csekme Krisztián | KSQFYZ
 * @see ActivationModule
 */

public class Linear extends ActivationModule {

	private static final long serialVersionUID = -8519619967650623347L;


	@Override
	    public double activate(double sum) {
	     return sum;
	    }
	    
	    
	    @Override
	    public double derivate(double sum) {
	        return 1;
	    }
	
	
}
