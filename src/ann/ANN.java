/**
 * 
 */
package ann;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ann.elements.ExtInput;
import ann.elements.Input;
import ann.elements.Neuron;
import ann.input.Case;
import ann.input.Read;
import ann.output.ConfigurationTestResults;
import ann.output.ExecutionResult;
import ann.output.NeuronValues;
import ann.output.NoiseResult;
import ann.output.TrainAndTestError;
import ann.util.ANNConfig;
import ann.util.Individual;
import ann.util.MyRandom;
import ann.util.NeuronConfig;

/**
 * <p>
 * Core of the FeedForward Neural Network, it contains most of the important functionality.
 * <p>
 * Within this class you can find the training methods for both algorithms (GA and BP) and the
 * networks constructor as well.
 * @author Manuel Konomi Pilkati
 */
public class ANN {

	/**
	 * 
	 * @author Manuel Konomi Pilkati
	 * An auxiliary class to compare the fit of two chromosomes (Individual class).
	 *
	 */
	public class individualComparator implements Comparator<Individual> {

		@Override
		public int compare(Individual o1, Individual o2) {
			double re = o1.getFit() - o2.getFit();
			if (re < 0) {
				return -1;
			} else if (re == 0) {
				return 0;
			} else {
				return 1;
			}
		}

	}
	/**
	 * Path were the data is read and written
	 */
	private static String path = Read.SilverCenturion;

	/**
	 * Array with the neurons that form the network
	 */
	private ArrayList<Neuron> neurons;
	/**
	 * Connections between the neurons
	 */
	private ArrayList<Input> conects;
	
	private int nParam; // Numero de parametros de entrada del problema (y de
						// neuronas de entrada)
	private int nHiddenLayers;// Numero de capas ocultas de la red
	private int nNeuHidLay; // Neurons per Hidden layer
	private int nOutput;
	/**
	 * A reference to the outputs of the network
	 */
	private ArrayList<Neuron> output;
	/**
	 * The inputs of the network. They are connected via 'conects' to the neurons.
	 */
	private ArrayList<ExtInput> extInputs;

	/**
	 * The limits for the predicction. If the output neuron gives a number bigger than 'POSITIVE_PREDICCTION',
	 * the output is turned into a one (true). If the output is smaller than 'NEGATIVE_PREDICTION' then
	 * the output is zero (false)
	 */
	private final static double POSITIVE_PREDICTION = 0.6;
	private final static double NEGATIVE_PREDICTION = 0.4;

	/**
	 * Percentage of problem cases that will be used for trainning. The remaining cases will conform 
	 * the test set.
	 */
	private final double trainingPercentage = 0.3;

	private final static double MAX_ERROR_BP = 0.01;
	private static int CICLES_BP = 200;
	private static double LEARNING_FACTOR;
	private static double MOMFACTOR = 0.5; //momentum factor
	private static boolean MOMFACTORFLAG = true; //determines if the factor is used or not
	/**
	 * Defines the output type of the network. by default the network returns only one or zero (OUTTYPE= true).
	 * If swithced to 'false' the network returns real numbers.
	 */
	private static final boolean OUTTYPE = true;

	private static boolean PENALTY = false;
	private static final double INIRANG = 0.6;
	private static final double MIDRANG = 0.2;
	private static final double LOWRANG = -0.3;
	private static final double HIGHPEN = 0.06;
	private static final double MEDPEN = 0.025;
	private static final double LOWPEN = 0.0125;

	private static int INDVIDUAL_PAIRS = 5;
	private static double GENERR = -0.01;
	private static int GENERATIONS = 400;
	private static double CROSSOVER = 0.8;
	/**
	 * Determines the size of the mutation. With a big constant the probability for big mutations raises.
	 */
	private static double MUTCTE = 0.4;
	/**
	 * Mutation probability of a chromsome.
	 */
	private static double MUTFACT = 0.8;

	// TEST BENCH
	private static int ITERATIONS = 1;

	private static boolean BPON = false;
	private static boolean AGON = false;
	private static boolean APPON = true;

	private static final boolean EVOLUTIONGRAPH = true;

	private static final boolean NOISE_ON = true;

	private static final boolean SWEEP = false;
	private static int PENFUNC = 1;

	private static final int AG = 1, BP = 2, APP = 3;

	@SuppressWarnings("unused")
	@Deprecated
	private ArrayList<Double> means;

	@SuppressWarnings("unused")
	@Deprecated
	private ArrayList<Double> stdDeviations;

	/**
	 * Main method, launches static methods with preset configurations.
	 * @param args
	 */
	public static void main(String[] args) {
		String sub = "\\PENALTY TrucoNoise";
		String aux=sub+" penf2";
		ANN.PENFUNC=2;
		ANN.CURVAS(aux);
//		aux=sub+" penf3";
//		ANN.PENFUNC=3;
//		ANN.CURVAS(aux);
//		aux=sub+" penf4";
//		ANN.PENFUNC=4;
//		ANN.CURVAS(aux);
		
//		ANN.CURVASNEUCONF(sub);

	}

