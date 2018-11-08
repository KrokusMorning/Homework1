package server.net;

import server.controller.Controller;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final HangmanServer server;
    private final Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private boolean connected;

    ClientHandler(HangmanServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        connected = true;
    }

    @Override
    public void run() {
        Controller controller = new Controller();
        try {
            boolean autoFlush = true;
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        while (connected) {
            try {
                String input = fromClient.readLine();
                String inputArray[] = input.split(" ");

                if(inputArray[0].equals("START")){
                    String newGameString = controller.newGame();
                    sendMsg(newGameString);
                }
                else if(inputArray[0].equals("GUESS")){
                    if(inputArray.length > 1){
                        String guessResult = controller.guess(inputArray[1]);
                        sendMsg(guessResult);
                    }
                }
                else if(inputArray[0].equals("DISCONNECT")){
                    disconnectClient();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMsg(String msg) {
        toClient.println(msg);
    }

    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
        server.removeHandler(this);
    }
}
