package ann.util;

public class ANNConfig {

	private int nParam, nHidLay, neuronsHidLay, nOutput;
	double mFactor, mutCte, crossover, generr, mutFactor;
	int generations, pairs;
	double lFactor;
	String selectCasesStatement;
	boolean agon = false, bpon = false, appon = false;

	public double getCrossover() {
		return crossover;
	}

	public int getGenerations() {
		return generations;
	}

	public double getGenerr() {
		return generr;
	}

	public int getHidLay() {
		return this.nHidLay;
	}

	public double getlFactor() {
		return lFactor;
	}

	public double getLFactor() {
		return this.lFactor;
	}

	public double getmCte() {
		return mutCte;
	}

	public double getmFactor() {
		return mFactor;
	}

	public double getMFactor() {
		return this.mFactor;
	}

	public int getNeuronsHidLay() {
		return this.neuronsHidLay;
	}

	public int getnHidLay() {
		return nHidLay;
	}

	public int getnOutput() {
		return nOutput;
	}

	public int getNoutput() {
		return this.nOutput;
	}

	public int getnParam() {
		return nParam;
	}

	public int getPairs() {
		return pairs;
	}

	public int getParam() {
		return this.nParam;
	}

	public String getSelectCasesStatement() {
		return selectCasesStatement;
	}

	public boolean isAgon() {
		return agon;
	}

	public boolean isAppon() {
		return appon;
	}

	public boolean isBpon() {
		return bpon;
	}

	public void setAgon(boolean agon) {
		this.agon = agon;
	}

	public void setAppon(boolean appon) {
		this.appon = appon;
	}

	public void setBpon(boolean bpon) {
		this.bpon = bpon;
	}

	public void setCrossover(double crossover) {
		this.crossover = crossover;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public void setGenerr(double generr) {
		this.generr = generr;
	}

	/**
	 * @param d
	 *              the lFactor to set
	 */
	public void setlFactor(double d) {
		this.lFactor = d;
	}

	public void setmCte(double mCte) {
		this.mutCte = mCte;
	}

	/**
	 * @param d
	 *              the mFactor to set
	 */
	public void setmFactor(double d) {
		this.mFactor = d;
	}

	/**
	 * @param neuronsHidLay
	 *                          the neuronsHidLay to set
	 */
	public void setNeuronsHidLay(int neuronsHidLay) {
		this.neuronsHidLay = neuronsHidLay;
	}

	/**
	 * @param nHidLay
	 *                    the nHidLay to set
	 */
	public void setnHidLay(int nHidLay) {
		this.nHidLay = nHidLay;
	}

	/**
	 * @param nOutput
	 *                    the nOutput to set
	 */
	public void setnOutput(int nOutput) {
		this.nOutput = nOutput;
	}

	/**
	 * @param nParam
	 *                   the nParam to set
	 */
	public void setnParam(int nParam) {
		this.nParam = nParam;
	}

	public void setPairs(int pairs) {
		this.pairs = pairs;
	}

	public void setSelectCasesStatement(String selectCasesStatement) {
		this.selectCasesStatement = selectCasesStatement;
	}

	public double getMutFactor() {
		return mutFactor;
	}

	public void setMutFactor(double mutFactor) {
		this.mutFactor = mutFactor;
	}

}
