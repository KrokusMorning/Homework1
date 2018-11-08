package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class HangmanServer {

    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_QUARTER = 900000;
    private final List<ClientHandler> clients = new ArrayList<>();


    void removeHandler(ClientHandler handler) {
        synchronized (clients) {
            clients.remove(handler);
        }
    }

    public void serve() {
        try {
            int portNo = 8080;
            ServerSocket listeningSocket = new ServerSocket(portNo);
            while (true) {
                Socket clientSocket = listeningSocket.accept();
                startHandler(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    private void startHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_QUARTER);
        ClientHandler handler = new ClientHandler(this, clientSocket);
        synchronized (clients) {
            clients.add(handler);
        }
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }


}
