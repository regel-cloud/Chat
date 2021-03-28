package main;

import client.ClientWorker;
import common.PropertyReader;
import inputparser.AddressParser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter address:");
        String hostName = new AddressParser().returnInput();
        int port = 0;
        try {
            port = new PropertyReader().getPort();
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading property");
        }
        ClientWorker client = new ClientWorker(hostName, port);
        client.execute();
    }
}
