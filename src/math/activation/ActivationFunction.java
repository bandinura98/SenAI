package math.activation;

public interface ActivationFunction {
	double calculate(double input);
	double calculateDerivative(double input);
}
