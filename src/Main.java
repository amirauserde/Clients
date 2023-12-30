import Service.ClientsConsole;

public class Main {
    public static void main(String[] args) {
        try (ClientsConsole newConsole = new ClientsConsole()) {
            newConsole.run();
        } catch (Exception e) {
            System.out.println("Error occurred!");
        }
    }
}

