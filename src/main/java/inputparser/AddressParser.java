package inputparser;

import java.util.Scanner;

public class AddressParser {

    private String takeInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String returnInput() {
        return takeInput();
    }

}
