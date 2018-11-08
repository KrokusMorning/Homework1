package client.controller;

import client.net.Connection;
import client.net.OutputHandler;
import client.view.HangManHandler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

public class Controller {

    private final Connection connection = new Connection();
    private final HangManHandler hangManHandler = new HangManHandler();

    public void connect(String host, int port, OutputHandler outputHandler) {
        CompletableFuture.runAsync(() -> {
            try {
                connection.connect(host, port, outputHandler);
            } catch (IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }).thenRun(() -> outputHandler.handleMsg(hangManHandler.connected()));
    }

    public void newGame(String command){

        CompletableFuture.runAsync(() -> connection.newGame(command));
    }

    public void guess(String command, String guessLetter) {

        CompletableFuture.runAsync(() -> connection.guess(command, guessLetter));
    }

    public void disconnect() throws IOException {
        connection.disconnect();
    }
}
