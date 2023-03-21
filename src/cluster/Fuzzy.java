package cluster;

import java.util.ArrayList;
import java.util.List;

public class Fuzzy {
	    private double[][] data;
	    private int numClusters;
	    private int numAttributes;
	    private double[][] centers;
	    private double[][] membership;

	    public Fuzzy(double[][] data, int numClusters) {
	        this.data = data;
	        this.numClusters = numClusters;
	        this.numAttributes = data[0].length;
	        this.centers = new double[numClusters][numAttributes];
	        this.membership = new double[data.length][numClusters];
	    }

	    public void run(int maxIterations, double fuzziness) {
	        // Initialize membership values randomly
	        for (int i = 0; i < data.length; i++) {
	            double sum = 0.0;
	            for (int j = 0; j < numClusters; j++) {
	                membership[i][j] = Math.random();
	                sum += membership[i][j];
	            }
	            for (int j = 0; j < numClusters; j++) {
	                membership[i][j] /= sum;
	            }
	        }

	        // Update centers and membership values iteratively
	        int iteration = 0;
	        while (iteration < maxIterations) {
	            // Update centers
	            for (int j = 0; j < numClusters; j++) {
	                double[] center = new double[numAttributes];
	                double sum1 = 0.0;
	                double sum2 = 0.0;
	                for (int i = 0; i < data.length; i++) {
	                    double membershipPower = Math.pow(membership[i][j], fuzziness);
	                    for (int k = 0; k < numAttributes; k++) {
	                        center[k] += data[i][k] * membershipPower;
	                    }
	                    sum1 += membershipPower;
	                }
	                for (int k = 0; k < numAttributes; k++) {
	                    center[k] /= sum1;
	                }
	                centers[j] = center;
	            }

	            // Update membership values
	            for (int i = 0; i < data.length; i++) {
	                for (int j = 0; j < numClusters; j++) {
	                    double distanceToCenter = getDistance(data[i], centers[j]);
	                    double sum = 0.0;
	                    for (int k = 0; k < numClusters; k++) {
	                        double distanceToOtherCenter = getDistance(data[i], centers[k]);
	                        sum += Math.pow(distanceToCenter / distanceToOtherCenter, 2.0 / (fuzziness - 1.0));
	                    }
	                    membership[i][j] = 1.0 / sum;
	                }
	            }

	            iteration++;
	        }
	    }

	    private double getDistance(double[] a, double[] b) {
	        double sum = 0.0;
	        for (int i = 0; i < a.length; i++) {
	            sum += Math.pow(a[i] - b[i], 2.0);
	        }
	        return Math.sqrt(sum);
	    }

	    public List<List<Integer>> getClusters() {
	        List<List<Integer>> clusters = new ArrayList<>();
	        for (int j = 0; j < numClusters; j++) {
	            List<Integer> cluster = new ArrayList<>();
	            for (int i = 0; i < data.length; i++) {
	                if (membership[i][j] >= 0.5) {
	                    cluster.add(i);
	                }
	            }
	            clusters.add(cluster);
	        }
	        return clusters;
	    }
	
}
