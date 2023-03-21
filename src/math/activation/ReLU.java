package math.activation;

public class ReLU implements ActivationFunction {
    
    public double calculate(double input) {
        return Math.max(0, input);
    }
    
    public double calculateDerivative(double input) {
        return input > 0 ? 1 : 0;
    }
}
