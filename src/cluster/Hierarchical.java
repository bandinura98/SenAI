package cluster;

import java.util.ArrayList;
import java.util.List;

import clusteringredients.Cluster;

public class Hierarchical {
	
	/*
	 * kaç cluster olduğunu çıkarıyor ama içini doldurmuyor
	 * */
	
    private double[][] data;
    private List<Cluster> clusters;
    
    
    
    public Hierarchical(double[][] data) {
        this.data = data;
        this.clusters = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            clusters.add(new Cluster(i));
        }
    }
    
    
    public List<List<Integer>> cluster(int k) {
        while (clusters.size() > k) {
            // Find the two closest clusters
            double minDistance = Double.MAX_VALUE;
            Cluster c1 = null;
            Cluster c2 = null;
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    Cluster ci = clusters.get(i);
                    Cluster cj = clusters.get(j);
                    if (!ci.isMerged() && !cj.isMerged()) {
                        double distance = ci.distance(cj);
                        if (distance < minDistance) {
                            minDistance = distance;
                            c1 = ci;
                            c2 = cj;
                        }
                    }
                }
            }
            
            // Merge the closest clusters
            Cluster mergedCluster = new Cluster(c1, c2);
            clusters.remove(c1);
            clusters.remove(c2);
            clusters.add(mergedCluster);
        }
        
        // Build the cluster assignments for each point
        List<List<Integer>> assignments = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            assignments.add(new ArrayList<>());
        }
        for (int i = 0; i < data.length; i++) {
            Cluster closestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                
            	
            	double distance = distance(data[i], cluster.getCentroid().getFeatures());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = cluster;
                }
            }
            assignments.get(clusters.indexOf(closestCluster)).add(i);
        }
        
        return assignments;
    }
    public List<Cluster> clusterLister(int k) {
        while (clusters.size() > k) {
            // Find the two closest clusters
            double minDistance = Double.MAX_VALUE;
            Cluster c1 = null;
            Cluster c2 = null;
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    Cluster ci = clusters.get(i);
                    Cluster cj = clusters.get(j);
                    if (!ci.isMerged() && !cj.isMerged()) {
                        double distance = ci.distance(cj);
                        if (distance < minDistance) {
                            minDistance = distance;
                            c1 = ci;
                            c2 = cj;
                        }
                    }
                }
            }

            // Merge the closest clusters
            Cluster mergedCluster = new Cluster(c1, c2);
            clusters.remove(c1);
            clusters.remove(c2);
            clusters.add(mergedCluster);
        }

        // Build the list of Cluster objects
        List<Cluster> result = new ArrayList<>();
        for (Cluster cluster : clusters) {
            if (!cluster.isMerged()) {
                result.add(cluster);
            }
        }
        return result;
    }
    private double distance(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2.0);
        }
        return Math.sqrt(sum);
    }
}