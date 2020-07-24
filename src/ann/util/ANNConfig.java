package ann.util;

public class ANNConfig {

	private int nParam, nHidLay, neuronsHidLay, nOutput;
	double mFactor, mCte, crossover, generr;
	int generations, pairs;
	double lFactor;
	boolean agon = false, bpon=false, appon=false;
	
	public int getParam() {
		return this.nParam;
	}

	public int getHidLay() {
		return this.nHidLay;
	}

	public int getNeuronsHidLay() {
		return this.neuronsHidLay;
	}

	public int getNoutput() {
		return this.nOutput;
	}

	public double getLFactor() {
		return this.lFactor;
	}

	public double getMFactor() {
		return this.mFactor;
	}

	/**
	 * @param nParam the nParam to set
	 */
	public void setnParam(int nParam) {
		this.nParam = nParam;
	}

	/**
	 * @param nHidLay the nHidLay to set
	 */
	public void setnHidLay(int nHidLay) {
		this.nHidLay = nHidLay;
	}

	/**
	 * @param neuronsHidLay the neuronsHidLay to set
	 */
	public void setNeuronsHidLay(int neuronsHidLay) {
		this.neuronsHidLay = neuronsHidLay;
	}

	/**
	 * @param nOutput the nOutput to set
	 */
	public void setnOutput(int nOutput) {
		this.nOutput = nOutput;
	}

	/**
	 * @param d the lFactor to set
	 */
	public void setlFactor(double d) {
		this.lFactor = d;
	}

	/**
	 * @param d the mFactor to set
	 */
	public void setmFactor(double d) {
		this.mFactor = d;
	}

}
