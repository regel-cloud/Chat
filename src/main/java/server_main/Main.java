package server_main;


import common.PropertyReader;
import server.Server;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = 0;
        try {
            port = new PropertyReader().getPort();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading property");
        }
        Server server = new Server(port);
        server.execute();
    }
}

