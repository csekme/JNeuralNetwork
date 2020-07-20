package neuralnetwork;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import math.LinearMath;
import neuralnetwork.Neuron.Activation;
import plotter.Range;
import plotter.Series;

/**
 * Mesterséges Neurális Hálózat osztály.
 * Az osztály egységbe foglalja a teljes neurális hálózatot annak minden elemével
 * rétegeivel, illetve annak neuronjaival
 *
 * @author Csekme Krisztián (KSQFYZ) csekme.krisztian@outlook.com
 * @since 2020.01.03
 * @see Layer
 * @see Neuron
 * @see ActivationModule
 */
public class NeuralNetwork implements Serializable {

	private static final long serialVersionUID = -3975197469989716612L;
	
	static int id = 0;

	/** File logger */
	Logger nlog = LogManager.getLogger("NeuralNetwork");
	
	// Neurális rétegeket összetartó lista
    private List<Layer> layers = null;
    
    // Iteráció száma megadja hogy a bemenetre hány alkalommal történt beírás
    private long iteration = 0;
    
    // Neurális hálózat megnevezése
    private String name;
    
    // Neurális hálózat leírása
    private String description;

    //Hálózat n alkalommal tanított
    private int numberOfTrain = 0;
    
    //Legutóbbi tanítókészlet számossága
    private int numberOfTrainSet = 0;
    
    
    //Minden esemény számolás kiírása konzolra
    public boolean verbose = true;
    
    //Tanulás alkalmával a tanítópontok rögzítése
    private boolean storeTrainSet = false;

    //Utoljára használt tanító algoritmus
    private Trainer lastTrainer = null;
    
    //Tanulópontok tárolása
    private List<DataSet> dataSets = null;
    
    //Tanulási görbe adatok
    Range learnRateEpisode = null;
    Range learnRateEnergy = null;
    Series learnRatePoints = null;
    
    //Osztályozási feladatok
    Range classXAxis = null;
    Range classYAxis = null;
    double classMapDesnity = 0.3;
    Map<String, Color> clm = new HashMap<>();
    
    //Esemény kezelők
    List<ActionListener> inputListeners;
    List<ActionListener> outputListeners;
    
    //Neurális Hálózat pédánya
    private static NeuralNetwork nn = null;
    
    /**
     * Rejtett konstruktor, helyette a gyártófüggvény használatos
     */
    private NeuralNetwork() {
    	layers 			= new ArrayList<>();
        dataSets 		= new ArrayList<>();
        inputListeners 	= new ArrayList<>();
        outputListeners	= new ArrayList<>();
    }
    
    /**
     * Gyártófüggvény mely létrehozza a neurális hálózatot
     * @return Létrehozott Neurális Hálózat példánya
     */
    public static NeuralNetwork create() {
    	id++;
    	nn = new NeuralNetwork();
    	return nn;
    }
    
    /**
     * Neurális hálózat példány betöltésére használatos
     * függvény.
     * @param n NeuralNetwork
     */
    public static void setInstance(NeuralNetwork n) {
    	nn = n;
    }
    
    /**
     * Visszaadja a neurális hálózat példányát a memóriából
     * abban az esetben ha létrehozás még nem történt meg,
     * meg kell hívni a create gyártófüggvényt
     * @see #create()
     * @return Neurális háló példánya egyébként null
     */
    public static NeuralNetwork getInstance() {
    	return nn;
    }

