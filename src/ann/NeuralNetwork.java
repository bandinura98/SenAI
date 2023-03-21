package ann;

import java.util.ArrayList;
import java.util.Arrays;

import anngredients.Activations;

public class NeuralNetwork {
    
    private int numInputs;
    private int numHidden;
    private int numOutputs;
    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;
    private double[] hiddenLayer;
    private double[] outputLayer;
    private String activationFunction;
    private Activations activations = new Activations();
    
    

    
    public NeuralNetwork(int numInputs, int numHidden, int numOutputs, String activationFunction) {
        this.numInputs = numInputs;
        this.numHidden = numHidden;
        this.numOutputs = numOutputs;
        
        this.weightsInputHidden = new double[numInputs][numHidden];
        this.weightsHiddenOutput = new double[numHidden][numOutputs];
        
        this.hiddenLayer = new double[numHidden];
        this.outputLayer = new double[numOutputs];
        
        this.activationFunction = activationFunction;
        
        initializeWeights();
    }
    
    private void initializeWeights() {
        for (int i = 0; i < numInputs; i++) {
            for (int j = 0; j < numHidden; j++) {
                weightsInputHidden[i][j] = Math.random() - 0.5;
            }
        }
        
        for (int i = 0; i < numHidden; i++) {
            for (int j = 0; j < numOutputs; j++) {
                weightsHiddenOutput[i][j] = Math.random() - 0.5;
            }
        }
    }
    
    
    public double[] feedForward(double[] inputs) {
        // Calculate hidden layer values
        for (int i = 0; i < numHidden; i++) {
            double sum = 0;
            for (int j = 0; j < numInputs; j++) {
                sum += inputs[j] * weightsInputHidden[j][i];
            }
            hiddenLayer[i] = activations.selectActivations(sum, activationFunction);// sigmoid(sum);
        }
        
        // Calculate output layer values
        for (int i = 0; i < numOutputs; i++) {
            double sum = 0;
            for (int j = 0; j < numHidden; j++) {
                sum += hiddenLayer[j] * weightsHiddenOutput[j][i];
            }
            outputLayer[i] = activations.selectActivations(sum, activationFunction);//sigmoid(sum);
        }
        
        return outputLayer;
    }
    
    public void trainSGD(double[] inputs, double[] targets, double learningRate) {
        // Feed inputs forward to get outputs
        double[] outputs = feedForward(inputs);
        
        // Calculate output layer error
        double[] outputErrors = new double[numOutputs];
        for (int i = 0; i < numOutputs; i++) {
            double error = targets[i] - outputs[i];
            outputErrors[i] = error * outputs[i] * (1 - outputs[i]);
        }
        
        // Calculate hidden layer error
        double[] hiddenErrors = new double[numHidden];
        for (int i = 0; i < numHidden; i++) {
            double error = 0;
            for (int j = 0; j < numOutputs; j++) {
                error += outputErrors[j] * weightsHiddenOutput[i][j];
            }
            hiddenErrors[i] = error * hiddenLayer[i] * (1 - hiddenLayer[i]);
        }
        
        // Update weights between hidden and output layers
        for (int i = 0; i < numHidden; i++) {
            for (int j = 0; j < numOutputs; j++) {
                weightsHiddenOutput[i][j] += learningRate * outputErrors[j] * hiddenLayer[i];
            }
        }
        // Update weights between input and hidden layers
        for (int i = 0; i < numInputs; i++) {
            for (int j = 0; j < numHidden; j++) {
                weightsInputHidden[i][j] += learningRate * hiddenErrors[j] * inputs[i];
            }
        }
    }
    
