package clusteringredients;

public class Point {
	
	private double[] features;

    public Point(double[] features) {
        this.features = features;
    }

    public Point(double x, double y) {
        this.features = new double[] { x, y };
    }

    public double[] getFeatures() {
        return features;
    }

    public double distanceTo(Point other) {
        double sum = 0.0;
        for (int i = 0; i < features.length; i++) {
            sum += Math.pow(features[i] - other.features[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static Point getRandomPoint() {
        double[] features = new double[] { Math.random(), Math.random() };
        return new Point(features);
    }
	
	
}

