package connectionhandler;

import userhandler.UserHandler;

import java.io.IOException;
import java.util.List;

public interface ConnectionHandler {
    boolean hasUsers();

    List<String> getUserNames();

    void broadcast(String message, UserHandler userHandler);

    void removeUser(String userName, UserHandler userHandler) throws IOException;

    boolean isUserAdded(String userName);

}
