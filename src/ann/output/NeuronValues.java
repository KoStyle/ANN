package ann.output;

import ann.util.UpValueData;

public class NeuronValues {
	private int neuronID, neuronID2;
	private double downValue, result;
	private UpValueData upValue;
	private int execution;
	/**
	 * @return the downValue
	 */
	public double getDownValue() {
		return downValue;
	}
	/**
	 * @param downValue the downValue to set
	 */
	public void setDownValue(double downValue) {
		this.downValue = downValue;
	}
	/**
	 * @return the upValue
	 */
	public UpValueData getUpValue() {
		return upValue;
	}
	/**
	 * @param upValue the upValue to set
	 */
	public void setUpValue(UpValueData upValue) {
		this.upValue = upValue;
	}
	/**
	 * @return the neuronID
	 */
	public int getNeuronID() {
		return neuronID;
	}
	/**
	 * @param neuronID the neuronID to set
	 */
	public void setNeuronID(int neuronID) {
		this.neuronID = neuronID;
	}
	/**
	 * @return the result
	 */
	public double getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(double result) {
		this.result = result;
	}
	public int getIteration() {
		return execution;
	}
	public void setIteration(int iteration) {
		this.execution = iteration;
	}
	public int getNeuronID2() {
		return neuronID2;
	}
	public void setNeuronID2(int neuronID2) {
		this.neuronID2 = neuronID2;
	}
}
