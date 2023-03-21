package clusteringredients;

import java.util.ArrayList;
import java.util.List;
public class Cluster {
    private int id;
    private Point centroid;
    private List<Point> points;
    private boolean merged;
    
    
    public List<double[]> getDataPoints() {
        List<double[]> dataPoints = new ArrayList<>();
        for (Point point : points) {
            dataPoints.add(point.getFeatures());
        }
        return dataPoints;
    }
    
    public Cluster() {
    	this.points = new ArrayList<>();
    }
    
    public Cluster(int id) {
    	this.id = id;
        this.points = new ArrayList<>();
        this.centroid = new Point(new double[] {});
        this.merged = false;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void clearPoints() {
        points.clear();
    }

    public Point calculateCentroid() {
        double[] sum = new double[centroid.getFeatures().length];
        for (Point point : points) {
            double[] features = point.getFeatures();
            for (int i = 0; i < features.length; i++) {
                sum[i] += features[i];
            }
        }
        double[] mean = new double[sum.length];
        for (int i = 0; i < sum.length; i++) {
            mean[i] = sum[i] / points.size();
        }
        return new Point(mean);
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public double distance(Cluster other) {
        // Calculate the distance between two clusters as the distance between their centroids
        double[] x1 = this.centroid.getFeatures();
        double[] x2 = other.centroid.getFeatures();
        double sum = 0.0;
        for (int i = 0; i < x1.length; i++) {
            sum += Math.pow(x1[i] - x2[i], 2);
        }
        return Math.sqrt(sum);
    }
    public Cluster(Cluster c1, Cluster c2) {
        this.points = new ArrayList<>();
        this.centroid = new Point(new double[] {});
        this.merged = false;
        this.id = -1; // Set a unique ID for the new cluster object

        // Add all the points in the two clusters to the new cluster object
        this.points.addAll(c1.getPoints());
        this.points.addAll(c2.getPoints());

        // Recalculate the centroid for the new cluster object
        this.centroid = calculateCentroid();

        // Mark the two old clusters as merged
        c1.setMerged(true);
        c2.setMerged(true);
    }

}