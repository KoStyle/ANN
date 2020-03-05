package ann.elements;


public class Input {

	private boolean isNeuron = false;
	private ExtInput input;
	private Neuron nInput;
	private double weight;
	private double lFactor; //Learning factor
	private double errForward;
	private double oldIncrement=0;
	private double mFactor; //Momentum factor

	/* Constructors, getters and setters */

	public Input(ExtInput input, double weight) {
		super();
		this.input = input;
		this.weight = weight;
		this.isNeuron = false;

	}

	public Input(Neuron nInput, double weight) {
		super();
		this.nInput = nInput;
		this.weight = weight;
		this.isNeuron = true;
	}

	/**
	 * @return the isNeuron
	 */
	public boolean isNeuron() {
		return isNeuron;
	}

	/**
	 * @param isNeuron
	 *            the isNeuron to set
	 */
	public void setNeuron(boolean isNeuron) {
		this.isNeuron = isNeuron;
	}

	/**
	 * @return the input
	 */
	public double getInput() {
		if (this.isNeuron) {
			return this.nInput.synapsis();
		} else {
			return input.getValue();
		}
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(ExtInput input) {
		this.input = input;
	}

	/**
	 * @return the nInput
	 */
	@Deprecated
	public Neuron getnInput() {
		return nInput;
	}

	/**
	 * @param nInput
	 *            the nInput to set
	 */
	public void setnInput(Neuron nInput) {
		this.nInput = nInput;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	//TODO Ineficiente
	public void setLearningFactorR(double lF){
		this.lFactor=lF;
		if(this.isNeuron){
			this.nInput.setLearningFactorR(lF);
		}
		
	}
	
	public void setErrorForward(double err){
		this.errForward=err;
	}
	
	public double getErrorForward(){
		return this.errForward;
	}
	
	
	public void updateWeight(){
		if(!this.isNeuron){
			//double increment=this.errForward*this.lFactor*this.input.getValue();
			//this.weight=this.weight+increment;
		}else{
			double x=this.nInput.getOldOutput();
			double increment=this.errForward*this.lFactor*x;
			this.weight=this.weight+increment;
		}
	}
	
	public void updateWeightMomentum(){
		if(!this.isNeuron){
			//double increment=this.errForward*this.lFactor*this.input.getValue();
			//this.weight=this.weight+increment;
		}else{
			double x=this.nInput.getOldOutput();
			double increment=this.errForward*this.lFactor*x;
			this.weight=this.weight+increment + this.mFactor*this.oldIncrement;
			this.oldIncrement=increment;
		}
	}

	/**
	 * @param oldIncrement the oldIncrement to set
	 */
	public void setOldIncrement(double oldIncrement) {
		this.oldIncrement = oldIncrement;
	}

	/**
	 * @param mFactor the mFactor to set
	 */
	public void setmFactor(double mFactor) {
		this.mFactor = mFactor;
	}

	public void setLFactor(double lFactor2) {
		this.lFactor=lFactor2;
	}

}