	/**
	 * Constructor of the ANN (Artificial Neural Network)
	 * 
	 * It creates a non-trained neural network according to the parameters
	 * Received. The network will receive as input as many parameters as nParam.
	 * It will have "nLayers" layers (including the output and input layer. All
	 * the layers except for the output layer will have "nNeuLay" neurons.
	 * 
	 * The weights for all the connections will be random. The network resulting
	 * of this method's invocation is dummy. For a reliable ANN you will have to
	 * call "train()" method over the ANN giving a training set.
	 * @param configfile contains the path of a file with an alternative set up for the neurons
	 * in the network.
	 * @param ac A class containing the basic configuration for the ANN.
	 */
	public ANN(String configfile, ANNConfig ac) {
		super();
		this.nParam = ac.getParam();
		this.nHiddenLayers = ac.getHidLay();
		this.nNeuHidLay = ac.getNeuronsHidLay();
		this.nOutput = ac.getNoutput();
		ANN.LEARNING_FACTOR = ac.getLFactor();
		ANN.MOMFACTOR = ac.getMFactor();
		ANN.CROSSOVER = ac.getCrossover();
		ANN.MUTCTE = ac.getmCte();
		ANN.MUTFACT=ac.getMutFactor();
		ANN.GENERATIONS = ac.getGenerations();
		ANN.INDVIDUAL_PAIRS= ac.getPairs();
		ANN.AGON = ac.isAgon();
		ANN.APPON = ac.isAppon();
		ANN.BPON = ac.isBpon();
		
		int nNeurons = this.nParam + (this.nHiddenLayers * this.nNeuHidLay)
				+ this.nOutput;
		ArrayList<NeuronConfig> ncfg = Read.readConfig(configfile,
				ANN.LEARNING_FACTOR, nNeurons);

		this.conects = new ArrayList<Input>();
		this.neurons = new ArrayList<Neuron>();
		this.extInputs = new ArrayList<ExtInput>();
		this.output = new ArrayList<Neuron>();
		int neuronIDCount = 0;

		for (int i = 0; i < this.nParam; i++) {
			this.extInputs.add(new ExtInput(0));
		}
		
		//TODO Remember the why of this
		this.extInputs.add(new ExtInput(1));

		/*
		 * Generation of the Neural Network according to the arguments of the
		 * constructor.
		 */

		/*
		 * Generation of the first layer (input layer) of the ANN
		 */

		MyRandom rd = new MyRandom();
		for (int i = 0; i < this.nParam; i++) {
			Neuron n = new Neuron(ncfg.get(neuronIDCount));
			Input in = new Input(this.extInputs.get(i), 1);
			n.addConection(in);

			// Sigmoid function disabled (works as the identity function
			n.setSigmoid(false);

			this.neurons.add(n);
			neuronIDCount++;
		}

		/*
		 * Generation of the hidden layers of the ANN
		 */
		for (int i = 0; i < this.nHiddenLayers; i++) {

			//TODO maybe this could be unified?
			for (int j = 0; j < this.nNeuHidLay; j++) {
				Neuron n = new Neuron(ncfg.get(neuronIDCount));

				if (i == 0) {
					for (int k = 0; k < this.nParam; k++) {
						Neuron aux2 = null;
						aux2 = this.neurons.get(k);
						Input in = new Input(aux2, rd.nextDouble());
						this.conects.add(in);
						n.addConection(in);
						aux2.addDstream(in);
					}
				} else {
					for (int k = 0; k < this.nNeuHidLay; k++) {
						Neuron aux2 = null;
						int neuIndex = this.nNeuHidLay * (i - 1) + k
								+ this.nParam; // creating
						// connections
						// to the next
						// layer
						aux2 = this.neurons.get(neuIndex);
						Input in = new Input(aux2, rd.nextDouble());
						this.conects.add(in);
						n.addConection(in);
						aux2.addDstream(in);
					}
				}

				Input in = new Input(this.extInputs.get(this.nParam),
						rd.nextDouble());
				n.addConection(in);
				this.conects.add(in);
				this.neurons.add(n);
				neuronIDCount++;
			}
		}

		/*
		 * Generation of the final layer (output) of the ANN
		 */

		for (int ij = 0; ij < this.nOutput; ij++) {
			Neuron n = new Neuron(ncfg.get(neuronIDCount));
			if (this.nHiddenLayers < 1) {
				for (int k = 0; k < this.nParam; k++) {
					int neuIndex = k; // creating
										// connections
										// to the
										// next
										// layer
					Neuron aux2 = this.neurons.get(neuIndex);
					Input in = new Input(aux2, rd.nextDouble());
					this.conects.add(in);
					n.addConection(in);
					aux2.addDstream(in);
				}
			} else {
				for (int k = 0; k < this.nNeuHidLay; k++) {
					int neuIndex = this.nNeuHidLay * (this.nHiddenLayers - 1)
							+ k + this.nParam; // creating
					// connections
					// to the
					// next
					// layer
					Neuron aux2 = this.neurons.get(neuIndex);
					Input in = new Input(aux2, rd.nextDouble());
					this.conects.add(in);
					n.addConection(in);
					aux2.addDstream(in);
				}
			}

			Input in = new Input(this.extInputs.get(this.nParam),
					rd.nextDouble());
			n.addConection(in);
			this.conects.add(in);
			this.neurons.add(n);
			this.output.add(n);
		}
		for (Neuron n : this.neurons) {
			n.applyLFactor();
		}
		this.setMFactor();
	}

	private void setMFactor() {
		for (Input tmp : this.conects) {
			tmp.setmFactor(MOMFACTOR);
		}
	}

	private void addOffspring(ArrayList<Individual> offspring, Individual in) {
		ArrayList<Double> we = new ArrayList<Double>();
		for (Double d : in.getWeights()) {
			double tmp = d;
			we.add(tmp);
		}

		Individual copy = new Individual(we);
		offspring.add(copy);
	}
	
	/**
	 * This method executes the BP algorithm.
	 * @param trainCases set of training cases
	 * @param testCases Set of test cases. This parameter is optional. If a set is given, the function will
	 * take record of the evolution of the algorithm.
	 * @return Returns an array with the performance of the network in every training cycle. It will return
	 * null if no test set is provided.
	 */
	public ArrayList<TrainAndTestError> backPropagationLearning(
			ArrayList<Case> trainCases, ArrayList<Case> testCases) {
		double errorTrain = 200;
		double errorTest = 200;
		ArrayList<TrainAndTestError> evolution = new ArrayList<TrainAndTestError>();

		int i = 0, j = 0;

		errorTrain = 1 - this.testPack(trainCases);
		if (testCases != null) {
			errorTest = 1 - this.testPack(testCases);
			evolution.add(new TrainAndTestError(errorTrain, errorTest, j));
		}

		while (errorTrain > MAX_ERROR_BP && j < ANN.CICLES_BP) {

			if (i >= trainCases.size()) {
				i = 0;
			}

			Case aux = trainCases.get(new Random().nextInt(trainCases.size()));

			// TODO optimizar esto
			this.setInputs(aux.getInputsBP());
			ArrayList<Double> buleano = this.prediction(true);
			ArrayList<Double> estimation = this.prediction(false);

			if (!buleano.equals(aux.getExpected())) {
				this.calculateErrorBP(aux.getExpected());
				if (ANN.MOMFACTORFLAG) {
					this.updateWeightsMomentum();
				} else {
					this.updateWeights();
				}

			}

			i++;
			j++;
			errorTrain = 1 - this.testPack(trainCases);
			if (testCases != null) {
				errorTest = 1 - this.testPack(testCases);
				evolution.add(new TrainAndTestError(errorTrain, errorTest, j));
			}
			if (j % 1000 == 0) {
				System.out.println("Error en ciclo " + j + "= " + errorTrain
						+ " Estimacion= " + estimation);
			}
		}
		if (testCases == null) {
			return null;
		}
		return evolution;
	}