    /**
     * Neurális hálózatot létrehozó függvény, mely segítségével felépíthető a teljes topológia.
     * @param layer a számossága adja a hálózat rétegeinek a számát a
     * 		  tömbben átadott számok pedig az aktuális réteg neuronjainak a számát
     * @param activation rétegenként a neuronok aktivációs függvényei
     * @param bias rétegenként a neuronok eltolásai
     * @param biasWeightable rétegenként a neuronok eltolásának súlytényezője állítható 
     * @exception RuntimeException Kivételek keletkezhetnek az alábbi esetekben: <br>
     * 		<ul>
     * 			<li>Az átadott tömbök mérete nem egyező</li>
     * 			<li>A rétegek száma kevesebb mint 2</li>
     * 			<li>Ha egy rétegben 0 a megadott neuronok száma</li>
     * 		</ul>
     */
    public void build(int[] layer, Activation[] activation , Double[] bias, boolean[] biasWeightable) {
        
    	nlog.trace("Neurális hálózat létrehozása");
    	nlog.trace("Megnevezés: {}", NeuralNetwork.getInstance().getName());
    	nlog.trace("Leírás: {}", NeuralNetwork.getInstance().getDescription());

        if (layer.length < 2) {
        	nlog.error("Minimum 2 réteget meg kell adni");
            throw new RuntimeException("Minimum 2 réteget meg kell adni");
        }
        if (layer.length != activation.length) {
        	nlog.error("A bemeneti paraméterek (tömbök) elemszáma nem egyezik");
            throw new RuntimeException("A bemeneti paraméterek (tömbök) elemszáma nem egyezik");
        }
        layers.clear();

        for (int i = 0; i < layer.length; i++) {
            if (layer[i] == 0) {
            	nlog.error("minimum 1 neuront meg kell adni az adott rétegnek");
                throw new RuntimeException("minimum 1 neuront meg kell adni az adott rétegnek");
            }
            if (i == 0) {
            	nlog.trace("Bemeneti réteg hozzáadása: {} egységszámmal.",layer[i]);
                InputLayer.create().addNeuron(layer[i], activation[i], bias[i], biasWeightable[i]);
            } else if (i == layer.length - 1) {
            	nlog.trace("Kimeneti réteg hozzáadása: {} egységszámmal. {} aktiváló függvénnyel.",layer[i], activation[i].toString());
                OutputLayer.create().addNeuron(layer[i], activation[i], bias[i], biasWeightable[i]);
            } else {
            	nlog.trace("Rejtett réteg hozzáadása: {} egységszámmal. {} aktiváló függvénnyel.",layer[i], activation[i].toString());
                HiddenLayer.create().addNeuron(layer[i], activation[i], bias[i], biasWeightable[i]);
            }
        }
    }
    
    
    /**
     * A stimulus függvény híváskor azt követően hogy a hálózat bemenetére adat lett illesztve
     * végrehajtja a regisztrált eseményfigyelőket
     * @param action regisztrálandó eseményfigyelő
     * @see #stimulus(double[])
     */
    public void addInputListener(ActionListener action) {
    	inputListeners.add(action);
    }
 
    /**
     * Események köthetőek a hálózat kimenetelére, melyek a kalkulációk végeztével futnak le    
     * @param action regisztrálandó eseményfigyelő
     */
    public void addOutputListener(ActionListener action) {
    	outputListeners.add(action);
    }
    
    
    /**
     * Az impulse függvény a paraméterben kapott vektor-t a hálózat bemenetére
     * illeszti, és a kimenetig számolja a hálózatot
     * @param input lebegőpontos vektor melynek a számossága a bemeneti réteg neuronszáma
     * @exception RuntimeException keletkezik a nem egyezik a paraméterben megadott tömb mérete a
     * hálózat bemeneti neuron számával
     */
    public void stimulus(double[] input) {
        if (input.length != layers.get(0).getNeurons().size()) {
        	nlog.warn("Az átadott elemszámú bemeneti vektor nem illeszkedik a hálózat bemenetére, eltérő a méretük!");
            throw new RuntimeException("Az átadott elemszámú bemeneti vektor nem illeszkedik a hálózat bemenetére, eltérő a méretük!");
        }
        for (int i = 0; i < input.length; i++) {
            layers.get(0).getNeurons().get(i).y = input[i];
        }
        //Input eseményekfigyelők meghívása
    	for (int l = 0; l < inputListeners.size(); l++) {
    		inputListeners.get(l).actionPerformed( new ActionEvent(this, id, "INPUT"));
    	}
    	//Ingerület átvitel
        stimulusTransmitter();
		// Output események meghívogatása
		for (int l = 0; l < inputListeners.size(); l++) {
			outputListeners.get(l).actionPerformed(new ActionEvent(this, id, "OUTPUT"));
		}
    }

