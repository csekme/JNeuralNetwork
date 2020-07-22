package neuralnetwork;
import java.time.LocalDateTime;
import java.util.Arrays;


import math.LinearMath;

/**
 * Gradiens alapú tanítási algoritmus MLP tanítására
 * 
 * @author Csekme Krisztián | KSQFYZ
 * @see Trainer
 */
public class GradiantTrainer extends Trainer {

	private static final long serialVersionUID = 393109792309152831L;

	/**
	 * Végrehajtandó feladat
	 */
	@Override
	public void run() {

		// I. Változók inicializálása
		{
			if (NeuralNetwork.getInstance().isVerbose()) {
				Trace.trace("A(z) {} tanító algoritmus indítása ", getClass().getName());
			}
			 
			run = true;
			setStartTime(LocalDateTime.now());
			learnCurveInitialization = false;
			NeuralNetwork.getInstance().clearDataSet();
			iteration = 0;
		}

		// Elkezdjük a tanítást ha van tanító adat
		if (dataSet.size() > 0) {

			// II. Tanítási epizódok
			for (int ep = 0; ep < getEpoch(); ep++) {
				setEpochIndex(ep);

				iteration++;

				// Előkészítjük a tanítókészlet index készletét
				int[] indexes = new int[dataSet.size()];
				for (int r = 0; r < dataSet.size(); r++) {
					indexes[r] = r;
				}

				// Amennyiben a tanító készletet random szeretnénk bejárni összekeverjük az
				// indexeket
				if (isRandomSequence()) {
					shuffleArray(indexes);
				}

				for (int r = 0; r < indexes.length; r++) {

					// Tanító tömb
					double[] trainVector = dataSet.get(indexes[r]).getTrainVector();
					// Cél, elvárt adattömb
					double[] targetVector = dataSet.get(indexes[r]).getTargetVector();

					/**
					 * A tanítópontokat csak egyszer vesszük fel az első epizode alkalmával
					 */
					if (ep == 0) {
						NeuralNetwork.getInstance().getDataSet().add(DataSet.create(trainVector, targetVector));
					}

					NeuralNetwork.getInstance().getOutputLayer().setTargetVector(targetVector);

					NeuralNetwork.getInstance().stimulus(trainVector);

					if (NeuralNetwork.getInstance().isTrace()) {
						 Trace.trace("Delták a kimeneti rétegnél");
					}

					/**
					 * III. delták kiszámítása a kimeneti rétegnél
					 */
					double[] deltas = new double[NeuralNetwork.getInstance().getOutputUnitNumber()];
					double[] y = NeuralNetwork.getInstance().getY(NeuralNetwork.getInstance().getNumberOfLayers() - 1);
					if (NeuralNetwork.getInstance().isTrace()) {
						Trace.trace("y={}", Arrays.toString(y));
						Trace.trace("t={}", Arrays.toString(targetVector));
					}
					for (int d = 0; d < deltas.length; d++) {
						Neuron n = NeuralNetwork.getInstance().getOutputLayer().getNeurons().get(d);
						deltas[d] = (targetVector[d] - y[d]) * n.derivate(n.net); // (targetVector[d] - y[d]) * (1 +
																					// y[d]) * (1 - y[d]);
					}
					NeuralNetwork.getInstance().getOutputLayer().setDeltas(deltas);
					if (NeuralNetwork.getInstance().isTrace()) {
						Trace.trace("δ={}", Arrays.toString(deltas));
						Trace.trace("");
						Trace.trace("Delták a rejtett rétegeknél");
					}
					/**
					 * IV. delták kiszámítása a rejtett rétegeknél
					 */
					for (int l = NeuralNetwork.getInstance().getNumberOfLayers() - 2; l >= 1; l--) {
						if (NeuralNetwork.getInstance().isTrace()) {
							Trace.trace("Rejtett réteg {}", l);
						}
						double[] hiddenDeltas = new double[NeuralNetwork.getInstance().getLayers().get(l).getNeurons()
								.size()];
						for (int j = 0; j < hiddenDeltas.length; j++) {

							Neuron neuronInHidden = NeuralNetwork.getInstance().getLayers().get(l).getNeurons().get(j);
							hiddenDeltas[j] = neuronInHidden.derivate(neuronInHidden.net);

							double[] previousDeltas = NeuralNetwork.getInstance().getLayers().get(l + 1).getDeltas();
							double drj_1 = 0;
							for (int i = 0; i < previousDeltas.length; i++) {
								Neuron neuronFromPreviousLayer = NeuralNetwork.getInstance().getLayers().get(l + 1)
										.getNeurons().get(i);
								drj_1 += previousDeltas[i] * neuronFromPreviousLayer.weights[j];
							}
							hiddenDeltas[j] *= drj_1;

						}
						if (NeuralNetwork.getInstance().isTrace()) {
							Trace.trace("δ={}", Arrays.toString(hiddenDeltas));
						}
						NeuralNetwork.getInstance().getLayers().get(l).setDeltas(hiddenDeltas);
					}

					// Súlyvektorok új értéke a kimeneti rétegnél
					if (NeuralNetwork.getInstance().isTrace()) {
						Trace.trace("Súlyvektorok új értéke a kimeneti rétegnél");
					}
					// bátorsági faktor
					double bf = getBraveFactor();

					for (int n = 0; n < NeuralNetwork.getInstance().getOutputUnitNumber(); n++) {
						boolean hasBias = false;
						Neuron neuronFromOutputLayer = NeuralNetwork.getInstance().getOutputLayer().getNeurons().get(n);
						double[] weights = neuronFromOutputLayer.weights;
						// Megvizsgáljuk a bias létét és a súlyvektorhoz hozzáadjuk a bias súlyozását
						if (neuronFromOutputLayer.getBiasWeight() != null) {
							hasBias = true;
							weights = LinearMath.addElementToVector(neuronFromOutputLayer.weights,
									neuronFromOutputLayer.getBiasWeight(), true);
						}
						// Kimeneti réteg elötti réteg neuronjainak kimenete
						double[] outputY = NeuralNetwork.getInstance()
								.getY(NeuralNetwork.getInstance().getNumberOfLayers() - 2);
						// bias esetén kibővítjük a bias kimenetével értékével
						if (neuronFromOutputLayer.getBias() != null) {
							outputY = LinearMath.addElementToVector(outputY, neuronFromOutputLayer.getBias(), true);
						}
						if (weights.length == outputY.length) {
							double deltaFromNeuronOut = NeuralNetwork.getInstance().getOutputLayer().getDeltas()[n];

							weights = LinearMath.addVector(weights,
									LinearMath.multiplyWithScalar(outputY, (bf * deltaFromNeuronOut)));

						} else {
							Trace.warn("A súlyvektorok és bemenetek dimenziója nem egyezik w={} y={}", weights, outputY);
						}
						if (NeuralNetwork.getInstance().isTrace()) {
							Trace.trace("w={}", Arrays.toString(weights));
						}
						if (hasBias) {
							for (int i = 1; i < weights.length; i++) {
								neuronFromOutputLayer.weights[i - 1] = weights[i];
							}
							// Ha a neuron bias súlya módosítható
							if (neuronFromOutputLayer.isBiasWeightable()) {
								neuronFromOutputLayer.setBiasWeight(weights[0]);
							}
						} else {
							for (int i = 0; i < weights.length; i++) {
								neuronFromOutputLayer.weights[i] = weights[i];
							}
						}
					}
					if (NeuralNetwork.getInstance().isTrace()) {
						Trace.trace("A súlyvektorok új értéke a rejtett rétegnél");
					}
					// Rejtett réteg neuronjai súlyvektorának állítása
					for (int l = NeuralNetwork.getInstance().getNumberOfLayers() - 2; l >= 1; l--) {
						for (int n = 0; n < NeuralNetwork.getInstance().getLayers().get(l).getNeurons().size(); n++) {
							boolean hasBias = false;
							Neuron u = NeuralNetwork.getInstance().getLayers().get(l).getNeurons().get(n);
							double[] _w = u.weights;
							if (u.getBiasWeight() != null) {
								hasBias = true;
								_w = LinearMath.addElementToVector(u.weights, u.getBiasWeight(), true);
							}
							double[] _y = NeuralNetwork.getInstance().getY(l - 1);
							if (u.getBias() != null) {
								_y = LinearMath.addElementToVector(_y, u.getBias(), true);
							}
							if (_w.length == _y.length) {
								_w = LinearMath.addVector(_w, LinearMath.multiplyWithScalar(_y, getBraveFactor()
										* NeuralNetwork.getInstance().getLayers().get(l).getDeltas()[n]));
							} else {

									Trace.warn("A súlyvektorok és bemenetek dimenziója nem egyezik");
								
							}
							if (NeuralNetwork.getInstance().isTrace()) {
								Trace.trace("w={}", Arrays.toString(_w));
							}

							if (hasBias) {
								for (int i = 1; i < _w.length; i++) {
									u.weights[i - 1] = _w[i];
								}
								if (u.isBiasWeightable()) {
									u.setBiasWeight(_w[0]);
								}
							} else {
								for (int i = 0; i < _w.length; i++) {
									u.weights[i] = _w[i];
								}
							}

						}
					}

					// Tanítási görbe
					{
						double[] _t = NeuralNetwork.getInstance().getOutputLayer().getTargetVector();
						double[] _y = NeuralNetwork.getInstance()
								.getY(NeuralNetwork.getInstance().getNumberOfLayers() - 1);
						double en = 0;

						if (!learnCurveInitialization) {

							learnIndex = 0;

							for (int i = 0; i < _t.length; i++) {
								en += Math.pow(_t[i] - _y[i], 2);
							}
							en = Math.abs(en);

							dataSet.get(r).setLearningRate(en);

							learnCurveInitialization = true;

						} else {
							for (int i = 0; i < _t.length; i++) {
								en += Math.pow(_t[i] - _y[i], 2);
							}
							en = Math.abs(en);
							dataSet.get(r).setLearningRate(en);

						}
					}
				}
				learnIndex++;
				double med = 0;
				for (int _r = 0; _r < dataSet.size(); _r++) {
					if (dataSet.get(_r).getLearningRate() > med) {
						med = dataSet.get(_r).getLearningRate();
					}
				}
				setActualLearnRateEnergy(med);

				if ((getShutdownCondition() != null && med <= getShutdownCondition()) || ep == getEpoch() - 1) {
					if (getShutdownCondition() != null && med <= getShutdownCondition()) {
						Trace.trace("Hálózat energiája: {}", med);
					 	Trace.trace("A hálózat elérte a leállási feltételt, ezért a tanítás leáll.");
					}
					if (ep == getEpoch() - 1) {
						Trace.trace("Hálózat energiája: {}", med);
						Trace.trace("A hálózat elérte az epizódok számát, ezért a tanítás leáll.");
					}
					break;
				}
				// itt stop ha kell
			}
		}
		 
			Trace.trace("A(z) {} tanító algoritmus befejezése ", getClass().getName());
		 
		run = false;
		setStopTime(LocalDateTime.now());

		NeuralNetwork.getInstance().setNumberOfTrain(NeuralNetwork.getInstance().getNumberOfTrain() + 1);

	}

}
