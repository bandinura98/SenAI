package cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clusteringredients.Point;

public class DensityBased {
	 private static final int NOISE = -1;

	    

	    public static List<List<Point>> cluster(List<Point> points, double epsilon, int minPoints) {
	        List<List<Point>> clusters = new ArrayList<>();
	        Map<Point, Integer> pointLabels = new HashMap<>();

	        int clusterLabel = 0;
	        for (Point p : points) {
	            if (pointLabels.containsKey(p)) {
	                continue;
	            }

	            List<Point> neighborPoints = queryRegion(points, p, epsilon);
	            if (neighborPoints.size() < minPoints) {
	                pointLabels.put(p, NOISE);
	                continue;
	            }

	            clusterLabel++;
	            List<Point> newCluster = new ArrayList<>();
	            pointLabels.put(p, clusterLabel);
	            newCluster.add(p);

	            int i = 0;
	            while (i < neighborPoints.size()) {
	                Point q = neighborPoints.get(i);
	                if (!pointLabels.containsKey(q)) {
	                    pointLabels.put(q, clusterLabel);
	                    List<Point> qNeighborPoints = queryRegion(points, q, epsilon);
	                    if (qNeighborPoints.size() >= minPoints) {
	                        neighborPoints.addAll(qNeighborPoints);
	                    }
	                } else if (pointLabels.get(q) == NOISE) {
	                    pointLabels.put(q, clusterLabel);
	                    newCluster.add(q);
	                }
	                i++;
	            }

	            clusters.add(newCluster);
	        }

	        return clusters;
	    }

	    private static List<Point> queryRegion(List<Point> points, Point p, double epsilon) {
	        List<Point> neighborPoints = new ArrayList<>();
	        for (Point q : points) {
	            if (p.distanceTo(q) <= epsilon) {
	                neighborPoints.add(q);
	            }
	        }
	        return neighborPoints;
	    }
}