    /**
     * Az iterációt végrehajtó függvény melyet az impulse függvény hív meg
     * miután a hálózat bemenetére illesztette az értékvektor-t.
     * @see #stimulus
     */
    private void stimulusTransmitter() {
    	if (verbose) {
    		nlog.trace("Iteráció [{}]", iteration);
    	}
    	iteration++;
        
        for (int l = 0; l < layers.size(); l++) {

            if (l > 0) {
                for (int u = 0; u < layers.get(l).getNeurons().size(); u++) {
                    if (verbose) {
                        nlog.trace("");
                    }
                    Neuron p = layers.get(l).getNeurons().get(u);
                    if (verbose) {
                    	nlog.trace((l == layers.size() - 1 ? "Kimeneti réteg: [" : "Rejtett réteg: [") + (l + 1) + "] \t Processzáló egység: [" + (u + 1) + "]");
                    }

                    double[] w = p.weights; //get(l, u).weights;
                    double[] y = getY(l - 1);
                    if (p.getBias() != null) {
                        w = LinearMath.addElementToVector(w, p.getBiasWeight(), true);
                        y = LinearMath.addElementToVector(y, p.getBias(), true);
                    }
                    if (verbose) {
                    	nlog.trace("w {}",Arrays.toString(w));
                    }
                    p.net = LinearMath.scalarProduct(w, y);
                    if (verbose) {
                    	nlog.trace("net = {}", p.net);
                    }
                    p.y = p.activate(p.net);
                    if (verbose) {
                    	nlog.trace("y{} = {}", (u+1), p.y);
                        nlog.trace("");
                    }
                }
            } else {
                for (int u = 0; u < layers.get(l).getNeurons().size(); u++) {
                    if (verbose) {
                    	nlog.trace("");
                    	nlog.trace("Bemeneti réteg: [{}] \t Processzáló egység: [{}]", (l + 1) , (u + 1));
                    	nlog.trace("x{} = {}", (u + 1), layers.get(l).getNeurons().get(u).y );
                    	nlog.trace(""); 
                    }
                }
            }
        }
        

    }
    
    /**
     * Tanítókészlet törlése
     */
    public void clearDataSet() {
    	dataSets.clear();
    }

    /**
     * Súlyok inicializálása véletlenszámokkal
     * -0.5, 0.5 közötti értékkel ami a neuronban van definiálva
     * @see Neuron
     */
    public void initWeights() {
    	nlog.trace("Súlyok inicializálása...");
      
        for (int l = 0; l < layers.size(); l++) {
            if (l == 0) {
                for (int u = 0; u < layers.get(l).getNeurons().size(); u++) {
                    get(l, u).initWeights(layers.get(l).getNeurons().size());
                }
            }
            if (l > 0) {
                for (int u = 0; u < layers.get(l).getNeurons().size(); u++) {
                    get(l, u).initWeights(layers.get(l - 1).getNeurons().size());
                }
            }
        }
    }

