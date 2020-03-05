package ann.util;

import java.util.ArrayList;


public class Individual implements Comparable<Individual>{
	private ArrayList<Double> weights;
	private double fit;
	
	public Individual(ArrayList<Double> weights) {
		super();
		this.weights = weights;
	}
	/**
	 * @return the weights
	 */
	public ArrayList<Double> getWeights() {
		return weights;
	}
	/**
	 * @param weights the weights to set
	 */
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}
	/**
	 * @return the fit
	 */
	public double getFit() {
		return fit;
	}
	/**
	 * @param fit the fit to set
	 */
	public void setFit(double fit) {
		this.fit = fit;
	}
	// a= x*a + (1-x)b
	public void cross(Individual a){
		MyRandom rd= new MyRandom();
		double z, b;
		
		z=rd.nextDouble01();
		b=rd.nextDouble01();
		
		for(int i=0; i<this.weights.size(); i++){
			Double tmp= this.weights.get(i);
			double aux=(tmp*z) + (a.weights.get(i)*(1-z));
			double aux2=(tmp*b) + (a.weights.get(i)*(1-b));
			a.weights.set(i,aux2);
			this.weights.set(i, aux);
		}
		
	}
	@Override
	public int compareTo(Individual arg0) {
		double re = this.getFit() -arg0.getFit();
		if (re < 0) {
			return 1;
		} else if (re == 0) {
			return 0;
		} else {
			return -1;
		}
	}
}