	private void updateWeightsMomentum() {
		for (Input aux : this.conects) {
			aux.updateWeightMomentum();
		}

	}

	@Deprecated
	public ArrayList<Double> calculateDeviations(ArrayList<Case> cases,
			ArrayList<Double> means) {
		ArrayList<Double> deviations = new ArrayList<Double>();
		double att1 = 0., att2 = 0., att3 = 0.;
		for (Case tmp : cases) {
			att1 += Math.pow(tmp.getHFE() - means.get(0), 2);
			att2 += Math.pow(tmp.getIntegral() - means.get(1), 2);
			att3 += Math.pow(tmp.getMeanValue() - means.get(2), 2);
		}

		att1 = Math.sqrt(att1 / cases.size());
		att2 = Math.sqrt(att2 / cases.size());
		att3 = Math.sqrt(att3 / cases.size());

		deviations.add(att1);
		deviations.add(att2);
		deviations.add(att3);

		return deviations;
	}

	/**
	 * Calculates the error of every neuron in the network to use it in BP
	 * @param expectation An array list with the expected outputs. They are compared to the outputs generated
	 * by the network.
	 */
	public void calculateErrorBP(ArrayList<Double> expectation) {
		for (int i = 0; i < this.nOutput; i++) {
			this.output.get(i).recalculateErrorOutput(expectation.get(i));
		}
		for (int i = this.neurons.size() - (this.nOutput + 1); i >= 0; i--) {
			Neuron aux = this.neurons.get(i);
			aux.recaculateError();
		}
	}

	@Deprecated
	public ArrayList<Double> calculateMeans(ArrayList<Case> cases) {
		ArrayList<Double> means = new ArrayList<Double>();
		Double att1 = 0.0, att2 = 0.0, att3 = 0.0;
		for (Case tmp : cases) {
			att1 += tmp.getHFE();
			att2 += tmp.getIntegral();
			att3 += tmp.getMeanValue();
		}
		att1 = att1 / cases.size();
		att2 = att2 / cases.size();
		att3 = att3 / cases.size();

		means.add(att1);
		means.add(att2);
		means.add(att3);
		return means;
	}

	/**
	 * Calculates the Fit of all the individuals in 'a' and returns the maximum
	 * error, the minimum error and the index of the individual with the minimum
	 * error
	 * 
	 * @param a
	 *            The set of individuals
	 * @param cas
	 *            The case that will be tested with the individuals
	 * @return Maximum error, minimum error and minimum individual's index, in
	 *         that order.
	 */
	//TODO reajustar checkfit con checkfitPlus
	public ArrayList<Double> checkFit(ArrayList<Individual> a,
			ArrayList<Case> casos) {
		double maxError = 0;
		double minError = Double.MAX_VALUE;
		double minIndex = -1;

		for (Individual indi : a) {
			double totalError = 0;
			this.setWeights(indi.getWeights());

			/*
			 * se calcula el error acumulado por cada individuo para tener el
			 * fit (cuanto menos error mejor)
			 */
			totalError = this.testPackTSS(casos);

			/* Penalizacion */
			double aux = totalError;
			if (ANN.PENALTY) {
				double penalty = 0;
				for (int i = this.nParam * 2; i < this.conects.size()
						- this.nNeuHidLay - 1; i++) {
					double weight = this.conects.get(i).getWeight();
					if (weight >= ANN.INIRANG) {
						penalty += ANN.HIGHPEN;
					} else if (weight >= ANN.MIDRANG) {
						penalty += ANN.MEDPEN;
					} else if (weight >= ANN.LOWRANG) {
						penalty += ANN.LOWPEN;
					}
				}
				aux = totalError + penalty / indi.getWeights().size();
				if (aux > 1) {
					aux = 1;
				}
			}
			indi.setFit(aux);

			/*
			 * Recording the maximum error and the minimum error
			 */
			if (totalError > maxError) {
				maxError = totalError;
			}
			if (totalError < minError) {
				minError = totalError;
				minIndex = a.indexOf(indi);
			}
		}

		ArrayList<Double> d = new ArrayList<Double>();
		d.add(maxError);
		d.add(minError);
		d.add(minIndex);
		return d;
	}

	/**
	 * Has the same functionality than 'checkFit' with the added feature of taking record of the
	 * evolution of the GA.
	 * @param a Chromosomes to be checked
	 * @param casosTrain Training set used to check the fit
	 * @param casosTest Test set used to record the evolution
	 * @param data The record of the evolution
	 * @param ciclo The evolution cycle.
	 * @return Maximum error, minimum error and minimum individual's index, in
	 *         that order.
	 */
	public ArrayList<Double> checkFitPlus(ArrayList<Individual> a,
			ArrayList<Case> casosTrain, ArrayList<Case> casosTest,
			TrainAndTestError data, int ciclo) {
		double maxError = 0;
		double minError = Double.MAX_VALUE;
		double minIndex = -1;

		if (data == null) {
			data = new TrainAndTestError(-1, -1, -1);
		}

		for (Individual indi : a) {
			double totalError = 0;
			this.setWeights(indi.getWeights());

			/*
			 * se calcula el error acumulado por cada individuo para tener el
			 * fit (cuanto menos error mejor)
			 */
//			totalError = this.testPack(casosTrain);
			totalError = this.testPackTSS(casosTrain);

			/* Penalizacion */
			double aux = totalError;
			if (ANN.PENALTY) {
				double penalty = 0;
				switch(ANN.PENFUNC){
				case 1:
					penalty= this.penaltyFunc(casosTrain, 20);
					break;
				case 2:
					penalty= this.penaltyFunc2(casosTrain, 20);
					break;
				case 3:
					penalty= this.penaltyFunc3(casosTrain, 20);
					aux=Math.pow(aux, -4);
					break;
				case 4:
					penalty= this.penaltyFunc4(casosTrain, 20);
					break;
				}			
				aux+=penalty;
			}
			indi.setFit(aux);

			/*
			 * Recording the maximum error and the minimum error
			 */
			if (aux > maxError) {
				maxError = aux;
			}
			if (aux < minError) {
				minError = aux;
				minIndex = a.indexOf(indi);
			}

		}

		/*
		 * Extra lines are for the calculation of the best individual and to
		 * record its results in test cases
		 */

		Individual indi1 = a.get((int) minIndex);
		this.setWeights(indi1.getWeights());
		double errorTest = 1 - this.testPack(casosTest);
		double errorTrain = 1 - this.testPack(casosTrain);
		data.setCicle(ciclo);
		data.setTest(errorTest);
		data.setTraining(errorTrain);

		/*
		 * End of the extra lines
		 */

		ArrayList<Double> d = new ArrayList<Double>();
		d.add(maxError);
		d.add(minError);
		d.add(minIndex);
		return d;
	}