    /**
     * Egy adott réteg kimeneti vektora, processzáló egységek kimenete
     *
     * @param layerIndex réteg indexszáma
     * @return lebegőpontos vektor mely az adott réteg teljes kimenete
     */
    public double[] getY(int layerIndex) {
        int n = layers.get(layerIndex).getNeurons().size();
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            y[i] = layers.get(layerIndex).getNeurons().get(i).y;
        }
        return y;
    }

    /**
     * Visszaadja a megcímzett neuron példányt
     * @param layer a réteg indexe
     * @param index a rétegben található neuron indexe
     * @return a címzett Neuton
     * @see Layer
     * @see Neuron
     */
    Neuron get(int layer, int index) {
        return layers.get(layer).getNeurons().get(index);
    }

    /**
     * Visszaadja a Neurális Hálózat bemenetének a dimenzióját
     * @return bemeneti neuronok száma
     * @see Neuron
     */
    public int getInputUnitNumber() {
        if (layers.size() > 0) {
            return layers.get(0).getNeurons().size();
        }
        return 0;
    }

    /**
     * Visszaadja a kimeneti réteg neuronjainak a számát
     * @see OutputLayer
     * @see Neuron
     * @return kimeneti neuronok száma
     */
    public int getOutputUnitNumber() {
        if (layers.size() > 0) {
            return layers.get(layers.size() - 1).getNeurons().size();
        }
        return 0;
    }

    /**
     * Visszaadja a hálózat rétegeinek a számát
     * @see Layer
     * @return Rétegek száma
     */
    public int getNumberOfLayers() {
        return layers.size();
    }

    /**
     * Visszaadja a hálózatban található összes neuronnak a számát
     * @see Neuron
     * @return össz neuronszám
     */
    public int getSumOfUnits() {
        int n = 0;
        for (int l = 0; l < layers.size(); l++) {
            for (int u = 0; u < layers.get(l).getNeurons().size(); u++) {
                n++;
            }
        }
        return n;
    }

    /**
     * Visszaadja a Neurális Hálózat megnevezését
     * @return megnevezés
     */
    public String getName() {
        return name;
    }

    /**
     * Neurális hálózat megnevezése
     * @param name megnevezés
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Visszaadja a Neurális Hálózat leírását
     * @return Neurális hálózat leírása
     */
    public String getDescription() {
        return description;
    }

    /**
     * Neurális hálózat leírása
     * @param description leírás
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Visszaadja a Neurális Hálózat kimeneti rétegét
     * @return Kimeneti réteg
     */
    public OutputLayer getOutputLayer() {
        return (OutputLayer) layers.get(layers.size() - 1);
    }

    /**
     * Vissza adja a Neurális Hálózat rétegeit tartalmazó listát
     * @return rétegeket tartalmazó lista
     * @see #layers
     * @see Layer
     */
    public List<Layer> getLayers() {
        return layers;
    }

    /**
     * Visszaadja a Neurális Hálózatonon végrehajtott iterációk számát
     * @return iterációk száma
     * @see #iteration
     */
    public long getIteration() {
        return iteration;
    }

    /**
     * Visszaadja hogy a Neurális Hálózat hány alkalommal volt tanítva
     * @return tanítások száma
     * @see #numberOfTrain
     */
    public int getNumberOfTrain() {
        return numberOfTrain;
    }

    /**
     * A Neurális Hálózat tanítási alkalmak száma
     * @param numberOfTrain tanítás alkalmak száma
     * @see #numberOfTrain
     */
    public void setNumberOfTrain(int numberOfTrain) {
        this.numberOfTrain = numberOfTrain;
    }

    /**
     * Visszaadja a tanító készletet nagyságát
     * @return tanító készlet nagysága
     * @see #numberOfTrainSet
     */
    public int getNumberOfTrainSet() {
        return numberOfTrainSet;
    }

    /**
     * Tanító készlet nagysága
     * @param numberOfTrainSet tanító készlet nagysága
     * @see #numberOfTrainSet
     */
    public void setNumberOfTrainSet(int numberOfTrainSet) {
        this.numberOfTrainSet = numberOfTrainSet;
    }

    /**
     * Tanító algoritmus beállítása
     * @param trainer Tanító algoritmus
     * @see #lastTrainer
     */
    public void setTrainer(Trainer trainer) {
    	this.lastTrainer = trainer;
    }

    /**
     * Visszaadja az utoljára használt tanító algoritmust
     * @return trainer
     * @see #lastTrainer
     */
    public Trainer getLastTrainer() {
    	return this.lastTrainer;
    }

    /**
     * Visszaadja az osztály beszédességét
     * @return ha true akkor kiír a log csatornára
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Osztály beszédességének beállítása
     * @param verbose verbose ha true akkor eseményeket, 
     * 		   számolásokat ír ki a logcsatornára
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Visszaadja hogy a tanítókészlet tárolásra került-e
     * @return tanítókészlet tárolásra került?
     * @see #storeTrainSet
     */
	public boolean isStoreTrainSet() {
		return storeTrainSet;
	}

	/**
	 * Tanítókészlet tárolásra került
	 * @param storeTrainSet flag
	 * @see #storeTrainSet
	 */
	public void setStoreTrainSet(boolean storeTrainSet) {
		this.storeTrainSet = storeTrainSet;
	}

	/**
	 * Neurális Hálózat tanítókészlete 
	 * @return tanítókészlet
	 * @see Trainer
	 */
	public List<DataSet> getDataSet() {
		return dataSets;
	}
	
	/**
	 * Neurális Hálózat tanító adata, index hivatkozással
	 * @param index tanító adat index
	 * @return index által hivatkozott tanító adat
	 * @see DataSet
	 */
	public DataSet getDataSet(int index) {
		return dataSets.get(index);
	}

	/**
	 * Neurális Hálózat tanítókészlete
	 * @param trainPoints tanítókészlet lista
	 * @see DataSet
	 */
	public void setDataSet(List<DataSet> trainPoints) {
		this.dataSets = trainPoints;
	}

	/**
	 * Tanulási görbe x tengelye
	 * @return tanulási görbe x tengelye
	 */
	public Range getLearnRateEpisode() {
		return learnRateEpisode;
	}
	
	/**
	 * Tanulási görbe x tengelye
	 * @param learnRateEpisode x tengely
	 */
	public void setLearnRateEpisode(Range learnRateEpisode) {
		this.learnRateEpisode = learnRateEpisode;
	}

	/**
	 * Tanulási görbe y tengelye
	 * @return tanulási görbe y tengelye
	 */
	public Range getLearnRateEnergy() {
		return learnRateEnergy;
	}

	/**
	 * Tanulási görbe y tengelye
	 * @param learnRateEnergy y tengely
	 */
	public void setLearnRateEnergy(Range learnRateEnergy) {
		this.learnRateEnergy = learnRateEnergy;
	}
	
	/**
	 * Visszaadja a maximális hálózati energiát a tanítás során
	 * mért adatokból
	 * @see Trainer
	 * @see #learnRatePoints
	 * @return max hálózati energia
	 */
	public double getMaxLearnRateEnergy() {
		double max = Double.MIN_VALUE;
		for (int i=0; i<learnRatePoints.polygons.size(); i++) {
			if (learnRatePoints.polygons.get(i).getY()>max) {
				max = learnRatePoints.polygons.get(i).getY();
			}
		}
		return max;
	}
	
	/**
	 * Visszaadja a legkisebb hálózati energiát a tanítás során
	 * mért adatokból 
	 * @see Trainer
	 * @see #learnRatePoints
	 * @return min hálózat energiája
	 */
	public double getMinLearnRateEnergy() {
		double min = Double.MAX_VALUE;
		for (int i=0; i<learnRatePoints.polygons.size(); i++) {
			if (learnRatePoints.polygons.get(i).getY()<min) {
				min = learnRatePoints.polygons.get(i).getY();
			}
		}
		return min;
	}

	/**
	 * Tanulási görbe pontjai
	 * @return görbe pontok
	 */
	public Series getLearnRatePoints() {
		return learnRatePoints;
	}

	/**
	 * Tanulási görbe pontjai
	 * @param learnRatePoints görbe pontok
	 */
	public void setLearnRatePoints(Series learnRatePoints) {
		this.learnRatePoints = learnRatePoints;
	}

	/**
	 * Osztályozó képernyő x tengely
	 * @return Range mint x tengely
	 */
	public Range getClassXAxis() {
		return classXAxis;
	}

	/**
	 * Osztályozó képernyő x tengely
	 * @param classXAxis x tengely
	 */
	public void setClassXAxis(Range classXAxis) {
		this.classXAxis = classXAxis;
	}

	/**
	 * Osztályozó képernyő y tengely
	 * @return osztályozó képernyő y tengely
	 */
	public Range getClassYAxis() {
		return classYAxis;
	}

	/**
	 * Osztályozó képernyő y tengely
	 * @param classYAxis y tengely
	 */
	public void setClassYAxis(Range classYAxis) {
		this.classYAxis = classYAxis;
	}

	/**
	 * Osztályozó képernyő szín tokenek
	 * @return szín tokenek
	 */
	public Map<String, Color> getClm() {
		return clm; 
	}

	/**
	 * Osztályozó képernyő szín tokenek
	 * @param clm szín tokenek
	 */
	public void setClm(Map<String, Color> clm) {
		this.clm = clm;
	}

	/**
	 * Osztályozó képernyő felületi sűrűsége
	 * @return felületi sűrűség
	 */
	public double getClassMapDesnity() {
		return classMapDesnity;
	}

	/**
	 * Osztályozó képernyő felületi sűrűsége
	 * @param classMapDesnity osztályozási sűrűség
	 */
	public void setClassMapDesnity(double classMapDesnity) {
		this.classMapDesnity = classMapDesnity;
	}
    
	
    
    
    
}
