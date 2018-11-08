package server.startup;

import server.net.HangmanServer;

public class ServerMain {

    public static void main(String[] args) {
        HangmanServer server = new HangmanServer();
        server.serve();
    }
}
