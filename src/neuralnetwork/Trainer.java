package neuralnetwork;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A tanító algoritmus elvont osztály gondoskodik a hálózat felügyelt tanításáról
 * @author Csekme Krisztián | KSQFYZ
 * @see DataSet tanítókészlet
 */
public abstract class Trainer implements Runnable, Serializable {

	private static final long serialVersionUID = 4344039018994817248L;
	
	/** Szál futását jelző flag */
	protected boolean run = false;
	
	/** Tanítási görbe inicializációt jelző flag */
	protected boolean learnCurveInitialization = false;
	
	/** Tanítás iterációk száma */
	protected int iteration;
	
	/** File logger */
	protected Logger flog = LogManager.getLogger("NeuralNetwork");
	
	/** Tanító adat készlet*/
	protected List<DataSet> dataSet;
	
	/** Az aktuális tanulási energia az algoritmus futása során kell beállítani */
	protected double actualLearnRateEnergy;
	
	/** A tanító adat készlet bejárása szekvenciális vagy véletlenszerű */
	protected boolean randomSequence = false;
	
    /** Epizódok száma */
    protected int epoch = 0;
    
    /** Aktuális epizód állás */
    protected int epochIndex = 0;
    
    /** Bátrosági faktor | gradiens kereséshez */
    protected double braveFactor = 0.175;
    
    /** Trainer indításának az ideje */
    protected LocalDateTime startTime;
    
    /** Trainer leállításának az ideje */
    protected LocalDateTime stopTime;
	
	/** 
	 * A tanítás során betáplált leállási feltétel, ami beavatkozhat
	 * a megadott epizódok számának elérése előtt is ha az eredmények megfelelőek 
	 */
	protected Double shutdownCondition = null;
	
    /** Események, számolások kiírása konzolra */
    protected boolean verbose = false;
	
	// Tanulási görbe változói
    protected double 				learnRateScale 			= 	1;
    protected double 				learnDensity 			= 	1;
    protected int 					learnIndex 				= 	0;
	
    /**
     * Konstruktor inicializálja a tanúlókészlet tárolót
     * @see #dataSet
     */
    public Trainer() {
    	dataSet = new ArrayList<>();
    }

    /**
     * A tanítást végrehajtó metódus
     * @see Runnable
     * @see Thread
     */
	@Override
	abstract public void run();
	
	

	/**
	 * Visszaadja a a trainer feladatjelző állapotát
	 * FIGYELEM! A metódus csak akkor működő ha a run metódus kezdetén
	 * a run váltózót manuálisan true értékre állítjuk, illetve a run metódus végeztével
	 * azt manuálisan false értékre állítjuk egyébként hibás működést eredményez 
	 * @return true ha a feladat még fut egyébként false
	 */
	public boolean isRun() {
		return run;
	}
	
    /**
     * A paraméterben kapott integer tömb elemeit véletlenszerűen összekeveri
     * @param integers integer tömb referenciája
     */
    static void shuffleArray(int[] integers)
    {
      Random rnd = ThreadLocalRandom.current();
      for (int i = integers.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        // Index csere
        int a = integers[index];
        integers[index] = integers[i];
        integers[i] = a;
      }
    }
    
    /**
     * A tanító algoritmus random bejárásért felelős kapcsoló állása
     * @return true ha be van kapcsolva a véletlen bejárás
     */
    public boolean isRandomSequence() {
    	return this.randomSequence;
    }
    
    /**
     * A metódus beállítja a tanítókészlet random bejárási flag-jét
     * @param value random bejárás flag
     */
    public void setRandomSequence(boolean value) {
    	this.randomSequence = value;
    }
    
    /**
     * Visszaadja a tanító készletet
     * @return tanítókészlet
     */
    public List<DataSet> getDataSet() {
    	return this.dataSet;
    }

    /**
	 * A tanítás során betáplált leállási feltétel, ami beavatkozhat
	 * a megadott epizódok számának elérése előtt is ha az eredmények megfelelőek 
     * @return a leállás feltétele ha nem lett megadva akkor null az értéke
     */
	public Double getShutdownCondition() {
		return shutdownCondition;
	}
	
   /**
	 * A tanítás során betáplált leállási feltétel, ami beavatkozhat
	 * a megadott epizódok számának elérése előtt is ha az eredmények megfelelőek 
     * @param shutdownCondition a leállási feltétel
     */
	public void setShutdownCondition(Double shutdownCondition) {
		this.shutdownCondition = shutdownCondition;
	}
    
	/**
	 * Visszaadja a tanítási epizódok számát
	 * @return epizódok száma
	 */
    public int getEpoch() {
        return epoch;
    }

    /**
     * Tanítási epizódok számának beállítása
     * @param epoch epizódok száma
     */
    public void setEpoch(int epoch) {
        this.epoch = epoch;
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
     * számolásokat ír ki a logcsatornára 
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    /**
     * Visszaadja a gradiens kereséshez használatos
     * bátorság faktort
     * @return <p>bátorság faktor alapértelmezetten 0.175<br> 
     * 			tapasztalati szám</p>
     * @see #braveFactor
     */
	public double getBraveFactor() {
		return braveFactor;
	}

	/**
	 * Beállítja a gradiens kereséshez használatos
	 * bátorság faktort, ha nem kerül beállításra
	 * az alapértelmezett értéke 0.175
	 * @param braveFactor bátorsági faktor
	 */
	public void setBraveFactor(double braveFactor) {
		this.braveFactor = braveFactor;
	}

	/**
	 * Visszaadja a tanulás folyamatának állását
	 * az aktuális epizód állást
	 * @return {@link #epochIndex} aktuális epizód
	 * @see #epoch
	 */
	public int getEpochIndex() {
		return epochIndex;
	}

	/**
	 * Beállítja az aktuális epizód számát
	 * Ez az algoritmus futtatása alatt javasolt
	 * @param epochIndex Aktuális epizód index
	 * @see #epoch
	 */
	public void setEpochIndex(int epochIndex) {
		this.epochIndex = epochIndex;
	}

	/**
	 * Tanító algoritmus elindításának az ideje
	 * @return {@link #startTime} indítási időpont
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Tanító algoritmus elindításának az ideje
	 * @param startTime indítási időpont
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Tanító algoritmus leállításának az ideje
	 * @return {@link #stopTime}
	 */
	public LocalDateTime getStopTime() {
		return stopTime;
	}

	/**
	 * Tanító algoritmus elindításának az ideje
	 * @param stopTime leállítási idő
	 */
	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}
	
	/**
	 * A tanítás ideje milliszekundumban
	 * amennyiben másodpercben kell 1000-el kell osztani
	 * @return tanítás ideje milliszekundumban
	 * @see #startTime
	 * @see #stopTime
	 */
	public long executeTimeInMilliSecond() {
		if (startTime!=null && stopTime!=null) {
			return ChronoUnit.MILLIS.between(startTime, stopTime);
		}
		return 0;
	}

	/**
	 * Visszaadja az aktuális tanulási energiát
	 * melyet az algoritmus állít be a tanulás folyamata alatt
	 * @return {@link #actualLearnRateEnergy}
	 */
	public double getActualLearnRateEnergy() {
		return actualLearnRateEnergy;
	}

	/**
	 * Beállítja az aktuális tanulási energiát
	 * melyet az algoritmus állít be a tanulás folyamata alatt
	 * @param actualLearnRateEnergy A tanítás aktuális energiája
	 * @see #actualLearnRateEnergy
	 */
	public void setActualLearnRateEnergy(double actualLearnRateEnergy) {
		this.actualLearnRateEnergy = actualLearnRateEnergy;
	}
	
	
}
