package View;
import Service.ClientManagement;
import java.util.List;
import java.util.Scanner;

public class ClientConsole implements AutoCloseable {
    Scanner scanner = new Scanner(System.in);
    ClientManagement newCM = ClientManagement.getInstance();
    private static ClientConsole instance;

    public static ClientConsole getInstance() {
        if(instance == null) {
            synchronized (ClientConsole.class) {
                if(instance == null){
                    instance = new ClientConsole();
                }
            }
        }
        return instance;
    }

    private ClientConsole() {
    }

    public void run() {

        System.out.println("Client Management panel");

        boolean menuRun = true;

        while (menuRun) {
            System.out.println("Please choose from the options:");
            System.out.println("""
                    1. Find (- edit) client
                    2. Add a client
                    3. Print clients
                    4. exit""");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> findMenu();
                case "2" -> addClient();
                case "3" -> newCM.printClients();
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }

    }

    private void findMenu() {
        String search = getUserInput("Enter your search");
        List<Integer> searchedIds =  newCM.findClient(search);
        if(searchedIds.isEmpty()) {
            System.out.println("No results found!");
        } else {
            searchedIds.forEach(clientId -> System.out.println(newCM.activeClientBrief(clientId)));
            int chosenID = Integer.valueOf(getUserInput("For more details enter the client ID:"));

            if(!searchedIds.contains(chosenID)) {
                System.out.println("Wrong ID!");
            } else {
                clientMenu(chosenID);
            }
        }
    }

    private void clientMenu(int clientId) {
        System.out.println(newCM.printClientDetails(clientId));
        System.out.println();
        boolean menuRun = true;
        while (menuRun) {
            System.out.println("""
                    1. Add a contact
                    2. Change status
                    3. Change priority
                    4. exit""");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addContact(clientId);
                case "2" -> statusChange(clientId);
                case "3" -> priorityChange(clientId);
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private void addClient() {
        int clientChoice = Integer.parseInt(getUserInput("Client type:%n1. Real%n2. Legal".formatted()));
        int newClientId;
        if(clientChoice == 1) {
            String firstName = getUserInput("Enter first name: ");
            String lastName = getUserInput("Enter last name: ");
            String priority = getUserInput("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)");
            newClientId = newCM.addRealClient(firstName, lastName, priority);
        } else {
            String name = getUserInput("Enter company's name: ");
            String nationalCode = getUserInput("Enter company's national code: ");
            String priority = getUserInput("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)");
            newClientId = newCM.addLegalClient(name, nationalCode, priority);
        }

        if(newClientId >= 0) {
            System.out.println("Congratulations, new client added.");
            if(getUserInput("To add contacts and address press 1").equals("1")) {
                addContact(newClientId);
            }
        } else {
            System.out.println("Client already in the list.");
        }
    }

    private void addContact(int clientId) {
        int choice = Integer.parseInt(getUserInput("""
                Press:
                1. To add a Phone number,
                2. To add an Address,
                3. To add/update email address"""));
        switch (choice) {
            case 1 -> addPhoneNumber(clientId);
            case 2 -> addAddress(clientId);
            default -> newCM.setEmail(clientId, getUserInput("Enter email address: "));
        };
    }

    private void addPhoneNumber(int clientId) {
        String type = getUserInput("Enter number type (HOME, OFFICE, MOBILE, FAX, OTHER): ");
        String number = getUserInput("Enter number (10 digits): ");
        boolean result = newCM.addPhoneNumber(clientId, number, type);
        if(result) {
            System.out.println("Phone number added.");
        } else {
            System.out.println("Phone number already in contacts");
        }
    }

    private void addAddress(int clientID) {
        String[] info = new String[4];
        info[3] = getUserInput("Enter Address type (MAIN, SECOND, OFFICE, DELIVERY, OTHER): ");
        info[0] = getUserInput("Please enter the country: ");
        info[1] = getUserInput("Please enter the city: ");
        info[2] = getUserInput("Please enter the address: ");
        boolean result = newCM.addAddress(clientID, info);
        if(result) {
            System.out.println("Address added.");
        } else {
            System.out.println("Address already in contacts");
        }
    }

    private void priorityChange(int clientId) {
        String priority = getUserInput("Please Enter the new priority (CRITICAL, HIGH, MEDIUM, LOW): ");
        boolean result = newCM.priorityChange(clientId, priority);
        System.out.println(result? "Priority changed!" : "Wrong input!");
    }

    private void statusChange(int clientId) {
        String status = getUserInput("Please Enter the new status (CURRENT, PAST): ");
        boolean result = newCM.statusChange(clientId, status);
        System.out.println(result? "Status changed!" : "Wrong input!");
    }

    private String getUserInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
