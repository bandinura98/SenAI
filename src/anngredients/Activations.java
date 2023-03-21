package anngredients;

public class Activations {
	
	public double selectActivations(double x,String str) {
		switch (str) {
		case "sigmoid": {
			return sigmoid(x);
		}
		case "tanh": {
			return sigmoid(x);
		}
		case "relu": {
			return sigmoid(x);
		}
		
		
		default:
			throw new IllegalArgumentException("Unexpected value at activation function: " + str);
		}
	}
	
	private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
	private static double tanh(double x) {
	    return Math.tanh(x);
	}
	private static double relu(double x) {
	    if (x > 0) {
	        return x;
	    } else {
	        return 0;
	    }
	}
	
	
	private static double[] softmax(double[] x) {
	    double sum = 0;
	    double[] result = new double[x.length];

	    for (int i = 0; i < x.length; i++) {
	        result[i] = Math.exp(x[i]);
	        sum += result[i];
	    }

	    for (int i = 0; i < result.length; i++) {
	        result[i] /= sum;
	    }

	    return result;
	}

	//alpha determines the slope
	private static double elu(double x, double alpha) {
	    if (x >= 0) {
	        return x;
	    } else {
	        return alpha * (Math.exp(x) - 1);
	    }
	}
	private static double leakyRelu(double x, double alpha) {
	    if (x > 0) {
	        return x;
	    } else {
	        return alpha * x;
	    }
	}


}
