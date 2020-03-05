package ls.assignment1.util;

public class NeuronConfig {
	private double failure=0, alfa=1, alfa2=1, beta=1, beta2=1, gamma=1, gamma2=1, lf;
	private int failureType=0, id, neuronsLay, param, lay;
	private boolean activation=true;
	
	/*
	 * FAILURE TYPES
	 * TYPE 0: No failure at all
	 * TYPE 1: Fails in training
	 * TYPE 2: Fails in real execution
	 * TYPE 3: Fails always
	 * 
	 * 
	 */
	public NeuronConfig(double lf, int id) {
		super();
		this.lf = lf;
		this.id = id;
	}
	/**
	 * @return the failure
	 */
	public double getFailure() {
		return failure;
	}
	/**
	 * @param failure the failure to set
	 */
	public void setFailure(double failure) {
		this.failure = failure;
	}
	/**
	 * @return the alfa
	 */
	public double getAlfa() {
		return alfa;
	}
	/**
	 * @param alfa the alfa to set
	 */
	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}
	/**
	 * @return the alfa2
	 */
	public double getAlfa2() {
		return alfa2;
	}
	/**
	 * @param alfa2 the alfa2 to set
	 */
	public void setAlfa2(double alfa2) {
		this.alfa2 = alfa2;
	}
	/**
	 * @return the beta
	 */
	public double getBeta() {
		return beta;
	}
	/**
	 * @param beta the beta to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}
	/**
	 * @return the beta2
	 */
	public double getBeta2() {
		return beta2;
	}
	/**
	 * @param beta2 the beta2 to set
	 */
	public void setBeta2(double beta2) {
		this.beta2 = beta2;
	}
	/**
	 * @return the gamma
	 */
	public double getGamma() {
		return gamma;
	}
	/**
	 * @param gamma the gamma to set
	 */
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
	/**
	 * @return the gamma2
	 */
	public double getGamma2() {
		return gamma2;
	}
	/**
	 * @param gamma2 the gamma2 to set
	 */
	public void setGamma2(double gamma2) {
		this.gamma2 = gamma2;
	}
	/**
	 * @return the lf
	 */
	public double getLf() {
		return lf;
	}
	/**
	 * @param lf the lf to set
	 */
	public void setLf(double lf) {
		this.lf = lf;
	}
	/**
	 * @return the failureType
	 */
	public int getFailureType() {
		return failureType;
	}
	/**
	 * @param failureType the failureType to set
	 */
	public void setFailureType(int failureType) {
		this.failureType = failureType;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the activation
	 */
	public boolean getActivation() {
		return activation;
	}
	/**
	 * @param activation the activation to set
	 */
	public void setActivation(boolean activation) {
		this.activation = activation;
	}
	/**
	 * @return the neuronsLay
	 */
	public int getNeuronsLay() {
		return neuronsLay;
	}
	/**
	 * @param neuronsLay the neuronsLay to set
	 */
	public void setNeuronsLay(int neuronsLay) {
		this.neuronsLay = neuronsLay;
	}
	/**
	 * @return the param
	 */
	public int getParam() {
		return param;
	}
	/**
	 * @param param the param to set
	 */
	public void setParam(int param) {
		this.param = param;
	}
	/**
	 * @return the lay
	 */
	public int getLay() {
		return lay;
	}
	/**
	 * @param lay the lay to set
	 */
	public void setLay(int lay) {
		this.lay = lay;
	}

}
