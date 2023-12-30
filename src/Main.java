import Service.ClientsConsole;

public class Main {
    public static void main(String[] args) {
        ClientsConsole newConsole = new ClientsConsole();
            newConsole.run();
    }
}

//try(ClientsConsole newConsole = new ClientsConsole()) {
//        newConsole.run();
//        } catch(Exception e) {
//        System.out.println("From Exception Block");
//        }