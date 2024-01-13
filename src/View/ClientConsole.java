package View;
import Service.ClientManagement;
import util.ScannerWrapper;
import java.util.List;

public class ClientConsole implements AutoCloseable {
    ScannerWrapper scannerWrapper = ScannerWrapper.getInstance();
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
            String choice = scannerWrapper.getUserInput("""
                    1. Find (- edit) client
                    2. Add a client
                    3. Print clients
                    4. exit""");

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
        String search = scannerWrapper.getUserInput("Enter your search");
        List<Integer> searchedIds =  newCM.findClient(search);
        if(searchedIds.isEmpty()) {
            System.out.println("No results found!");
        } else {
            searchedIds.forEach(clientId -> System.out.println(newCM.activeClientBrief(clientId)));
            int chosenID = Integer.parseInt(scannerWrapper.getUserInput("For more details enter the client ID:"));

            if(!searchedIds.contains(chosenID)) {
                System.out.println("Wrong ID!");
            } else {
                clientDetailsMenu(chosenID);
            }
        }
    }

    private void clientDetailsMenu(int clientId) {
        System.out.println(newCM.printClientDetails(clientId));
        System.out.println();
        boolean menuRun = true;
        while (menuRun) {
            String choice = scannerWrapper.getUserInput("""
                    1. Add a contact
                    2. Change status
                    3. Change priority
                    4. exit""");

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
        int clientChoice = Integer.parseInt
                (scannerWrapper.getUserInput("Client type:%n1. Real%n2. Legal".formatted()));
        int newClientId;

        String firstName = scannerWrapper.getUserInput("Enter first name: ");
        String secondElement =
                scannerWrapper.getUserInput((clientChoice == 1)?
                        "Enter last name: " : "Enter company's national code: ");
        String priority = scannerWrapper.getUserInput("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)");
        newClientId = newCM.addClient(clientChoice, firstName, secondElement, priority);

        if(newClientId >= 0) {
            System.out.println("Congratulations, new client added.");
            if(scannerWrapper.getUserInput("To add contacts and address press 1").equals("1")) {
                addContact(newClientId);
            }
        } else {
            System.out.println("Client already in the list.");
        }
    }

    private void addContact(int clientId) {
        boolean addContactMenu = true;
        while (addContactMenu) {
            int choice = Integer.parseInt(scannerWrapper.getUserInput("""
                Press:
                1. To add a Phone number,
                2. To add an Address,
                3. To add/update email address
                4. To exit"""));
            switch (choice) {
                case 1 -> addPhoneNumber(clientId);
                case 2 -> addAddress(clientId);
                case 3 -> newCM.setEmail(clientId, scannerWrapper.getUserInput("Enter email address: "));
                default -> addContactMenu = false;
            }
        }
    }

    private void addPhoneNumber(int clientId) {
        String type = scannerWrapper.getUserInput("Enter number type (HOME, OFFICE, MOBILE, FAX, OTHER): ");
        String number = scannerWrapper.getUserInput("Enter number (10 digits): ");
        boolean result = newCM.addPhoneNumber(clientId, number, type);
        if(result) {
            System.out.println("Phone number added.");
        } else {
            System.out.println("Phone number already in contacts");
        }
    }

    private void addAddress(int clientID) {
        String[] info = new String[4];
        info[3] = scannerWrapper.getUserInput("Enter Address type (MAIN, SECOND, OFFICE, DELIVERY, OTHER): ");
        info[0] = scannerWrapper.getUserInput("Please enter the country: ");
        info[1] = scannerWrapper.getUserInput("Please enter the city: ");
        info[2] = scannerWrapper.getUserInput("Please enter the address: ");
        boolean result = newCM.addAddress(clientID, info);
        if(result) {
            System.out.println("Address added.");
        } else {
            System.out.println("Address already in contacts");
        }
    }

    private void priorityChange(int clientId) {
        String priority = scannerWrapper.getUserInput("Please Enter the new priority (CRITICAL, HIGH, MEDIUM, LOW): ");
        boolean result = newCM.priorityChange(clientId, priority);
        System.out.println(result? "Priority changed!" : "Wrong input!");
    }

    private void statusChange(int clientId) {
        String status = scannerWrapper.getUserInput("Please Enter the new status (CURRENT, PAST): ");
        boolean result = newCM.statusChange(clientId, status);
        System.out.println(result? "Status changed!" : "Wrong input!");
    }

    @Override
    public void close() {
        scannerWrapper.close();
    }
}