    public void trainer(ArrayList<double[]> inputs, ArrayList<double[]> expectedOutputs, NeuralNetwork nn, double learningRate, int epoch){
		if(inputs.size()!=expectedOutputs.size()) throw new IllegalArgumentException("at trainer method size of inputs(" + inputs.size() + ")are not same with the size of expectedOutputs(" + expectedOutputs.size()+")");
		for(int i = 0; i < epoch; i++) {
			for (int j = 0; j < inputs.size(); j++) {
				//System.out.println(inputs.size() + "" + expectedOutputs.size());
				nn.trainSGD(inputs.get(j), expectedOutputs.get(j), learningRate);
			}
		}
		
	}
    
/*
    public void trainSGD(double[] inputs, double[] targets, double learningRate, NeuralNetwork nn) {
	    // Feed inputs forward to get outputs
	    double[] outputs = nn.feedForward(inputs);

	    // Calculate output layer error
	    double[] outputErrors = new double[numOutputs];
	    for (int i = 0; i < numOutputs; i++) {
	        double error = targets[i] - outputs[i];
	        outputErrors[i] = error * outputs[i] * (1 - outputs[i]);
	    }

	    // Calculate hidden layer error
	    double[] hiddenErrors = new double[numHidden];
	    for (int i = 0; i < numHidden; i++) {
	        double error = 0;
	        for (int j = 0; j < numOutputs; j++) {
	            error += outputErrors[j] * weightsHiddenOutput[i][j];
	        }
	        hiddenErrors[i] = error * hiddenLayer[i] * (1 - hiddenLayer[i]);
	    }

	    // Update weights between hidden and output layers
	    for (int i = 0; i < numHidden; i++) {
	        for (int j = 0; j < numOutputs; j++) {
	            weightsHiddenOutput[i][j] += learningRate * outputErrors[j] * hiddenLayer[i];
	        }
	    }
	    // Update weights between input and hidden layers
	    for (int i = 0; i < numInputs; i++) {
	        for (int j = 0; j < numHidden; j++) {
	            weightsInputHidden[i][j] += learningRate * hiddenErrors[j] * inputs[i];
	        }
	    }
	}
    
    
    /*
    public void rmsprop(double[] inputs, double[] targets, double learningRate, NeuralNetwork nn) {
        // Initialize the hyperparameters for RMSprop
        double rho = 0.9; // Decay rate for moving average
        double epsilon = 1e-8; // Small value to avoid division by zero

        // Calculate the gradients of the weights
        double[][][] gradients = nn.backpropagation(inputs, targets);

        // Update the moving averages for the gradients
        double[][][] squaredGradients = new double[2][][];
        for (int i = 0; i < 2; i++) {
            squaredGradients[i] = new double[gradients[i].length][gradients[i][0].length];
            for (int j = 0; j < gradients[i].length; j++) {
                for (int k = 0; k < gradients[i][0].length; k++) {
                    squaredGradients[i][j][k] = rho * squaredGradients[i][j][k] + (1 - rho) * gradients[i][j][k] * gradients[i][j][k];
                }
            }
        }

        // Update the weights using the RMSprop formula
        for (int i = 0; i < nn.numInputs; i++) {
            for (int j = 0; j < nn.numHidden; j++) {
                nn.weightsInputHidden[i][j] -= learningRate * gradients[0][i][j] / (Math.sqrt(squaredGradients[0][i][j]) + epsilon);
            }
        }
        for (int i = 0; i < nn.numHidden; i++) {
            for (int j = 0; j < nn.numOutputs; j++) {
                nn.weightsHiddenOutput[i][j] -= learningRate * gradients[1][i][j] / (Math.sqrt(squaredGradients[1][i][j]) + epsilon);
            }
        }
    }
*/
	public static void main(String[] args) {
		NeuralNetwork nn = new NeuralNetwork(2, 4, 1,"sigmoid");
		
		for (int i = 0; i < 100000; i++) {
			nn.trainSGD(new double[] {0, 0}, new double[] {0}, 0.1);
			nn.trainSGD(new double[] {0, 1}, new double[] {1}, 0.1);
			nn.trainSGD(new double[] {1, 0}, new double[] {1}, 0.1);
			nn.trainSGD(new double[] {1, 1}, new double[] {0}, 0.1);
			}
		System.out.println(Arrays.toString(nn.feedForward(new double[] {0, 0})));
		System.out.println(Arrays.toString(nn.feedForward(new double[] {0, 1}))); 
		System.out.println(Arrays.toString(nn.feedForward(new double[] {1, 0})));
		System.out.println(Arrays.toString(nn.feedForward(new double[] {1, 1}))); 
	}
}