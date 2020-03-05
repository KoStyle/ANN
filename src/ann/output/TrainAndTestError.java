package ls.assignment1.output;

public class TrainAndTestError{
	private int cicle;
	private double test, training;
	public TrainAndTestError(double errorTrain, double errorTest, int cicle) {
		this.training=errorTrain;
		this.test=errorTest;
		this.cicle=cicle;
	}
	/**
	 * @return the cicle
	 */
	public int getCicle() {
		return cicle;
	}
	/**
	 * @param cicle the cicle to set
	 */
	public void setCicle(int cicle) {
		this.cicle = cicle;
	}
	/**
	 * @return the test
	 */
	public double getTest() {
		return test;
	}
	/**
	 * @param test the test to set
	 */
	public void setTest(double test) {
		this.test = test;
	}
	/**
	 * @return the training
	 */
	public double getTraining() {
		return training;
	}
	/**
	 * @param training the training to set
	 */
	public void setTraining(double training) {
		this.training = training;
	}
	
}
