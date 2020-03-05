package ann.util;

import org.junit.Test;

public class SigmoidTest {

	@Test
	public void testSigmoidOperation() {
		Sigmoid s= new Sigmoid(2, 4, 0);
		System.out.println(Double.toString(s.sigmoidOperation(0.25)));
	}

}
