package server.controller;

import server.model.HangmanBot;

public class Controller {

    private final HangmanBot hangmanBot = new HangmanBot();

    public String newGame(){

       return hangmanBot.newGame();
    }

    public String guess(String letter) {

        return hangmanBot.guess(letter);
    }

}
