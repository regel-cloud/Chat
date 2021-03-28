package registration;


import client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RegistrationProcess {
    private final Socket socket;
    private final Client client;
    private static final String REGISTRATION_OK_RESPONSE = "Welcome";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String ENTER_NAME = "Enter name: ";

    public RegistrationProcess(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    public void start() {
        try {
            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            String response = reader.readLine();
            System.out.println(response);
            do {
                String userName = getNameFromInput(scanner);
                client.setUserName(userName);
                sendMessageToServer(writer, userName);
                response = reader.readLine();
            } while (!REGISTRATION_OK_RESPONSE.equals(response));
            System.out.println(response);

        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
        }
    }

    private void sendMessageToServer(PrintWriter writer, String message) {
        writer.write(message + SEPARATOR);
        writer.flush();
    }

    private String getNameFromInput(Scanner scanner) {
        System.out.println(ENTER_NAME);
        return scanner.nextLine();
    }
}