	@Deprecated
	public void estimateFile(String file, String output) {
		/*
		 * Reading the cases from the file and parsing them
		 */
		@SuppressWarnings("unused")
		ArrayList<Case> cases = Read.read2(file, this.nParam, this.nOutput);
		/*
		 * Writing the results of testing in a text file
		 */
		/*
		 * try { FileWriter fw = new FileWriter(output); BufferedWriter bw = new
		 * BufferedWriter(fw); for (int i = 0; i < cases.size(); i++) { Case tmp
		 * = cases.get(i); this.setInputs(tmp.getInputsBP()); Double predict =
		 * this.prediction(ANN.OUTTYPE); Double error = Math.abs(predict -
		 * tmp.getExpected());
		 * 
		 * System.out.println("Predicted: " + predict.toString() +
		 * " Real depth: " + Double.toString(tmp.getExpected()) + " || ERROR: "
		 * + error.toString()); bw.write(predict.toString() + ";" +
		 * Double.toString(tmp.getExpected()) + "\n");
		 * 
		 * }
		 * 
		 * bw.close();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return;
		 */
	}

	/**
	 * This method is responsible of generating the optimal weights for the ANN
	 * given a set of cases to test the results
	 * 
	 * @param maxGen Maximum number of generations for the algorithm
	 * @param maxIndividualPairs Number of chromosomes/2
	 * @param trainingSet The set used to evolve the chromosomes
	 * @return The set of weights to be used by the network
	 */
	public ArrayList<Double> geneticTraining(ArrayList<Case> trainingSet,
			ArrayList<Case> testSet, ArrayList<TrainAndTestError> evolution) {
		int ind = ANN.INDVIDUAL_PAIRS * 2;
		if (testSet != null) {
			if (evolution == null) {
				evolution = new ArrayList<TrainAndTestError>();
			}
		}

		double mutationFactor;
		/*
		 * Generation of the first population (the weights for the ANN)
		 */
		MyRandom rd = new MyRandom();
		ArrayList<Individual> population = new ArrayList<Individual>();
		for (int i = 0; i < ind; i++) {
			ArrayList<Double> tmp = new ArrayList<Double>();
			for (int j = 0; j < this.conects.size(); j++) {
				tmp.add(rd.nextDouble());
			}
			Individual aux = new Individual(tmp);
			population.add(aux);
		}

		/*
		 * Checking the fit of all the population
		 */
		double maxError = 0;
		double minError = Double.MAX_VALUE;
		int minIndex = -1;
		ArrayList<Double> res;
		if (testSet == null) {
			res = this.checkFit(population, trainingSet);
		} else {
			TrainAndTestError auxiliar = new TrainAndTestError(-1, -1, -1);
			res = this.checkFitPlus(population, trainingSet, testSet, auxiliar,
					0);
			evolution.add(auxiliar);
		}

		maxError = res.get(0);
		minError = res.get(1);
		
//		if(!ANN.PENALTY){
			this.setProperFit(population, minError, maxError);
//		}
		for (int count = 0; count < ANN.GENERATIONS; count++) {

			if (count % 10 == 0) {
				System.out.println("Generacion " + Integer.toString(count));
				System.out.println(maxError);
				System.out.println(minError);

			}
			maxError = 0;

			/*
			 * Selection of the fitness individuals
			 */
			ArrayList<Individual> offspring = new ArrayList<Individual>();
			double sumfit = 0;
			for (Individual tmp : population)
				sumfit += tmp.getFit();

			for (int abc = 0; abc < population.size(); abc++) {
				double prob = rd.nextDouble01();
				int i = 0;
				double sum = population.get(i).getFit() / sumfit;
				while (i < population.size() - 1 && sum < prob) {
					i++;
					sum += population.get(i).getFit() / sumfit;
				}
				this.addOffspring(offspring, population.get(i));
			}
			population = offspring;

			/*
			 * Crossing
			 */
			for (int i = 0; i < ind; i += 2) {
				double pc = rd.nextDouble01(); // Crossover probability
				if (pc <= ANN.CROSSOVER) {
					Individual a, b;
					a = population.get(i);
					b = population.get(i + 1);
					a.cross(b);
				}
			}

			/*
			 * Mutation
			 */
			mutationFactor = ANN.MUTFACT;
			for (Individual tmp : population) {
				double rnd = rd.nextDouble01();
				if (rnd <= mutationFactor) {
					this.mutateIndividual(tmp);
				}
			}

			/*
			 * Checking the fit of all the population
			 */
			maxError = 0;
			minError = Double.MAX_VALUE;
			if (testSet == null) {
				res = this.checkFit(population, trainingSet);
			} else {
				TrainAndTestError auxiliar = new TrainAndTestError(-1, -1, -1);
				res = this.checkFitPlus(population, trainingSet, testSet,
						auxiliar, count + 1);
				evolution.add(auxiliar);
			}
			maxError = res.get(0);
			minError = res.get(1);
			minIndex = (int) Math.floor(res.get(2));
			
				this.setProperFit(population, minError, maxError);

			//Exit condition fulfilled, we set count to max to exit loop
			if (minError < ANN.GENERR) {
				count = ANN.GENERATIONS;
			}
		}

		System.out.println("Generacion Final");
		System.out.println(maxError);
		System.out.println(minError);
		return population.get(minIndex).getWeights();
	}

