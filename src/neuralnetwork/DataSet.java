package neuralnetwork;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * A neurális hálózat felügyelt tanító algoritmusa ezzel az adatszerkezettel operál
 * egy osztálypéldány egy tanító sort reprezentál
 * Létrehozása a create statikus függvény segítségével történik
 * @author Csekme Krisztián | KSQFYZ
 * @see NeuralNetwork
 * @see Trainer
 * @see #create(double[], double[])
 */
public class DataSet implements Serializable {

	private static final long serialVersionUID = -7493796630981995433L;
	
	/** A hálózat bemenetével egyenlő méretű tanító adat */
	private double[] trainVector  = null;
	
	/** A hálózat kimenetével egyező méretű célérték adat */
	private double[] targetVector = null;
	
	/** Tanító adathoz tartozó tudás ráta */
	private double learningRate;
 
	private DataSet(){}
	
	/**
	 * Tanító adatsor létrehozása a tanító algoritmus számára
	 * @param trainSet Tanító adat
	 * @param targetSet Elvárt céladat
	 * @see NeuralNetwork
	 * @see Trainer
	 * @see #trainVector
	 * @see #targetVector
	 * @return Tanító adatsor
	 * @exception RuntimeException Ha nincs neurális hálózat példány a memóriában
	 * @exception RuntimeException Ha a neurális hálózat bemeneti neuronszáma nem egyezik a tanító adat számosságával
	 * @exception RuntimeException Ha a neurális hálózat kimeneti neuronszáma nem egyezik a cél adat számosságával
	 */
	public static DataSet create(double[] trainSet, double[] targetSet) {
		DataSet ds = new DataSet();
		if (NeuralNetwork.getInstance()==null) {
			String errMessage = "Nincs neurális hálózat példány ezért nem hozható létre tanító adatsor";
			warn(errMessage);
			throw new RuntimeException(errMessage);		
		}
		if (trainSet.length!=NeuralNetwork.getInstance().getInputUnitNumber()) {
			String errMessage = "A neurális hálózat bemeneti neuronszáma nem egyezik a tanító adat számosságával"; 
			 warn(errMessage);
			throw new RuntimeException(errMessage);
		}
		if (targetSet.length!=NeuralNetwork.getInstance().getOutputUnitNumber()) {
			String errMessage = "A neurális hálózat kimeneti neuronszáma nem egyezik a cél adat számosságával"; 
			 warn(errMessage);
			throw new RuntimeException(errMessage);
		}
		ds.trainVector = trainSet;
		ds.targetVector = targetSet;		
		return ds;
	}
	
	/**
	 * Visszaadja a tanítósor tanító adatát
	 * @return tanító adat
	 */
	public double[] getTrainVector() {
		return trainVector;
	}
	
	/**
	 * Visszaadja a tanítósor célérték adatát
	 * @return elvárt adat
	 */
	public double[] getTargetVector() {
		return targetVector;
	}

	/**
	 * Visszaadja a tanító sorhoz ideiglenesen
	 * letárolt tanítási rátát 
	 * @return tanítási ráta ideiglenes értéke
	 */
	public double getLearningRate() {
		return learningRate;
	}
	
	/**
	 * Tanítás során ideiglenesen letárolható a tanító sorhoz
	 * tartozó tanulási ráta
	 * @param learningRate tanulási mutató
	 */
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	
	public static void trace(String line, Object ...puts) {
		System.out.println("TRACE | " + LocalDateTime.now().toString() + " |" +  " " + line.replace("{}", puts.toString()));
	}
	
	public static void warn(String line, Object ...puts) {
		System.err.println("WARN | " + LocalDateTime.now().toString() + " |"  + " "  + line.replace("{}", puts.toString()));
	}
	
}
