package math.activation;

public class Tanh implements ActivationFunction {
    
    public double calculate(double input) {
        return Math.tanh(input);
    }
    
    public double calculateDerivative(double input) {
        double output = calculate(input);
        return 1 - output * output;
    }
}
