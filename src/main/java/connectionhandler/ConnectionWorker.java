package connectionhandler;


import userhandler.UserHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionWorker implements ConnectionHandler {
    private final CopyOnWriteArrayList<String> userNames = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<UserHandler> userHandlers = new CopyOnWriteArrayList<>();

    public void processConnection(Socket clientSocket) {
        UserHandler newUser = new UserHandler(clientSocket, this);
        new Thread(newUser).start();
        userHandlers.addIfAbsent(newUser);
    }

    public void broadcast(String message, UserHandler excludeUser) {
        for (UserHandler user : userHandlers) {
            if (user != excludeUser) {
                user.sendMessage(message);
            }
        }
    }

    public boolean isUserAdded(String userName) {
        return userNames.addIfAbsent(userName);
    }

    public void removeUser(String userName, UserHandler user) throws IOException {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userHandlers.remove(user);
            broadcast("The user " + userName + " quited", user);
        }
    }

    public List<String> getUserNames() {
        return this.userNames;
    }

    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
