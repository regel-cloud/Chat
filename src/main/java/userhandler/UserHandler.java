package userhandler;


import connectionhandler.ConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserHandler implements Runnable {
    private static final String REGISTRATION_OK_RESPONSE = "Welcome";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String BYE_MESSAGE = "##exit";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String COLON = ":";
    private static final String HAS_QUITED = " has quited.";
    private static final String NEW_USER_CONNECTED = "New user connected: ";
    private static final String NO_OTHER_USERS_CONNECTED = "No other users connected";
    private static final String CONNECTED_USERS = "Connected users: ";
    private final Socket socket;
    private final ConnectionHandler connectionHandler;


    private PrintWriter writer;

    public UserHandler(Socket socket, ConnectionHandler handler) {
        this.socket = socket;
        this.connectionHandler = handler;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            sendMessage(returnInfoAboutConnectedUsers());

            String newUser = addNewUser(reader);

            sendMessage(REGISTRATION_OK_RESPONSE);

            notifyUsersAboutNewUser(newUser);

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                sendMessagesToAllUsers(clientMessage, newUser);//

            } while (!clientMessage.equals(BYE_MESSAGE));//

            connectionHandler.removeUser(newUser, this);
            socket.close();
            notifyAboutDeletedUser(newUser);
            writer.close();
            reader.close();
        } catch (IOException | NullPointerException ex) {
            System.out.println("Error in server.UserThread: " + socket.getInetAddress());
        }
    }

    private String addNewUser(BufferedReader reader) throws IOException {
        String userName = reader.readLine();
        while (!connectionHandler.isUserAdded(userName)) {
            userName = reader.readLine();
        }
        return userName;
    }

    private void sendMessagesToAllUsers(String clientMessage, String userName) {
        String serverMessage = LEFT_BRACKET + userName + RIGHT_BRACKET + COLON + clientMessage;
        connectionHandler.broadcast(serverMessage, this);
    }

    private void notifyAboutDeletedUser(String userName) {
        String serverMessage = userName + HAS_QUITED;
        connectionHandler.broadcast(serverMessage, this);
    }

    private void notifyUsersAboutNewUser(String userName) {
        String serverMessage = NEW_USER_CONNECTED + userName;
        connectionHandler.broadcast(serverMessage, this);
    }

    private String returnInfoAboutConnectedUsers() {
        if (connectionHandler.hasUsers()) {
            return CONNECTED_USERS + connectionHandler.getUserNames();
        } else {
            return NO_OTHER_USERS_CONNECTED;
        }
    }

    public void sendMessage(String message) {
        writer.write(message + SEPARATOR);
        writer.flush();
    }
}