	private void mutateIndividual(Individual ind) {
		double prob = new MyRandom().nextDouble01();
		int i = 0;
		double incr = (1.0 / ind.getWeights().size());
		double sum = incr;
		while (i < ind.getWeights().size() - 1 && sum < prob) {
			i++;
			sum += incr;
		}

		// Mutando el gen elegido al azar

		double gen = ind.getWeights().get(i);
		double variacion = 0;

		int signo = new MyRandom().nextInt(2);
		if (signo == 0) { // positivo
			variacion = new MyRandom().nextDouble01() * ANN.MUTCTE;
			ind.getWeights().set(i, gen + variacion);
		} else { // negativo
			variacion = new MyRandom().nextDouble01() * ANN.MUTCTE;
			ind.getWeights().set(i, gen - variacion);
		}

	}

	/**
	 * This methods returns an output according to the weights and inputs of the
	 * ANN
	 * 
	 * @return the prediction
	 */
	public ArrayList<Double> prediction(boolean Bol) {
		ArrayList<Double> ret = new ArrayList<Double>();
		if (!Bol) {
			for (Neuron n : this.output) {
				ret.add(n.finalSynapsis());
			}
			return ret;
		} else {
			for (Neuron n : this.output) {
				double aux = n.finalSynapsis();
				if (aux > ANN.POSITIVE_PREDICTION) {
					ret.add(1.);
				} else if (aux < ANN.NEGATIVE_PREDICTION) {
					ret.add(0.);
				} else {
					ret.add(-1.);
				}
			}
		}

		return ret;

	}

	/**
	 * This method activates or deactivates a neuron by enabling it to generate an output
	 * @param id The neuron identifier
	 * @param active The activation value for said neuron. True to switch on, false to switch off
	 */
	public void setActiveNeuron(int id, boolean active) {
		this.neurons.get(id).setActivated(active);
	}

	/**
	 * Sets the inputs to the Artificial Network
	 * 
	 * @param inputs
	 */
	public void setInputs(ArrayList<Double> inputs) {

		if (inputs.size() != this.nParam) {
			return;
		} else {
			for (int i = 0; i < this.nParam; i++) {
				this.extInputs.get(i).setValue(inputs.get(i));
			}
		}

	}

	/**
	 * Sets the inputs to the Artificial Network and normalizes them
	 * 
	 * @param inputs
	 * @param means
	 * @param desviations
	 */
	@Deprecated
	public void setInputs(ArrayList<Double> inputs, ArrayList<Double> means,
			ArrayList<Double> desviations) {

		if (inputs.size() != this.nParam) {
			return;
		} else {
			for (int i = 0; i < this.nParam; i++) {
				double normalizedInput = (inputs.get(i) - means.get(i))
						/ desviations.get(i);
				this.extInputs.get(i).setValue(normalizedInput);
			}
		}

	}

	/**
	 * Normalises the fit of the chromosomes in the range [0-1] to make easer to see which chromosome
	 * is better
	 * 
	 * @param ind The chromosomes
	 * @param minError The minimum fit in the population
	 * @param maxError The maximum fit in the population
	 */
	private void setProperFit(ArrayList<Individual> ind, double minError,
			double maxError) {
		for (Individual tmp : ind) {
			tmp.setFit(((tmp.getFit() - minError) / (maxError - minError)));
		}
	}

	/**
	 * Sets a given array with weights in the network
	 * 
	 * @param weights
	 */
	public void setWeights(ArrayList<Double> weights) {
		for (int i = 0; i < this.conects.size(); i++) {
			this.conects.get(i).setWeight(weights.get(i));
		}
	}

	public ArrayList<Double> getWeights() {
		ArrayList<Double> ret = new ArrayList<Double>();
		for (int i = 0; i < this.conects.size(); i++) {
			ret.add(this.conects.get(i).getWeight());
		}
		return ret;
	}

	/**
	 * This method measures the classification accuracy of the network with its actual set of weights.
	 * @param cases The set of cases to be used in classification
	 * @return It returns a double that represents the classification success percenetage
	 */
	public double testPack(ArrayList<Case> cases) {
		double totalError = 0;
		for (int i = 0; i < cases.size(); i++) {
			Case tmp = cases.get(i);
			this.setInputs(tmp.getInputsBP());
			ArrayList<Double> predict = this.prediction(ANN.OUTTYPE);
			if (!predict.equals(tmp.getExpected())) {
				totalError += 1;
			}
		}
		return 1 - (totalError / cases.size());
	}

