package client;

import reader.ReadThread;
import registration.RegistrationProcess;
import writer.WriteThread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientWorker implements Client {
    private final String hostname;
    private final int port;
    private volatile String userName;

    public ClientWorker(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
            new RegistrationProcess(socket, this).start();
            startMessaging(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    private void startMessaging(Socket socket) {
        new Thread(new ReadThread(socket, this)).start();
        new Thread(new WriteThread(socket)).start();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }
}
