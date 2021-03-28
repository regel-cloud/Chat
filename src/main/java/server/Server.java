package server;


import connectionhandler.ConnectionWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private final int port;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Chat server.Server is listening on port " + port);

            ConnectionWorker handler = new ConnectionWorker();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Client accepted" + clientSocket.getInetAddress());
                handler.processConnection(clientSocket);
            }
        } catch (IOException ex) {
            LOGGER.info("Error in the server: " + ex.getMessage());
        }
    }

}

