/**
 * 
 */
package ls.assignment1.elements;

import java.util.ArrayList;

import ls.assignment1.util.NeuronConfig;
import ls.assignment1.util.Sigmoid;
import ls.assignment1.util.UpValueData;

/**
 * @author konomi
 * 
 */
public class Neuron {

	// CONSTANTS
	public static final int ALWAYS = 1;
	public static final int LEARNING = 2;
	public static final int EXECUTING = 3;
	public static final int NONE = 4;

	private ArrayList<Input> ustream; // Upstream
	private ArrayList<Input> dstream; // Downstream

	private Sigmoid sigmoidFunction;
	private Sigmoid secondarySigmoid=null;
	private double lFactor;
	private double oldOutput;
	private double error;
	private int failureType = -1;
	private double failure;
	private boolean activated = true;
	private int neuronId;
	private boolean isLearning = true;

	public Neuron() {
		this.ustream = new ArrayList<Input>();
		this.dstream = new ArrayList<Input>();
		this.sigmoidFunction = new Sigmoid();
		this.sigmoidFunction.setBeta(8);
	}
	
	public Neuron(NeuronConfig cfg){
		this.ustream = new ArrayList<Input>();
		this.dstream = new ArrayList<Input>();
		this.sigmoidFunction = new Sigmoid();
		this.sigmoidFunction.setBeta(8);
		this.neuronId=cfg.getId();
		this.failure=cfg.getFailure();
		this.failureType=cfg.getFailureType();
		this.lFactor=cfg.getLf();
		this.sigmoidFunction=new Sigmoid(cfg.getAlfa(), cfg.getBeta(), cfg.getGamma());
		this.activated=cfg.getActivation();
		
	}

	public void addConection(Input cn) {
		if (cn != null) {
			this.ustream.add(cn);
		}
	}

	public void addDstream(Input cn) {
		if (cn != null) {
			this.dstream.add(cn);
		}
	}

	public double synapsis() {
		double ret = 0;
		if (this.activated) {
			for (Input tmp : this.ustream) {
				ret += tmp.getInput() * tmp.getWeight();
			}
			this.oldOutput = this.sigmoid(ret);
			
			switch (this.failureType) {
			case ALWAYS:
				this.oldOutput = this.oldOutput * (1-this.failure);
				break;
			case LEARNING:
				if (this.isLearning) {
					this.oldOutput = this.oldOutput * (1-this.failure);
				}
				break;
			case EXECUTING:
				if (!this.isLearning) {
					this.oldOutput = this.oldOutput * (1-this.failure);
				}
				break;
			}
		} else {
			this.oldOutput = 0;
		}
		return this.oldOutput;
	}

	private double sigmoid(double net) {
		return this.sigmoidFunction.sigmoidOperation(net);
	}

	public double finalSynapsis() {
		return this.synapsis();
	}

	public void setLearningFactorR(double lF) {
		this.lFactor = lF;
		for (Input tmp : this.ustream) {
			tmp.setLearningFactorR(this.lFactor);
		}
	}

	public double recalculateErrorOutput(double spected) {
		// (t-Out)Out(1-Out)
		this.error = (spected - this.oldOutput) * this.oldOutput* (1 - this.oldOutput);

		for (Input i : this.ustream) {
			i.setErrorForward(error);
		}
		return this.error;
	}

	public double recaculateError() {
		// (1-Out)Out(SUM(err*peso))
		double sum = 0;
		for (Input i : this.dstream) {
			sum += i.getWeight() * i.getErrorForward();
		}

		this.error = (1 - this.oldOutput) * this.oldOutput * sum;
		for (Input i : this.ustream) {
			i.setErrorForward(error);
		}

		return this.error;
	}

	public void recalculateWeights() {
		for (Input i : this.ustream) {
			i.updateWeight();
		}
	}

	public double getOldOutput() {
		return this.oldOutput;
	}

	public void setFailure(double error) throws Exception {
		if (error > 1 || error < 0 || this.failureType > 4
				|| this.failureType < 1) {
			throw new Exception(
					"Imposible to set the failure rate for the neuron");
		}

		this.failure = error;
	}
	
	public void setSigmoid(boolean value){
		this.sigmoidFunction.setActivation(value);
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the neuronId
	 */
	public int getNeuronId() {
		return neuronId;
	}

	/**
	 * @return the lFactor
	 */
	public double getlFactor() {
		return lFactor;
	}

	/**
	 * @param lFactor the lFactor to set
	 */
	public void setlFactor(double lFactor) {
		this.lFactor = lFactor;
	}
	
	public void applyLFactor(){
		for(Input i: this.ustream){
			i.setLFactor(this.lFactor);
		}
	}
	
	public double downValue(){
		double sumatorio=0;
		for(Input i: this.dstream){
			sumatorio+=Math.abs(i.getWeight());
		}
		double media= sumatorio/(double)this.dstream.size();
		
		return media;
	}
	
	public UpValueData upValue(){
		UpValueData ret = new UpValueData();
		
		double maxRange=0;
		double minRange=0;
		double sum=0;
		
		for(Input i: this.ustream){
			if(i.getWeight()>0){
				maxRange+=i.getWeight();
			}else{
				minRange+=i.getWeight();
			}
			sum+= i.getWeight();
		}
		
		ret.setMean(sum/(double)this.ustream.size());
		ret.setMaxRange(maxRange);
		ret.setMinRange(minRange);
		
		return ret;
	}
}
