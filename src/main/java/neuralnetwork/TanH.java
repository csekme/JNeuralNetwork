package neuralnetwork;

/**
 * Hiperbolikus Tangens aktivációs függvény és annak deriváltja
 * @author Csekme Krisztián  | KSQFYZ
 */
public class TanH extends ActivationModule  {

	private static final long serialVersionUID = -7862395754036851848L;

	@Override
    public double activate(double sum) {
        return (2 / (1 + Math.pow(Math.E, (-2 * sum))))-1;
    }

    @Override
    public double derivate(double sum) {
        return 1- Math.pow(activate(sum), 2);
    }

}
