package test;

import java.util.Random;

import lineerregression.MultiLineer;
import lineerregression.MultipleLinearRegression;
import math.Matrix;

public class MultiLineerTest {
	public static void main(String[] args) {
		/*double[][] X = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        double[] y = {3, 7, 11, 15};
        MultipleLinearRegression model = new MultipleLinearRegression();
        model.fit(X, y);
        double[][] X_test = {{9}, {10}};
        double y_pred = model.predict(X_test);

        System.out.println(y_pred);
        
        //assertEquals(19.0, y_pred, 0.001);
        */
		Random rand = new Random();
		/*
		 * 
		 * TODO az aşağdaki double array arraylarının matrixin içine yerleştirilmesi matrixin özelliği olmalı
		 * 
		 * 
		 * 
		 * */
		// Create example data
		double[][] Xdata = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		double[][] ydata = {{5}, {10}, {15}};
		Matrix X = new Matrix(3,3);
		Matrix y = new Matrix(3,1);
		
		X.set(0, 0, rand.nextDouble(0.0,5.0));
		X.set(1, 0, rand.nextDouble(0.0,5.0));
		X.set(2, 0, rand.nextDouble(0.0,5.0));

		X.set(0, 1, rand.nextDouble(0.0,5.0));
		X.set(1, 1, rand.nextDouble(0.0,5.0));
		X.set(2, 1, rand.nextDouble(0.0,5.0));
		
		X.set(0, 2, rand.nextDouble(0.0,5.0));
		X.set(1, 2, rand.nextDouble(0.0,5.0));
		X.set(2, 2, rand.nextDouble(0.0,5.0));
		
		y.set(0, 0, rand.nextDouble(10.0,50.0));
		y.set(1, 0, rand.nextDouble(10.0,50.0));
		y.set(2, 0, rand.nextDouble(10.0,50.0));

		// Fit the linear regression model
		MultiLineer model = new MultiLineer(X, y);
		Matrix coefficients = model.fit();

		// Print the coefficients
		for (int i = 0; i < coefficients.getRows(); i++) {
		    System.out.println("Coefficient " + i + ": " + coefficients.get(i, 0));
		}
        

	}
}
