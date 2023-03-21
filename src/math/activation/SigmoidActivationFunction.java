package math.activation;

public class SigmoidActivationFunction implements ActivationFunction {

    @Override
    public double calculate(double input) {
        return 1.0 / (1.0 + Math.exp(-input));
    }
    
    public double calculateDerivative(double input) {
        double output = calculate(input);
        return output * (1 - output);
    }


}
