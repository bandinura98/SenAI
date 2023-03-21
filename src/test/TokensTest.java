package test;

import java.util.List;

import tokenize.RuleBasedTokenizations;

public class TokensTest {
	
	
	public static void main(String[] args) {
		RuleBasedTokenizations tokenize = new RuleBasedTokenizations();
		
		System.out.println("test for Punctuation\n\n");
		
	    String input = "Hello, world! This is a test.";
	    List<String> tokens = tokenize.Punctuation(input);
	    for (String token : tokens) {
	        System.out.println(token);
	    }

		System.out.println("\n\n\ntest for whiteSpace\n\n");
		
	    String input2 = "  hello   world  ";
	    String[] tokens2 = tokenize.whiteSpace(input2);
	    for (String token : tokens2) {
	        System.out.println(token);
	    }
	    
	    System.out.println("\n\n\n\ntest for whiteSpace\n\n");
	    
	    String input3 = "This is a sample input string.";
	    String regex = "[a-zA-Z]+";
	    List<String> tokens3 = tokenize.Regex(input3, regex);
	    System.out.println(tokens);
	    
	    
	}
}
