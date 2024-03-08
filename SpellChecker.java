/*
Name: Mingyuan Shao
UNI: ms6592

The class is designed to input a dictionary and check the incorrect 
word and find their suggestions
*/



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpellChecker implements SpellCheckerInterface {

    private HashSet<String> dictionary;

    //this is the method to input a hashset of dictionary
    public SpellChecker(String filename) {
        try{
        this.dictionary = new HashSet<>();
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            for (String word : line.split("\\s+")) {
                // Convert to lowercase and remove punctuation
                word = word.toLowerCase().replaceAll("[^a-z0-9]", "");
                if (!word.isEmpty()) {
                    dictionary.add(word);
                }
            }
        }
        reader.close();
        }catch(IOException e){
            System.err.println("No dictionary founded!");
        }
    }

    //this is the method to generate a incorrect word list
    public List<String> getIncorrectWords(String filename) {
        
        List<String> incorrectWords = new ArrayList<>();
        try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            for (String word : line.split("\\s+")) {
                // Convert to lowercase and remove punctuation
                word = word.toLowerCase().replaceAll("[^a-z0-9]", "");
                if (!word.isEmpty() && !dictionary.contains(word)) {
                    incorrectWords.add(word);
                }
            }
        }
        reader.close();
        }catch(IOException e){
            System.err.println("No tested file founded!");
        }
        return incorrectWords;
    }

    //this is a suggestion method for inputting incorrect words
    public Set<String> getSuggestions(String word) {
        Set<String> suggestions = new HashSet<>();
        // Add one character
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String suggestion = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(suggestion)) {
                    suggestions.add(suggestion);
                }
            }
        }
        // Remove one character
        for (int i = 0; i < word.length(); i++) {
            String suggestion = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(suggestion)) {
                suggestions.add(suggestion);
            }
        }
        // Swap adjacent characters
        for (int i = 0; i < word.length() - 1; i++) {
            String suggestion = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2);
            if (dictionary.contains(suggestion)) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
        
    }

    //main method to test my programme
    public static void main(String[] args) {
        String filename = "words.txt";
        String inputFilename = "test.txt";
            SpellChecker spellChecker = new SpellChecker(filename);
            List<String> incorrectWords = spellChecker.getIncorrectWords(inputFilename);
            for (String word : incorrectWords) {
                System.out.println("Suggestions for \"" + word + "\":");
                Set<String> suggestions = spellChecker.getSuggestions(word);
                System.out.println(suggestions);
            }
    }
}