	/**
	 * This method calculates the acumulated error between the expected output and the generated output
	 * in real output mode. This means that the output of the network is in the range [0-1] and not binary.
	 * This is the method used to calculate the initial fit for the chromosomes.
	 * @param cases The set of cases used to measure the error
	 * @return The normalised classification success is returned.
	 */
	public double testPackTSS(ArrayList<Case> cases) {
		double tss = 0;
		for (int i = 0; i < cases.size(); i++) {
			Case tmp = cases.get(i);
			this.setInputs(tmp.getInputsBP());
			ArrayList<Double> predict = this.prediction(false);
			for (int j = 0; j < this.nOutput; j++) {
				tss += Math.pow(predict.get(j) - tmp.getExpected().get(j), 2);
			}
		}
		double divisor= cases.size()*this.nOutput;
		/*% de acierto*/
		return 1- (tss/divisor);
	}
	/**
	 * This method calibrates the network by calling upon the BP algorithm
	 * @param cases The full set of cases available for the learning process. They will be divided into 
	 * test and traning cases.
	 * @param evolution A flag that enables and disables the record of the algorithm's evolution.
	 * @return If 'evolution' is true, returns an array with the record of the evolution.
	 */
	public ArrayList<TrainAndTestError> trainNetworkBP(ArrayList<Case> cases,
			boolean evolution) {
		/*
		 * Reading the cases from the file and parsing them
		 */

		ArrayList<Case> trainCases = new ArrayList<Case>();
		ArrayList<Case> testCases = new ArrayList<Case>();
		try {
			this.splitCases(cases, trainCases, testCases);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (evolution) {
			return this.backPropagationLearning(trainCases, testCases);
		}
		return this.backPropagationLearning(trainCases, null);

	}

	/**
	 * This method trains a network given a file with training cases, the number
	 * of pairs of individuals, the max generations that will be created and a
	 * boolean to decide which kind of training will be done.
	 * 
	 * @param file
	 *            String with the path of the file with the cases
	 * @param pairs
	 *            Pairs of individuals in each generation
	 * @param gens
	 *            Max generations
	 * @param evoGraph
	 *            If true, the method will record the necesary information to
	 *            generate an Evolution or Error graphic
	 * @return
	 */
	public ArrayList<TrainAndTestError> trainNetworkAG(ArrayList<Case> cases,
			boolean evoGraph) {
		ArrayList<Case> trainCases = new ArrayList<Case>();
		ArrayList<Case> testCases = new ArrayList<Case>();
		ArrayList<TrainAndTestError> evolution = null;

		try {
			this.splitCases(cases, trainCases, testCases);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (evoGraph) {
			evolution = new ArrayList<TrainAndTestError>();
			this.setWeights(this.geneticTraining(trainCases, testCases,
					evolution));

		} else {

			this.setWeights(this.geneticTraining(trainCases, null, null));
		}

		System.out.println("Acurancy:"
				+ String.valueOf(this.testPack(testCases)));
		return evolution;
	}

	public void updateWeights() {
		for (Neuron n : this.neurons) {
			n.recalculateWeights();
		}
	}
	
	/**
	 * This method saves all the configuration used in the training to be written in a file later.
	 * @param cfg The object 'ConfigurationTestResults' that stores all the information concerning multiple 
	 * trainings.
	 */
	public void setCfg(ConfigurationTestResults cfg) {
		cfg.setCROSSOVER(CROSSOVER);
		cfg.setGENERATIONS(GENERATIONS);
		cfg.setGENERR(GENERR);
		cfg.setHIGHPEN(HIGHPEN);
		cfg.setINDVIDUAL_PAIRS(INDVIDUAL_PAIRS);
		cfg.setINIRANG(INIRANG);
		cfg.setLOWPEN(LOWPEN);
		cfg.setLOWRANG(LOWRANG);
		cfg.setMAX_ERROR(MAX_ERROR_BP);
		cfg.setMEDPEN(MEDPEN);
		cfg.setMIDRANG(MIDRANG);
		cfg.setMUTCTE(MUTCTE);
		cfg.setMUTFACT(MUTFACT);
		cfg.setOUTTYPE(OUTTYPE);
		cfg.setPENALTY(PENALTY);
		cfg.setLEARNING_FACTOR(LEARNING_FACTOR);
		cfg.setCICLES(CICLES_BP);
		cfg.setMOMENTUM_FACTOR(ANN.MOMFACTOR);
		cfg.setMOMENTUM_FLAG(ANN.MOMFACTORFLAG);
		cfg.setNEGATIVE_PREDICTION(ANN.NEGATIVE_PREDICTION);
		cfg.setPOSITIVE_PREDICTION(POSITIVE_PREDICTION);
		cfg.setParams(this.nParam);
		cfg.setnHidden(this.nHiddenLayers);
		cfg.setnNeuHidden(this.nNeuHidLay);
		cfg.setOut(nOutput);
	}
	/**
	 * This method stores the results of a single training with one of the algorithms to print it later.
	 * It stores the success rate, the success rate with one neuron of and two neurons of and, if required,
	 * the results of the network when noise is added.
	 * @param set Case set to test the network
	 * @param iteration The identifier of the training. There can be multiple trainings stored in a
	 * 'ConfigurationTestResult'
	 * @return Returns an ExecutionResult object that stores all this data.
	 */
	public ExecutionResult testCaseSet(ArrayList<Case> set, int iteration) {
		ExecutionResult er = new ExecutionResult(iteration);
		er.setResult(this.testPack(set));
		er.setWeights(this.getWeights());
		this.neuronOff1(er, set);
		this.neuronOff2(er, set);
		this.neuronOn1(er, set);
		if (ANN.NOISE_ON) {
			ArrayList<NoiseResult> nrs;
			ArrayList<Case> noisedSet;
			nrs = new ArrayList<>();
			NoiseResult nr;
			// TODO numeros magicos
			for (double i = 0.001; i <= 0.041; i += 0.001) {
				noisedSet = new ArrayList<Case>();
				for (int j=1; j<set.size(); j++) {
					Case aux=set.get(j);
					noisedSet.add(Case.noiseFilter(aux, i));
				}
				nr = new NoiseResult();
				nr.setNoiseLevel(i);
				nr.setResult(this.testPack(noisedSet));
				nrs.add(nr);
			}
			er.setNoiseResults(nrs);
		}

		return er;

	}
	/**
	 * This method tests the network with only the first neuron on.
	 * @param er
	 * @param set
	 */
	private void neuronOn1(ExecutionResult er, ArrayList<Case> set) {
		ArrayList<Boolean> bols = new ArrayList<Boolean>();
		for (int x = 1; x < this.nParam; x++) {
			bols.add(this.neurons.get(x).isActivated());
			this.setActiveNeuron(x, false);
		}

		NeuronValues nv = new NeuronValues();
		nv.setNeuronID(2000);

		double ret = this.testPack(set);
		nv.setResult(ret);
		er.addNeuronValues(nv);
		nv.setDownValue(this.neurons.get(0).downValue());
		nv.setUpValue(this.neurons.get(0).upValue());

		for (int x = 1; x < this.nParam; x++) {
			this.setActiveNeuron(x, bols.get(x - 1));
		}

	}
	
	/**
	 * This method tests the network with all the possible combinations of two hidden neurons offline.
	 * @param er
	 * @param set
	 */
	public void neuronOff2(ExecutionResult er, ArrayList<Case> set) {
		for (int x = this.nParam; x < this.output.get(0).getNeuronId() - 1; x++) {
			boolean tmp1 = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);
			for (int y = x + 1; y < this.output.get(0).getNeuronId(); y++) {
				NeuronValues nv = new NeuronValues();
				nv.setNeuronID(x);
				nv.setNeuronID2(y);
				boolean tmp2 = this.neurons.get(y).isActivated();
				this.setActiveNeuron(y, false);
				double ret = this.testPack(set);
				nv.setResult(ret);
				er.addComboNeuronValues(nv);
				this.setActiveNeuron(y, tmp2);
			}
			this.setActiveNeuron(x, tmp1);
		}
	}

	/**
	 * This method tests the network while shutting down one neuron at a time (only input and hidden neurons)
	 * @param er
	 * @param set
	 */
	public void neuronOff1(ExecutionResult er, ArrayList<Case> set) {
		for (int x = 0; x < this.output.get(0).getNeuronId(); x++) {
			NeuronValues nv = new NeuronValues();
			nv.setNeuronID(x);
			boolean tmp = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);

			double ret = this.testPack(set);
//			double auxaux= this.testPack(set);
			nv.setResult(ret);
			nv.setDownValue(this.neurons.get(x).downValue());
			nv.setUpValue(this.neurons.get(x).upValue());
			er.addNeuronValues(nv);

			this.setActiveNeuron(x, tmp);
		}
	}
	
