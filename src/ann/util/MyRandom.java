package ann.util;

import java.util.Random;

@SuppressWarnings("serial")
public class MyRandom extends Random{

	@Override
	public double nextDouble() {
		double aux= super.nextDouble();
		int a=this.nextInt(2);
		if(a==0){
			return -aux;
		}else{
			return aux;
		}
	}
	
	public double nextDouble01(){
		return super.nextDouble();
	}
	
	@Override
	public synchronized double nextGaussian() {
		return super.nextGaussian()*0.006;
	}
}
