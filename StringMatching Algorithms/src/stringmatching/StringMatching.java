
package stringmatching;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class StringMatching {

    public static void main(String[] args) throws IOException {
        // Creating Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Step 1: Asking the user for the number of lines to read from input.txt
        System.out.print("How many lines you want to read from the text file? ");
        int numLines = scanner.nextInt();
        scanner.nextLine();

        // Step 2: Asking the user for the length of each pattern
        System.out.print("What is the length of each pattern? ");
        int patternLength = scanner.nextInt();
        scanner.nextLine();

        // Step 3: Asking the user for the number of patterns to be generated
        System.out.print("How many patterns to be generated? ");
        int numPatterns = scanner.nextInt();
        scanner.nextLine();

        // Step 4: Reading input text file and storing text in lower case
        StringBuilder textBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < numLines) {
                textBuilder.append(line.toLowerCase()).append(" ");
                count++;
            }
        }
        String text = textBuilder.toString();

        // Step 5: Generating random patterns and storing them in an array and a file
        String[] patterns = new String[numPatterns];
        Random random = new Random();
        try (FileWriter writer = new FileWriter("patterns.txt")) {
            for (int i = 0; i < numPatterns; i++) {
                int startIndex = random.nextInt(text.length() - patternLength);
                String pattern = text.substring(startIndex, startIndex + patternLength);
                patterns[i] = pattern;
                writer.write(pattern + "\n");
            }
        }

        // Step 6: Creating and displaying shift tables for Horspool's algorithm
        System.out.println("\nShift Tables:");
        for (String pattern : patterns) {
            HashMap<Character, Integer> shiftTable = createShiftTable(pattern, text);
            System.out.println("Pattern: " + pattern + " -> Shift Table: " + shiftTable);
        }

        // Step 7: Brute Force Algorithm - Measure running time
        long bruteForceStartTime = System.nanoTime(); //Start timing
        for (String pattern : patterns) {
            bruteForceStringMatch(text, pattern); }
        long bruteForceEndTime = System.nanoTime();
        long bruteForceAvgTime = (bruteForceEndTime - bruteForceStartTime);

        // Step 8: Horspool Algorithm - Measure running time
        long horspoolStartTime = System.nanoTime(); //Start timing
        for (String pattern : patterns) {
            horspoolStringMatch(text, pattern);
        }
        long horspoolEndTime = System.nanoTime();
        long horspoolAvgTime = (horspoolEndTime - horspoolStartTime);

        // Step 9: Display average running times and summary
        System.out.println("\nAverage Running Time:");
        System.out.println("Average time of search in Brute Force Approach: " + bruteForceAvgTime + " ns");
        System.out.println("Average time of search in Horspool Approach: " + horspoolAvgTime + " ns");
        System.out.println("\nFor this instance, Horspool approach is " + (bruteForceAvgTime > horspoolAvgTime ? "better" : "worse") + " than Brute force approach");
    }

    // Create shift table for Horspool's algorithm
    public static HashMap<Character, Integer> createShiftTable(String pattern, String text) {
        HashMap<Character, Integer> table = new HashMap<>();
        int m = pattern.length();

        // Step 10: Use HashSet to get distinct characters from the text
        HashSet<Character> distinctChars = new HashSet<>();
        for (char c : text.toCharArray()) {
            distinctChars.add(c);
        }

        // Step 11: Initialize all distinct characters with shift value of pattern length
        for (char c : distinctChars) {
            table.put(c, m);
        }

        // Step 12: Update shift values based on the pattern
        for (int i = 0; i < m - 1; i++) {
            table.put(pattern.charAt(i), m - 1 - i);
        }

        return table;
    }

    // Brute Force String Matching Algorithm
    public static int bruteForceStringMatch(String text, String pattern) {
        int n = text.length(); //Get the length of the text
        int m = pattern.length(); //Get the length of the pattern
        for (int i = 0; i <= n - m; i++) { // Loop For every character in n
            int j = 0; //temp to  hold the index while traversing n 
            ////While j is in the text and the characters in the text and pattern are the same
            while(j < m && Character.toLowerCase(text.charAt(i+j)) == Character.toLowerCase(pattern.charAt(j))){
            //while (j < m && pattern.charAt(j) == text.charAt(i + j)){
                j++; // next index 
            }
            if (j == m) { // reached end of the pattern 
                return i; // Match found
            }
        }
        return -1; // No match found
    }

    // Horspool's String Matching Algorithm
    public static int horspoolStringMatch(String text, String pattern) {
        HashMap<Character, Integer> shiftTable = createShiftTable(pattern, text); //Generate the shift table
        int n = text.length(); //Get length of text
        int m = pattern.length(); //Get length of pattern
        int i = m - 1; //the number of characters in the pattern-1

        // Step 13: Implement Horspool search algorithm
        while (i <= n-1) { //While i is less than or equal to the number of characters in the text-1
            int k = 0;
            while (k < m-1 && pattern.charAt(m - 1 - k) == text.charAt(i - k)) {
            //while(k <= m-1 && Character.toLowerCase(pattern[m-1-k]) == Character.toLowerCase(text[i-k])){
                k++; //Increment k
            }
            if (k == m) {
                return i - m + 1; // Match found, Return the index where the pattern was found
            } else {
                char c = text.charAt(i);
                i += shiftTable.getOrDefault(c, m); //Shift by the appropiate amount depending on the character
            }
        }
        return -1; // No match found
    }
}
