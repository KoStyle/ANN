/**
 * 
 */
package ann.input;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author konomi
 * 
 */
public class Case {
	@Deprecated
	private double hFE;
	@Deprecated
	private double integral;
	@Deprecated
	private double meanValue;
	@Deprecated
	private double result;
	private ArrayList<Double> expected, predicted, predictedPerc;
	private ArrayList<Double> data;
	private int nData;
	private int nResults;
	private int id;
	private int sub_id;

	/**
	 * @return the nData
	 */
	public int getnData() {
		return nData;
	}

	/**
	 * @param nData the nData to set
	 */
	public void setnData(int nData) {
		this.nData = nData;
	}

	@Deprecated
	public Case(double hFE, double integral, double meanValue) {
		super();
		this.hFE = hFE;
		this.integral = integral;
		this.meanValue = meanValue;
	}

	@Deprecated
	public Case(double hFE, double integral, double meanValue, double result) {
		super();
		this.hFE = hFE;
		this.integral = integral;
		this.meanValue = meanValue;
		this.result = result;
	}

	public Case(int nData, int nResult) {
		this.nData = nData;
		this.nResults= nResult;
		this.data = new ArrayList<Double>();
		this.expected=new ArrayList<Double>();
	}
	
	@SuppressWarnings("unchecked")
	public Case(Case kaseo) {
		this.nData=kaseo.nData;
		this.nResults=kaseo.nResults;
		this.expected= (ArrayList<Double>) kaseo.expected.clone();
		this.predicted= new ArrayList<Double>();
		this.predictedPerc= new ArrayList<Double>();
		this.id=kaseo.id;
		this.sub_id=kaseo.sub_id;
	}

	public void addData(Double data) throws Exception {
		if (this.data.size() >= this.nData) {
			throw new Exception("Too many values in the case");
		} else {
			this.data.add(data);
		}
	}
	
	public void addExpected(Double expected) throws Exception {
		if (this.expected.size() >= this.nResults) {
			throw new Exception("Too many expected results in the case");
		} else {
			this.expected.add(expected);
		}
	}
	
	public Double getData(int index){
		return this.data.get(index);
	}
	
	public void setData(Double data, int index){
		this.data.set(index, data);
	}

	/**
	 * @return the hFE
	 */
	@Deprecated
	public double getHFE() {
		return this.hFE;
	}
	@Deprecated
	public ArrayList<Double> getInputs() {
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(this.getHFE());
		inputs.add(this.getIntegral());
		inputs.add(this.getMeanValue());
		return inputs;
	}

	public ArrayList<Double> getInputsBP() {
		return this.data;
	}

	/**
	 * @return the integral
	 */
	@Deprecated
	public double getIntegral() {
		return integral;
	}

	/**
	 * @return the meanValue
	 */
	@Deprecated
	public double getMeanValue() {
		return meanValue;
	}

	/**
	 * @return the result
	 */
	@Deprecated
	public double getResult() {
		return result;
	}

	/**
	 * @param hFE
	 *            the hFE to set
	 */
	@Deprecated
	public void setHFE(double hFE) {
		this.hFE = hFE;
	}

	/**
	 * @param integral
	 *            the integral to set
	 */
	@Deprecated
	public void setIntegral(double integral) {
		this.integral = integral;
	}

	/**
	 * @param meanValue
	 *            the meanValue to set
	 */
	@Deprecated
	public void setMeanValue(double meanValue) {
		this.meanValue = meanValue;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	@Deprecated
	public void setResult(double result) {
		this.result = result;
	}

	public ArrayList<Double> getExpected() {
		return expected;
	}

	public void setExpected(ArrayList<Double> expected) {
		this.expected = expected;
	}
	
	public void setExpected(Double expected, int index){
		this.expected.set(index, expected);
	}

	public int getnResults() {
		return nResults;
	}

	public void setnResults(int nResults) {
		this.nResults = nResults;
	}
	
	public static Case noiseFilter(Case original, double noiseLevel){
		Case ret= new Case(20, 2);
		Random rd= new Random();
		for(int i=0; i<original.getnData(); i++){
			double noise=(rd.nextGaussian() * noiseLevel);
			try {
				ret.addData(original.getData(i) + noise);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ret.setExpected(original.getExpected());
		return ret;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSub_id() {
		return sub_id;
	}

	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}

	public ArrayList<Double> getPredicted() {
		return predicted;
	}

	public void setPredicted(ArrayList<Double> predicted) {
		this.predicted = predicted;
	}

	public ArrayList<Double> getPredictedPerc() {
		return predictedPerc;
	}

	public void setPredictedPerc(ArrayList<Double> predictedPerc) {
		this.predictedPerc = predictedPerc;
	}
}
