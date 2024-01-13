import View.ClientConsole;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (ClientConsole newConsole = ClientConsole.getInstance()) {
            newConsole.run();
        }
    }
}

