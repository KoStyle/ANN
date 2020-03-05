package ls.assignment1.util;


import org.junit.Test;

public class MyRandomTest {

	@Test
	public void test() {
		MyRandom r= new MyRandom();
		for(int i=0; i<100; i++){
			System.out.println(r.nextGaussian()*0.1);
		}
	}

}
