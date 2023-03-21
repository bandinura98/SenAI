package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cluster.*;
import clusteringredients.Cluster;
import clusteringredients.Point;



/*
This program generates 100 random points in 10x10 space and runs clustering algorithm with k=3.
Then prints the resul of clusters and their centroids.
 * */




public class KMeansClusteringTest {
	
	public static void testHierarchic() {
	    double[][] data = new double[][] {
	        {1.0, 2.0},
	        {2.0, 3.0},
	        {3.0, 4.0},
	        {4.0, 5.0},
	        {5.0, 6.0},
	        {6.0, 7.0},
	        {7.0, 8.0},
	        {8.0, 9.0},
	        {9.0, 10.0},
	        {10.0, 11.0},
	    };

	    // Create HierarchicalClustering object
	    Hierarchical clustering = new Hierarchical(data);

	    // Run clustering algorithm
	    List<Cluster> clusters = clustering.clusterLister(2);

	    // Print debug info
	    System.out.println("Num clusters: " + clusters.size());
	    for (Cluster cluster : clusters) {
	        System.out.println("Cluster:");
	        for (int j = 0; j < cluster.getDataPoints().size(); j++) {
	            System.out.println(Arrays.toString(cluster.getDataPoints().get(j)));
	        }
	    }
	}
	
	public static void testFuzzyCMeans() {
        // Create test data
        double[][] data = new double[][] {
            {1.0, 2.0},
            {2.0, 3.0},
            {3.0, 4.0},
            {4.0, 5.0},
            {5.0, 6.0},
            {6.0, 7.0},
            {7.0, 8.0},
            {8.0, 9.0},
            {9.0, 10.0},
            {10.0, 11.0},
        };
        
        // Create Fuzzy C-Means object and run clustering
        Fuzzy fcm = new Fuzzy(data, 2);
        fcm.run(10, 2.0);
        
        // Get cluster assignments and check the number of clusters
        List<List<Integer>> clusters = fcm.getClusters();
        if (clusters.size() != 2) {
            throw new AssertionError("Expected 2 clusters, but got " + clusters.size());
        }
        
        // Check that all data points are assigned to exactly one cluster
        List<Integer> allDataPoints = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            allDataPoints.add(i);
        }
        List<Integer> assignedDataPoints = new ArrayList<>();
        for (List<Integer> cluster : clusters) {
            assignedDataPoints.addAll(cluster);
        }
        Collections.sort(allDataPoints);
        Collections.sort(assignedDataPoints);
        if (!allDataPoints.equals(assignedDataPoints)) {
            throw new AssertionError("Not all data points are assigned to exactly one cluster");
        }
        List<List<Integer>> clusters2 = fcm.getClusters();
        for (int i = 0; i < clusters.size(); i++) {
            System.out.print("Cluster " + (i+1) + ": ");
            for (int j = 0; j < clusters.get(i).size(); j++) {
                System.out.print(clusters.get(i).get(j) + " ");
            }
            System.out.println();
        }

        
    }
	
	public static void densityBasedTest() {
		 List<Point> points = new ArrayList<>();
	        points.add(new Point(0, 0));
	        points.add(new Point(1, 1));
	        points.add(new Point(2, 2));
	        points.add(new Point(3, 3));
	        points.add(new Point(5, 5));
	        points.add(new Point(6, 6));
	        points.add(new Point(7, 7));
	        points.add(new Point(8, 8));

	        List<List<Point>> clusters = DensityBased.cluster(points, 1.5, 2);
	        for (List<Point> cluster : clusters) {
	            System.out.println("Cluster:");
	            for (Point p : cluster) {
	            	double arr[] = p.getFeatures();
	                System.out.println(arr[0] + ", " + arr[1]);
	            }
	        }
	    
		}
	public static void KMeansTest() {
		// Generate some random points
		List<Point> points = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
			double x = Math.random() * 10;
			double y = Math.random() * 10;
			double[] features = new double[] { x, y };
			Point point = new Point(features);
			points.add(point);
		}
		
		    // Run the clustering algorithm
		    int k = 3;
		    
		    KMeans kMeans = new KMeans(points, k);
		    
		    List<Cluster> clusters = kMeans.cluster();
		
		    // Print the results
		    for (int i = 0; i < k; i++) {
		    	
		        System.out.println("Cluster " + i + ":");
		        Cluster cluster = clusters.get(i);
		        Point centroid = cluster.getCentroid();
		        System.out.println("Centroid: (" + centroid.getFeatures()[0] + ", " + centroid.getFeatures()[1] + ")");
		        System.out.println("Points:");
		        for (Point point : cluster.getPoints()) {
		        	
		            System.out.println("(" + point.getFeatures()[0] + ", " + point.getFeatures()[1] + ")");
		        }
		        System.out.println();
		    }
	}
	
	public static void main(String[] args) {
		//densityBasedTest();
		//KMeansTest();
		//testFuzzyCMeans();
		//testHierarchic(); this does not work
	}
}
