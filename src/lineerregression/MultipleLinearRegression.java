package lineerregression;

import math.Matrix;

public class MultipleLinearRegression {
	private double[] coefficients;

    public void fit(double[][] X, double[] y) {
        int n = X.length;
        int m = X[0].length;

        // create matrices and vectors for linear algebra calculations
        Matrix XMatrix = new Matrix(n, m + 1);
        Matrix YVector = new Matrix(n, 1);
        for (int i = 0; i < n; i++) {
            XMatrix.set(i, 0, 1); // add a column of ones for the intercept term
            for (int j = 0; j < m; j++) {
                XMatrix.set(i, j + 1, X[i][j]);
            }
            YVector.set(i, 0, y[i]);
        }

        // calculate the coefficients using linear algebra
        Matrix XTX = XMatrix.transpose().times(XMatrix);
        Matrix XTY = XMatrix.transpose().times(YVector);
        Matrix beta = XTX.solve(XTY);
        coefficients = new double[m + 1];
        for (int i = 0; i < m + 1; i++) {
            coefficients[i] = beta.get(i, 0);
        }
    }

    public double predict(double[] X) {
        double y = coefficients[0];
        for (int i = 0; i < X.length; i++) {
            y += coefficients[i + 1] * X[i];
        }
        return y;
    }
    public double predict(double[][] X) {
        double y = coefficients[0];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                y += coefficients[i + 1] * X[i][j];
            }
        }
        return y;
    }
}

