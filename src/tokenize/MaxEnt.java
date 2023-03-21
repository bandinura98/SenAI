package tokenize;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
 * heap error 
 * 
 * */
public class MaxEnt {
    private static List<String> tokenList;
    private static List<String> featureList;
    private static List<List<String>> instanceList;
    private static List<Double> weightList;
    private static double[] weights;
    private static final double SIGMA = 0.1;
    private static final int ITERATIONS = 2;
    private static final int NUM_FEATURES = 10;
    private static final double LEARNING_RATE = 0.1;
    private static final String TRAINING_DATA_FILE = "training_data.txt";
    
    public static void main(String[] args) {
        String text = "This is a sample sentence.";
        loadTrainingData(TRAINING_DATA_FILE);
        train();
        List<String> tokens = tokenize(text);
        for (String token : tokens) {
            System.out.println(token);
        }
    }
    
    public static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        int length = text.length();
        int i = 0;
        while (i < length) {
            String token = findToken(text, i);
            if (token != null) {
                tokens.add(token);
                i += token.length();
            } else {
                i++;
            }
        }
        return tokens;
    }
    
    public static String findToken(String text, int index) {
        int length = text.length();
        for (int i = index; i < length; i++) {
            String context = getContext(text, i);
            if (isTokenBoundary(context)) {
                if (tokenList.contains(context)) {
                    return context;
                } else {
                    List<String> features = getFeatures(context);
                    double score = 0;
                    for (String feature : features) {
                        int featureIndex = featureList.indexOf(feature);
                        if (featureIndex != -1) {
                            score += weights[featureIndex];
                        }
                    }
                    if (score >= 0) {
                        return context;
                    }
                }
            }
        }
        return null;
    }
    
    public static String getContext(String text, int index) {
        StringBuilder context = new StringBuilder();
        int length = text.length();
        for (int i = index; i < length; i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                context.append(c);
            } else {
                break;
            }
        }
        return context.toString();
    }
    
    public static boolean isTokenBoundary(String context) {
        // Add your own token boundary rules here
        return true;
    }
    
    public static void loadTrainingData(String filename) {
        tokenList = new ArrayList<>();
        featureList = new ArrayList<>();
        instanceList = new ArrayList<>();
        weightList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String token = parts[0];
                List<String> features = new ArrayList<>();
                for (int i = 1; i < parts.length - 1; i++) {
                    String feature = parts[i];
                    if (!featureList.contains(feature)) {
                        featureList.add(feature);
                    }
                    features.add(feature);
                }
                double weight = Double.parseDouble(parts[parts.length - 1]);
                System.out.println(weight);
                tokenList.add(token);
                instanceList.add(features);
                weightList.add(weight);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
        
        weights = new double[featureList.size()];
    }
    public static void train() {
        // Initialize the weights to 0
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 0.0;
        }

        // Train the model for the specified number of iterations
        for (int iter = 0; iter < ITERATIONS; iter++) {
        	
            for (int i = 0; i < instanceList.size(); i++) {
                List<String> features = instanceList.get(i);
                double score = 0;
                for (String feature : features) {
                    int featureIndex = featureList.indexOf(feature);
                    if (featureIndex != -1) {
                        score += weights[featureIndex];
                    }
                }
                double label = weightList.get(i);
                double prob = sigmoid(score);
                for (String feature : features) {
                    int featureIndex = featureList.indexOf(feature);
                    if (featureIndex != -1) {
                        double gradient = (label - prob) * getIndicator(features, feature);
                        weights[featureIndex] += LEARNING_RATE * gradient;
                    }
                }
            }
        }
    }

    public static List<String> getFeatures(String token) {
        List<String> features = new ArrayList<>();
        for (int i = 0; i < token.length(); i++) {
            String prefix = token.substring(0, i + 1);
            if (featureList.contains(prefix)) {
                features.add("PREFIX=" + prefix);System.out.println(2);
            }
        }
        for (int i = 0; i < token.length(); i++) {
            String suffix = token.substring(i);
            if (featureList.contains(suffix)) {
                features.add("SUFFIX=" + suffix);System.out.println(1);
            }
        }
        return features;
    }

    private static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private static int getIndicator(List<String> features, String feature) {
        return features.contains(feature) ? 1 : 0;
    }

}
