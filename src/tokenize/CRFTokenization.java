package tokenize;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * 
 * under construction
 * 
 * 
 * 
 * */







public class CRFTokenization {
    public static void main(String[] args) {
        String text = "This is a sample sentence.";
        List<String> tokens = tokenize(text);
        for (String token : tokens) {
            System.out.println(token);
        }
    }
    
    private static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        String[] words = text.split(" ");
        for (String word : words) {
            tokens.addAll(segment(word));
        }
        return tokens;
    }
    
    private static List<String> segment(String word) {
        List<String> segments = new ArrayList<>();
        int length = word.length();
        int i = 0;
        while (i < length) {
            int j = length;
            while (j > i) {
                String segment = word.substring(i, j);
                if (isValid(segment)) {
                    segments.add(segment);
                    break;
                }
                j--;
            }
            i = j;
        }
        return segments;
    }
    
    public static boolean isValid(String segment) {
        // Add your own validation logic here
        return true;
    }
}
