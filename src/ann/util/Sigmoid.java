package ann.util;

/**
 * This class contains a type of sigmoid function following this structure
 * 
 * f(x)=(β·1)/((1·γ)+(e^-x·α))
 * 
 * This way, the form of the function can be modified with the alfa, beta and
 * gamma parameters
 * 
 * @author Manu Konomi
 * 
 */
public class Sigmoid {

	private double alfa = 1;
	private double beta = 1;
	private double gamma = 1;
	private boolean activated = true;

	/**
	 * Constructor of the Sigmoid class. If some of the parameters is lesser
	 * than zero, it will take the default value (1.0)
	 * 
	 * @param alfa
	 * @param beta
	 * @param gamma
	 */
	public Sigmoid(double alfa, double beta, double gamma) {
		this.alfa = alfa;
		this.beta = beta;
		this.gamma = gamma;

		if (alfa <= 0) {
			this.alfa = 1;
		}
		if (beta <= 0) {
			this.beta = 1;
		}
		if (gamma <= 0) {
			this.gamma = 1;
		}
	}

	/**
	 * Empty constructor for the class. Assings to the parameters their default
	 * values (1.0)
	 */
	public Sigmoid() {

	}

	/**
	 * @return the alfa
	 */
	public double getAlfa() {
		return alfa;
	}

	/**
	 * @param alfa
	 *            the alfa to set
	 */
	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	/**
	 * @return the beta
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * @param beta
	 *            the beta to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * @return the gamma
	 */
	public double getGamma() {
		return gamma;
	}

	/**
	 * @param gamma
	 *            the gamma to set
	 */
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double sigmoidOperation(double input) {
		if (this.activated) {
			double aux;
			aux = -input * this.alfa;
			aux = Math.pow(Math.E, aux);
			aux = aux + this.gamma;
			aux = this.beta / aux;
			return aux;
		} else {
			return input;
		}
	}

	/**
	 * @return the isFinalNeuron
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param isFinalNeuron
	 *            the isFinalNeuron to set
	 */
	public void setActivation(boolean isFinalNeuron) {
		this.activated = isFinalNeuron;
	}

}
