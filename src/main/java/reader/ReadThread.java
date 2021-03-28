package reader;

import client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread implements Runnable {

    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String COLON = ":";
    private final Socket socket;
    private final Client client;

    public ReadThread(Socket socket, Client client) {
        this.client = client;
        this.socket = socket;

    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                String message = reader.readLine();

                System.out.println(message);

                if (client.getUserName() != null) {
                    System.out.print(LEFT_BRACKET + client.getUserName() + RIGHT_BRACKET + COLON);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

