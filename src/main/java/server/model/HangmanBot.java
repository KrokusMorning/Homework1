package server.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class HangmanBot {
    List<String> words = new ArrayList<>();
    private String[] word;
    private String[] wordProgress;
    private int guesses;
    private int remainingGuesses;
    private int foundLetters;
    private int totalScore;


    public HangmanBot() {
        readWords();
    }

    public String newGame(){
        pickRandomWord();
        guesses = word.length;
        remainingGuesses = guesses;
        foundLetters = 0;

        return "START" + " " + arrayToString(word).toUpperCase() + " " + arrayToString(wordProgress).toUpperCase() + " " + remainingGuesses + " " + word.length + " " + foundLetters + " " + totalScore;
    }

    private void readWords(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/google-10000-english.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNext()){
            words.add(scanner.nextLine());
        }
    }

    private void pickRandomWord(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1000);
        word = words.get(randomNum).split("");

        if(word.length < 2){
            pickRandomWord();
        }

        wordProgress = new String[word.length];
        for(int i = 0; i < word.length; i++){
            wordProgress[i] = "_";
        }

    }

    public String guess(String guess){
        boolean foundNoLetter = true;
        if(word == null){
         return "INVALID NOSTART";
        }
        if(guess.equals("0")){
            return "INVALID NOGUESS";
        }
        if(remainingGuesses > 0 ) {
            if (guess.length() > 1) {
                if (guess.equalsIgnoreCase(arrayToString(word))) {
                    foundLetters = word.length;
                    wordProgress = word;
                    foundNoLetter = false;
                    remainingGuesses = 0;
                    //return arrayToString(word);
                }
            }
            for (int i = 0; i < word.length; i++) {
                if (word[i].equalsIgnoreCase(guess) && wordProgress[i].equalsIgnoreCase("_")) {
                    wordProgress[i] = guess;
                    foundNoLetter = false;
                    foundLetters += 1;
                }
            }
            if (foundNoLetter) {
                remainingGuesses -= 1;

                if (remainingGuesses == 0) {
                    totalScore -= 1;
                }
            }
            if (foundLetters == word.length) {
                totalScore += 1;
                remainingGuesses = 0;
            }

        }
        else
        {
            remainingGuesses = -1;
        }

        return "GUESS" + " " + arrayToString(word).toUpperCase() + " " + arrayToString(wordProgress).toUpperCase() + " " + remainingGuesses + " " + word.length + " " + foundLetters + " " + totalScore;
    }

    private String arrayToString(String[] stringArray){
        StringBuilder sb = new StringBuilder();
        if (stringArray == null){
            return "";
        }
        for (String item : stringArray) {
            sb = sb.append(item);
        }

        return sb.toString();
    }

}
