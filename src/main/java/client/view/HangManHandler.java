package client.view;

public class HangManHandler {

    public String newGame(String msg){
        String inputArray[] = msg.split(" ");

        String wordProgress = inputArray[2];
        String remainingGuesses = inputArray[3];
        String wordLength = inputArray[4];
        String totalScore = inputArray[6];

        return
                "\n*********************************************************************************************** \n" +
                        "Im thinking of a word with " + wordLength + " letters. What is the word? Guess a letter or the complete word.  \n" +
                        "*********************************************************************************************** \n\n" +
                        wordProgress + ", GUESSES LEFT: " + remainingGuesses + " TOTAL SCORE: " + totalScore;

    }

    public String guess (String msg){
        String inputArray[] = msg.split(" ");

        String word = inputArray[1];
        String wordProgress = inputArray[2];
        String remainingGuesses = inputArray[3];
        String wordLength = inputArray[4];
        String foundLetters = inputArray[5];
        String totalScore = inputArray[6];

        if(wordLength.equals(foundLetters)){
            msg = word + " IS THE WORD, YOU WIN! TOTAL SCORE: " + totalScore;
        }
        else if(remainingGuesses.equals("0")){
            msg =  "\"" + word + "\"" + " WAS THE WORD, THE MAN GOT HANGED! " + "TOTAL SCORE: " + totalScore;
        }
        else {
            msg = wordProgress + ", GUESSES LEFT: " + remainingGuesses + " TOTAL SCORE: " + totalScore;
        }
        if(remainingGuesses.equals("-1")){
            msg = "use command \"START\" to start new game";
        }

        return msg;
    }
    public String getInfo() {
        return  "*************************************\n" +
                "Welcome to the game of Hangman!    \n" +
                "These are the commands:            \n" +
                "Start new game:        start       \n" +
                "Guess the letter a:    guess a     \n" +
                "Guess the word hello:  guess hello \n" +
                "Quit:                  quit        \n" +
                "Help:                  help        \n" +
                "*************************************";
    }
    public String connected() {
        return  "Connected \n\n" + getInfo();
    }
}
