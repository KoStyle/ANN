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
	private ArrayList<Double> expected;
	private ArrayList<Double> data;
	private int nData;
	private int nResults;

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
}
