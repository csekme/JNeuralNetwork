package neuralnetwork;

/**
 * SoftPlus aktivációs függvény és annak deriváltja
 * @author Csekme krisztián |KSQFYZ
 */
public class SoftPlus extends ActivationModule {

	private static final long serialVersionUID = 1119633113817456711L;

	@Override
    public double activate(double x) {
        return Math.log(1.0 + Math.exp(x));
    }

    @Override
    public double derivate(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }
}
