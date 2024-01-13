package util;

import Service.ClientManagement;

import java.util.Scanner;

public class ScannerWrapper implements AutoCloseable {

    private static final ScannerWrapper INSTANCE;
    private final Scanner scanner;

    public static ScannerWrapper getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ScannerWrapper();
    }


    private ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
