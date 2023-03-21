package ann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TestANN {
	
	
	
	
	
	public static void main(String[] args) {
		NeuralNetwork nn = new NeuralNetwork(2, 4, 1,"sigmoid");
		
		ArrayList<double[]> inputs = new ArrayList<>();
		inputs.add(new double[] {0,0});
		inputs.add(new double[] {0,1});
		inputs.add(new double[] {1,0});
		inputs.add(new double[] {1,1});
		
		ArrayList<double[]> expextedOutputs = new ArrayList<>();
		expextedOutputs.add(new double[] {0});
		expextedOutputs.add(new double[] {1});
		expextedOutputs.add(new double[] {1});
		expextedOutputs.add(new double[] {0});
		
		
		//nn.trainer(inputs, expextedOutputs, nn, 0.1, 100000);
		/*for (int i = 0; i < 100000; i++) {
			nn.trainSGD(new double[] {0,0}, new double[] {0}, 0.1, nn);
			nn.trainSGD(new double[] {0,1}, new double[] {1}, 0.1, nn);
			nn.trainSGD(new double[] {1,0}, new double[] {1}, 0.1, nn);
			nn.trainSGD(new double[] {1,1}, new double[] {0}, 0.1, nn);
		}*/
		
		
		
		System.out.println(Arrays.toString(nn.feedForward(new double[] {0, 0})));
		System.out.println(Arrays.toString(nn.feedForward(new double[] {0, 1}))); 
		System.out.println(Arrays.toString(nn.feedForward(new double[] {1, 0})));
		System.out.println(Arrays.toString(nn.feedForward(new double[] {1, 1}))); 
	}
}
