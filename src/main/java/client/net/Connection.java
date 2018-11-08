package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {
    private static final int TIMEOUT_QUARTER = 900000;
    private static final int TIMEOUT_20S = 20000;
    private Socket socket;
    private PrintWriter writeToServer;
    private BufferedReader readFromServer;

    public void connect(String host, int port, OutputHandler outputHandler) throws
            IOException {
        if(host.equalsIgnoreCase("0")){
            host = "127.0.0.1";
        }
        if(port == 0){
            port = 8080;
        }
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT_20S);
        socket.setSoTimeout(TIMEOUT_QUARTER);
        boolean autoFlush = true;
        writeToServer = new PrintWriter(socket.getOutputStream(), autoFlush);
        readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new Listener(outputHandler)).start();
        sendMessage("CONNECT");
    }

    public void disconnect() throws IOException {
        sendMessage("DISCONNECT");
        socket.close();
        socket = null;
    }

    public void guess(String command, String guess){
        sendMessage(command, guess);
    }

    public void newGame(String command) {sendMessage(command); }

    private void sendMessage(String... message) {
        StringBuilder sb = new StringBuilder();
        for (String aMessage : message) {
            sb.append(aMessage + " ");
        }
        if(writeToServer != null){
            writeToServer.println(sb.toString());
        }

    }

    private class Listener implements Runnable {
        private final OutputHandler outputHandler;

        private Listener(OutputHandler outputHandler) {
            this.outputHandler = outputHandler;
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    outputHandler.handleMsg(readFromServer.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
