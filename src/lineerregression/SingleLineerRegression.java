package lineerregression;

public class SingleLineerRegression {
	 private double slope;
	    private double intercept;

	    public void fit(double[] x, double[] y) {
	        double sumX = 0.0;
	        double sumY = 0.0;
	        double sumXY = 0.0;
	        double sumX2 = 0.0;

	        int n = x.length;
	        for (int i = 0; i < n; i++) {
	            sumX += x[i];
	            sumY += y[i];
	            sumXY += x[i] * y[i];
	            sumX2 += x[i] * x[i];
	        }

	        double xBar = sumX / n;
	        double yBar = sumY / n;

	        double denominator = sumX2 - sumX * xBar;
	        if (denominator == 0) {
	            throw new ArithmeticException("Cannot compute linear regression: denominator is zero");
	        }

	        slope = (sumXY - sumX * yBar) / denominator;
	        intercept = yBar - slope * xBar;
	    }

	    public double predict(double x) {
	        return slope * x + intercept;
	    }

	    public double getSlope() {
	        return slope;
	    }

	    public double getIntercept() {
	        return intercept;
	    }
}
