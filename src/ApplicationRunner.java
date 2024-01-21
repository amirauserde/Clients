import View.ClientConsole;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (ClientConsole newConsole = ClientConsole.getInstance()) {
            newConsole.run();
        } catch (Throwable ex) {
            System.out.println("System error! Sorry we have to close the App!");
        }
    }
}

