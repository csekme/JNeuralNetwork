package neuralnetwork;

/**
 * ReLU aktivációs függvény és annak deriváltja
 * @author Csekme Krisztián | KSQFYZ
 * @see ActivationModule
 */
public class ReLU extends ActivationModule {

	private static final long serialVersionUID = 3031005996314286673L;


	@Override
    public double activate(double sum) {
        if (sum<0) {
            return 0;
        } 
        return sum;
        
    }
    
    
    @Override
    public double derivate(double sum) {
        if (sum<0){
            return 0;
        } 
        return 1;
      
    }
    
}
