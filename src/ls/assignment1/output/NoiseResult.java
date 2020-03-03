package ls.assignment1.output;

public class NoiseResult {
	private double result;
	private double noiseLevel;
	public NoiseResult(double result, double noiseLevel) {
		super();
		this.result = result;
		this.noiseLevel = noiseLevel;
	}
	public NoiseResult() {
		super();
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
	/**
	 * @return the noiseLevel
	 */
	public double getNoiseLevel() {
		return noiseLevel;
	}
	/**
	 * @param noiseLevel the noiseLevel to set
	 */
	public void setNoiseLevel(double noiseLevel) {
		this.noiseLevel = noiseLevel;
	}
	
	
}
