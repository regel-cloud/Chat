package writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Scanner;

public class WriteThread implements Runnable {
    private static final String BYE_MESSAGE = "##exit";
    private static final String SEPARATOR = System.lineSeparator();
    private final Socket socket;

    public WriteThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                Scanner scanner = new Scanner(System.in);
                PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String text;
            do {
                text = scanner.nextLine();
                String message = returnMessage(text);
                sendMessageToServer(writer, message);

            } while (!text.equals(BYE_MESSAGE));
            socket.close();

        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
        }
    }


    private String returnMessage(String text) {
        LocalDateTime time = LocalDateTime.now();
        return text + " " + time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.UK));
    }


    private void sendMessageToServer(PrintWriter writer, String message) {
        writer.write(message + SEPARATOR);
        writer.flush();

    }
}
