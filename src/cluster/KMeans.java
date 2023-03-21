package cluster;

import java.util.ArrayList;
import java.util.List;

import clusteringredients.Cluster;
import clusteringredients.Point;

public class KMeans {
	private List<Point> points;
    private int k;
    private List<Cluster> clusters;

    public KMeans(List<Point> points, int k) {
        this.points = points;
        this.k = k;
        this.clusters = new ArrayList<>(k);
    }

    public List<Cluster> cluster() {
        // Initialize k clusters with random centroids
        for (int i = 0; i < k; i++) {
            Cluster cluster = new Cluster();
            cluster.setCentroid(Point.getRandomPoint());
            clusters.add(cluster);
        }

        // Iterate until convergence or max iterations is reached
        int maxIterations = 100;
        int iteration = 0;
        while (iteration < maxIterations) {
            // Assign each point to the closest cluster
            for (Point point : points) {
                Cluster closestCluster = null;
                double minDistance = Double.MAX_VALUE;
                for (Cluster cluster : clusters) {
                    double distance = point.distanceTo(cluster.getCentroid());
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestCluster = cluster;
                    }
                }
                closestCluster.addPoint(point);
            }

            // Recalculate the centroid of each cluster
            boolean converged = true;
            for (Cluster cluster : clusters) {
                if (cluster.getPoints().isEmpty()) {
                    continue;
                }
                Point oldCentroid = cluster.getCentroid();
                Point newCentroid = cluster.calculateCentroid();
                if (!newCentroid.equals(oldCentroid)) {
                    converged = false;
                    cluster.setCentroid(newCentroid);
                    cluster.clearPoints();
                }
            }
            if (converged) {
                break;
            }
            iteration++;
        }

        return clusters;
    }
}
