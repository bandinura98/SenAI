package tokenize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleBasedTokenizations {
	public static String[] whiteSpace(String input) {
	    if (input == null) {
	        return new String[0];
	    }
	    input = input.trim();
	    if (input.isEmpty()) {
	        return new String[0];
	    }
	    return input.split("\\s+");
	}
	
	public static List<String> Punctuation(String input) {
	    List<String> tokens = new ArrayList<String>();
	    String[] words = input.split("\\s+");
	    for (String word : words) {
	        // remove leading/trailing punctuation
	        String cleanWord = word.replaceAll("^\\p{Punct}+|\\p{Punct}+$", "");
	        // split on internal punctuation
	        String[] subWords = cleanWord.split("\\p{Punct}+");
	        tokens.addAll(Arrays.asList(subWords));
	    }
	    return tokens;
	}
	
	public static List<String> Regex(String input, String regex) {
	    List<String> tokens = new ArrayList<String>();
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(input);
	    while (matcher.find()) {
	        tokens.add(matcher.group());
	    }
	    return tokens;
	}
	
	
}