	public double penaltyFunc(ArrayList<Case> set, int lastNeuron){
		ArrayList<NeuronValues> neuronVal= new ArrayList<NeuronValues>();
		for (int x = 0; x < lastNeuron; x++) {
			NeuronValues nv = new NeuronValues();
			nv.setNeuronID(x);
			boolean tmp = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);
			double ret = this.testPackTSS(set);
			nv.setResult(ret);
			neuronVal.add(nv);
			this.setActiveNeuron(x, tmp);
		}
		double acumulator=0;
		for(NeuronValues aux: neuronVal){
			acumulator+= aux.getResult();
		}
		return acumulator;
	}
	
	public double penaltyFunc2(ArrayList<Case> set, int lastNeuron){
		ArrayList<NeuronValues> neuronVal= new ArrayList<NeuronValues>();
		for (int x = 0; x < lastNeuron; x++) {
			NeuronValues nv = new NeuronValues();
			nv.setNeuronID(x);
			boolean tmp = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);
			double ret = this.testPackTSS(set);
			nv.setResult(ret);
			neuronVal.add(nv);
			this.setActiveNeuron(x, tmp);
		}
		double acumulator=0;
		for(NeuronValues aux: neuronVal){
			acumulator+= aux.getResult();
		}
		return acumulator/neuronVal.size();
	}
	
	public double penaltyFunc3(ArrayList<Case> set, int lastNeuron){
		ArrayList<NeuronValues> neuronVal= new ArrayList<NeuronValues>();
		for (int x = 0; x < lastNeuron; x++) {
			NeuronValues nv = new NeuronValues();
			nv.setNeuronID(x);
			boolean tmp = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);
			double ret = Math.pow(this.testPackTSS(set), -4);
			nv.setResult(ret);
			neuronVal.add(nv);
			this.setActiveNeuron(x, tmp);
		}
		double acumulator=0;
		for(NeuronValues aux: neuronVal){
			acumulator+= aux.getResult();
		}
		return acumulator;
	}
	
	public double penaltyFunc4(ArrayList<Case> set, int lastNeuron){
		ArrayList<NeuronValues> neuronVal= new ArrayList<NeuronValues>();
		boolean flag=false;
		for (int x = 0; x < lastNeuron; x++) {
			NeuronValues nv = new NeuronValues();
			nv.setNeuronID(x);
			boolean tmp = this.neurons.get(x).isActivated();
			this.setActiveNeuron(x, false);
			double ret = this.testPackTSS(set);
			nv.setResult(ret);
			neuronVal.add(nv);
			this.setActiveNeuron(x, tmp);
			if(x==0 && ret<0.7){
				flag=true;
			}
		}
		double acumulator=0;
		for(NeuronValues aux: neuronVal){
			acumulator+= aux.getResult();
		}
		if(flag){
			return 0;
		}else{
			return acumulator;
		}
	}

	public ArrayList<Case> getFailures(ArrayList<Case> set) {
		ArrayList<Case> failures = new ArrayList<Case>();
		for (int i = 0; i < set.size(); i++) {
			Case tmp = set.get(i);
			this.setInputs(tmp.getInputsBP());
			ArrayList<Double> predict = this.prediction(ANN.OUTTYPE);
			if (!predict.equals(tmp.getExpected())) {
				failures.add(tmp);
			}
		}
		return failures;

	}


	/**
	 * This method is used to automate the testing with the network. According to the fields at the beginning 
	 * of this class, the user can define the number of training with each algorithm, the algorithms to be used
	 * and if he wants to test 'Sweep mode' after the training. Sweep mode shuts down neurons one by one to find 
	 * the point where the system no longer works. Returns a ConfigurationTestResult object containing all the results
	 * @param set The full set of cases for the learning process
	 */
	public ConfigurationTestResults testBench(ArrayList<Case> set) {

		ConfigurationTestResults cfg = new ConfigurationTestResults(null);
		this.setCfg(cfg);
		cfg.dataset=set;
		int iteration = 0;
		int maxIterations;
		if (SWEEP) {
			maxIterations = (this.nParam - 1) * 2;
		} else {
			maxIterations = ITERATIONS;
		}
		
		//TODO Recycle code for the love of god
		// BACKPROPAGATION
		if (ANN.BPON) {
			for (int i = 0; i < maxIterations; i++) {
				System.out.println("Ejecucion " + iteration);
				if (SWEEP) {
					this.sweep(i);
				}
				cfg.addResultBP(this.execution(BP, set, iteration));
				iteration++;
			}
		}

		// GENETIC ALGORITHM
		if (ANN.AGON) {
			for (int i = 0; i < maxIterations; i++) {
				System.out.println("Ejecucion " + iteration);
				if (SWEEP) {
					this.sweep(i);
				}
				cfg.addResultAG(this.execution(AG, set, iteration));
				iteration++;
			}
		}

		// GENETIC ALGORITHM WITH PENALTY
		if (ANN.APPON) {
			for (int i = 0; i < maxIterations; i++) {
				System.out.println("Ejecucion " + iteration);
				if (SWEEP) {
					this.sweep(i);
				}
				
				cfg.addResultAPP(this.execution(APP, set, iteration));
				iteration++;
			}
		}

		return cfg;

	}

	/**
	 * This variant writes the result in the given rootDirectory
	 * @param set
	 * @param rootDirectory
	 */
	public void testBench(ArrayList<Case> set, File rootDirectory) {
		if (rootDirectory != null && !rootDirectory.exists()) {
			rootDirectory.mkdir();
		}
		
		ConfigurationTestResults cfg = testBench(set);
		
		cfg.setRootFolder(rootDirectory);
		try {
			cfg.writeResults();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void randomizeWeights() {
		MyRandom rd = new MyRandom();
		for (Input i : this.conects) {
			i.setWeight(rd.nextDouble() * 0.3);
		}
	}

	/**
	 * This function divides the case set into trainning and test set according the 'trainingPercentage'.
	 * The order of the cases is always randomised before spliting them.
	 * @param cases Original set
	 * @param training set to store the training cases
	 * @param test set to store the test cases
	 * @throws Exception If one of the arrays doesn't exist.
	 */
	public void splitCases(ArrayList<Case> cases, ArrayList<Case> training,
			ArrayList<Case> test) throws Exception {
		if (training == null || test == null) {
			throw new Exception("No existe training o test");
		}
		Collections.shuffle(cases);

		int i;
		for (i = 0; i <= cases.size() * this.trainingPercentage; i++) {
			training.add(cases.get(i));
		}
		for (; i < cases.size(); i++) {
			test.add(cases.get(i));
		}

	}

	/**
	 * This method shuts down the neurons of the network one by one, making a sweep. It only affects input neurons.
	 * While the phase is smaller than the number of input neurons, the sweep is done forward. This means
	 * that the neurons are set offline from the first to the last. If the phase is bigger than
	 * the number of input neurons, the sweep is done backwards, from the last to the first. 
	 * @param phase
	 */
	public void sweep(int phase) {
		boolean reverse;
		if ((phase / (this.nParam - 1) % 2) == 0) {
			reverse = false;
		} else {
			reverse = true;
		}

		int phaseMod = phase % (this.nParam - 1);

		if (!reverse) {
			for (int i = 0; i < this.nParam; i++) {
				if (i <= phaseMod) {
					this.setActiveNeuron(i, false);
				} else {
					this.setActiveNeuron(i, true);
				}
			}
		} else {
			for (int i = 0; i < this.nParam; i++) {
				if (i < (this.nParam - 1) - phaseMod) {
					this.setActiveNeuron(i, true);
				} else {
					this.setActiveNeuron(i, false);
				}
			}
		}

	}
	
	
	public static void DBBENCH(String dbfile, int id_setup){
		Connection conn = Read.getDBConnection(dbfile);
		ANNConfig ac = Read.readConfigDB(conn, id_setup);
		ArrayList<Case> cases = null;
		
		try {
			cases = Read.readFromDB(conn, ac.getSelectCasesStatement());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		ANN ann = new ANN(null, ac);
		//TODO Something something query to write the stuff in db (how, and what? nobody knows)
		ConfigurationTestResults cfg = ann.testBench(cases);	
		
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * A preset configuration to train the CANCER problem
	 * @param subdirectory
	 */
	public static void CANCER(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(9);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(8);
		ac.setnOutput(1);

		ArrayList<Case> cases = Read.read2(path + Read.CANCER, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\CANCER");
		ann.testBench(cases, root);
	}
	 /**
	  * A second preset configuration for the problem
	  * @param subdirectory
	  */
	public static void CANCER2(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(9);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(8);
		ac.setnOutput(2);

		ArrayList<Case> cases = Read.read2(path + Read.CANCER2, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\CANCER2");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the CARD problem
	 * @param subdirectory
	 */
	public static void CARD(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(51);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(6);
		ac.setnOutput(1);

		ArrayList<Case> cases = Read.read2(path + Read.CARD, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\CARD");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the HEART problem
	 * @param subdirectory
	 */
	public static void HEART(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(13);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(8);
		ac.setnOutput(2);

		ArrayList<Case> cases = Read.read2(path + Read.HEART, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\HEART");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the PIMA problem
	 * @param subdirectory
	 */
	public static void PIMA(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(8);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(6);
		ac.setnOutput(1);

		ArrayList<Case> cases = Read.read2(path + Read.PIMA, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\PIMA");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the SONAR problem
	 * @param subdirectory
	 */
	public static void SONAR(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(60);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(12);
		ac.setnOutput(1);

		ArrayList<Case> cases = Read.read2(path + Read.SONAR, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\SONAR");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the GLASS problem
	 * @param subdirectory
	 */
	public static void GLASS(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(9);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(12);
		ac.setnOutput(6);

		ArrayList<Case> cases = Read.read2(path + Read.GLASS, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\GLASS");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the HORSE problem
	 * @param subdirectory
	 */
	public static void HORSE(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(58);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(12);
		ac.setnOutput(3);

		ArrayList<Case> cases = Read.read2(path + Read.HORSE, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\HORSE");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the SEM force curves
	 * @param subdirectory
	 */
	public static void CURVAS(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.1);
		ac.setmFactor(0.1);
		ac.setnParam(20);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(8);
		ac.setnOutput(2);

		ArrayList<Case> cases = Read.read2(path + Read.CURVAS, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(null, ac);
		File root = new File(path + subdirectory + "\\CURVAS");
		ann.testBench(cases, root);
	}
	/**
	 * A preset configuration to train the SEM force curves with special neuron configuration
	 * @param subdirectory
	 */
	public static void CURVASNEUCONF(String subdirectory) {
		ANNConfig ac = new ANNConfig();

		ac.setlFactor(0.05);
		ac.setmFactor(0.1);
		ac.setnParam(20);
		ac.setnHidLay(1);
		ac.setNeuronsHidLay(8);
		ac.setnOutput(2);

		ArrayList<Case> cases = Read.read2(path + Read.CURVAS, ac.getParam(),
				ac.getNoutput());
		ANN ann = new ANN(path + "\\RED.txt", ac);
		File root = new File(path + subdirectory + "\\CURVASNEUCONF");
		ann.testBench(cases, root);
	}
	/**
	 * This method does a single training of the network with a specified algorithm and stores it in
	 * a 'ExecutionResult' object.
	 * @param type The algorithm used
	 * @param set The full case set
	 * @param iteration the training identifier.
	 * @return
	 */
	public ExecutionResult execution(int type, ArrayList<Case> set,
			int iteration) {
		ExecutionResult er;
		ArrayList<TrainAndTestError> tmp = null;
		this.randomizeWeights();
		switch (type) {
		case ANN.AG:
			ANN.PENALTY = false;
			tmp = this.trainNetworkAG(set, ANN.EVOLUTIONGRAPH);
			break;
		case ANN.BP:
			tmp= this.trainNetworkBP(set, ANN.EVOLUTIONGRAPH);
			break;
		case ANN.APP:
			ANN.PENALTY=true;
			tmp= this.trainNetworkAG(set, EVOLUTIONGRAPH);
			ANN.PENALTY=false;
			break;
		}
		er = this.testCaseSet(set, iteration);
		er.setFailures(this.getFailures(set));
		er.setEvolution(tmp);
		return er;
	}

}
