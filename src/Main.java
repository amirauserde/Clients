import View.ClientConsole;

public class Main {
    public static void main(String[] args) {
        try (ClientConsole newConsole = ClientConsole.getInstance()) {
            newConsole.run();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

