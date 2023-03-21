package test;

import java.util.Random;

import lineerregression.MultipleLinearRegression;
import lineerregression.SingleLineerRegression;

public class Main {
	public static void main(String[] args) {
		double x[] = new double[150];
		double y[] = new double[150];
		double z[] = new double[150];
		
		Random random = new Random();
		
		for(int i =0; i < 150; i++) {
			x[i] = random.nextDouble(10.0);
			y[i] = random.nextDouble(20.0);
			z[i] = random.nextDouble(1.0);
		}
		
		//MultipleLinearRegression mreg = new MultipleLinearRegression();
		
		SingleLineerRegression sreg = new SingleLineerRegression();
		
		sreg.fit(x, y);
		System.out.println(sreg.getSlope());
		System.out.println(sreg.getIntercept());
		
		//mreg.fit(null, y);
		
		
	}
}
