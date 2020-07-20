package neuralnetwork;
import java.io.Serializable;
import java.util.Random;
/**
 * Processzáló egység másnéven neuron, a neurális hálózat elemét leíró osztály
 * @author Csekme Krisztián | KSQFYZ
 * @see ActivationModule
 * @see Layer
 */
public class Neuron implements Serializable {

	private static final long serialVersionUID = -9040630218454429626L;
	//Aktivációs modulok
    public static enum Activation { SIGMOID, TANH, RELU, SOFTPLUS, LINEAR, NO_ACTIVATION }
    public ActivationModule activationModule;    
    //súlyvektor
    public double[] weights;
    //a neuron kimenete
    public double y;
    //kalkulált nettó érték
    public double net;  
    //bias
    private Double bias 		= null;
    //bias súly
    private Double biasWeight 	= null;
    //bias súly állítható
    private boolean biasWeightable;
    
    Random r = new Random();

    /**
     * Konstruktor, alapesetben a Sigmoid aktiváló modul kerül
     * beállításra
     * @see #activationModule
     */
    public Neuron() {
        activationModule = new Sigmoid();
    }

    /**
     * Konstruktor választható aktivációs modullal
     * @param a Aktiváció felsorolásból
     * @see #activationModule
     */
    public Neuron(Activation a) {
        switch (a) {
            case RELU:
                activationModule = new ReLU();
                break;
            case SIGMOID:
                activationModule = new Sigmoid();
                break;
            case SOFTPLUS:
                activationModule = new SoftPlus();
                break;
            case TANH:
                activationModule = new TanH();
                break;
            case LINEAR:
            	activationModule = new Linear();
            	break;
		default:
			activationModule = new Sigmoid();
			break;
        }
    }

    /**
     * Konstruktor választható aktivációs modullal, eltolással,
     * és a eltolás súlyozhatóságának beállításával
     * @param activation Aktivációs modul érték
     * @param bias Eoltolás
     * @param biasWeightable eltolás súlyozhatósága
     * @see #activationModule
     * @see #bias
     * @see #biasWeightable
     */
    public Neuron(Activation activation, Double bias, boolean biasWeightable) {
        switch (activation) {
            case RELU:
            	this. activationModule = new ReLU();
                break;
            case SIGMOID:
            	this.activationModule = new Sigmoid();
                break;
            case SOFTPLUS:
            	this.activationModule = new SoftPlus();
                break;
            case TANH:
            	this.activationModule = new TanH();
                break;
            case LINEAR:
            	this.activationModule = new Linear();
            	break;
		default:
			this.activationModule = new Sigmoid();
			break;
        }
        this.bias = bias;
        this.biasWeightable = biasWeightable;
    }

    /**
     * Visszaadja a neuron aktivációs függvény típusát
     * @return Activation
     * @see Activation
     */
    public Activation getActivation() {
        if (activationModule instanceof ReLU) {
            return Activation.RELU;
        }
        if (activationModule instanceof Sigmoid) {
            return Activation.SIGMOID;
        }
        if (activationModule instanceof SoftPlus) {
            return Activation.SOFTPLUS;
        }
        if (activationModule instanceof TanH) {
            return Activation.TANH;
        }
        if (activationModule instanceof Linear) {
        	return Activation.LINEAR;
        }
        return Activation.SIGMOID;
    }

    /**
     * Aktivációs függvény hívása a neuron agregált értékével
     * @param sum Neuron nettó értéke
     * @return aktivált érték
     * @see ActivationModule
     */
    public double activate(double sum) {
        return activationModule.activate(sum);
    }
    
    /**
     * Az aktivációs függvény deriváltjának hívása a neuron aggregált értékével
     * @param sum Neuron nettó értéke
     * @return derivált érték
     * @see ActivationModule
     */
    public double derivate(double sum) {
    	return activationModule.derivate(sum);
    }
    
    /**
     * Aktivációs modul beállítása
     * @param module Aktivációs modul
     * @see #activationModule
     * @see ActivationModule
     */
    public void setActivationModule(ActivationModule module) {
        this.activationModule = module;
    }

    /**
     * Visszaadja a neuron súlyvektorát stringként a súlyok vesszővel
     * elválasztva 
     * @return súlyvektor
     * @see #weights
     */
    public String getWeightsAsString() {
        String b = "";
        for (int i = 0; i < weights.length; i++) {
            b += weights[i];
            if (i < weights.length - 1) {
                b += ",";
            }
        }
        return b;
    }

    /**
     * Súlyvektor beállítása, és random értékkel való inicializálása
     * a súlyok az előző rétegben lévő neuronok kimenetéhez tartoznak
     * @param n súly vektor mérete
     * @see #weights
     * @see Layer
     */
    public void initWeights(int n) {
        weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = -0.5 + r.nextDouble();
        }
        if (bias!=null) {
            biasWeight = -0.5 + r.nextDouble();
        }
    }

    /**
     * Visszaadja a neuron eltolását
     * @return eltolás
     * @see #bias
     */
    public Double getBias() {
        return bias;
    }

    /**
     * Neuron eltolásának beállítása
     * @param b eltolás
     * @see #bias
     */
    public void setBias(Double b) {
        this.bias = b;
    }

    /**
     * Visszaadja a neuront eltolás súlyát
     * amennyiben a eltolás súlya nem változtatható a
     * vosszaadott érték konzekvens 1
     * @return eltolás súlya
     * @see #biasWeight
     * @see #biasWeightable
     */
    public Double getBiasWeight() {
    	if (!biasWeightable) {
    		return null;
    	}
        return biasWeight;
    }

    /**
     * Eltolás súly beállítása
     * @param biasWeight Eltolás súly paramétere
     * @exception RuntimeException Kivétel képződik abban az esetben ha
     * az eltolás súlya nem állítható
     * @see #biasWeightable
     */
    public void setBiasWeight(Double biasWeight) {
        if (!biasWeightable) {
        	throw new RuntimeException("A eltolás súlya nem változtatható!");
        }
    	this.biasWeight = biasWeight;
    }

    /**
     * Visszaadja hogy a neuron eltolása súlyazható-e
     * @return igaz ha változtatható a súly egyébként hamis
     * @see #biasWeightable
     * @see #bias
     */
	public boolean isBiasWeightable() {
		return biasWeightable;
	}

	/**
	 * Beállítható hogy a eltolás súlyozható vagy sem
	 * @param biasWeightable flag
	 * @see #biasWeightable
	 * @see #biasWeight
	 */
	public void setBiasWeightable(boolean biasWeightable) {
		this.biasWeightable = biasWeightable;
	}

    
}
