package View;
import facade.impl.ClientFacadeImpl;
import util.ScannerWrapper;
import java.util.List;
import java.util.function.Function;

public class ClientConsole implements AutoCloseable {
    ScannerWrapper scannerWrapper = ScannerWrapper.getInstance();
    ClientFacadeImpl clientFacade = ClientFacadeImpl.getInstance();
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
                    4. exit""", Function.identity());

            switch (choice) {
                case "1" -> findMenu();
                case "2" -> addClient();
                case "3" -> clientFacade.printClients();
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }

    }

    private void findMenu() {
        String search = scannerWrapper.getUserInput("Enter your search", Function.identity());
        List<Integer> searchedIds =  clientFacade.findClient(search);
        if(searchedIds.isEmpty()) {
            System.out.println("No results found!");
        } else {
            searchedIds.forEach(clientId -> System.out.println(clientFacade.activeClientBrief(clientId)));
            int chosenID = scannerWrapper.
                    getUserInput("For more details enter the client ID:", Integer::parseInt);

            if(!searchedIds.contains(chosenID)) {
                System.out.println("Wrong ID!");
            } else {
                clientDetailsMenu(chosenID);
            }
        }
    }

    private void clientDetailsMenu(int clientId) {
        System.out.println(clientFacade.printClientDetails(clientId));
        System.out.println();
        boolean menuRun = true;
        while (menuRun) {
            String choice = scannerWrapper.getUserInput("""
                    1. Add a contact
                    2. Change status
                    3. Change priority
                    4. exit""", Function.identity());

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

        int clientChoice = (scannerWrapper.getUserInput
                ("Client type:%n1. Real%n2. Legal".formatted(), Integer::parseInt));

        int newClientId = -1;

        String firstName = scannerWrapper.getUserInput("Enter first name: ", Function.identity());
        String secondElement =
                scannerWrapper.getUserInput((clientChoice == 1)?
                        "Enter last name: " : "Enter company's national code: ", Function.identity());
        String priority = scannerWrapper.getUserInput
                ("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)", Function.identity());

        try {
            newClientId = clientFacade.addClient(clientChoice, firstName, secondElement, priority);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
            addClient();
        }
        if(newClientId >= 0) {
            System.out.println("Congratulations, new client added.");
            if(scannerWrapper.getUserInput
                    ("To add contacts and address press 1", Function.identity()).equals("1")) {
                addContact(newClientId);
            }
        } else {
            System.out.println("Client already in the list.");
        }
    }

    private void addContact(int clientId) {
        boolean addContactMenu = true;
        while (addContactMenu) {
            int choice = scannerWrapper.getUserInput("""
                Press:
                1. To add a Phone number,
                2. To add an Address,
                3. To add/update email address
                4. To exit""", Integer::parseInt);
            switch (choice) {
                case 1 -> addPhoneNumber(clientId);
                case 2 -> addAddress(clientId);
                case 3 -> clientFacade.setEmail(clientId, scannerWrapper.
                        getUserInput("Enter email address: ", Function.identity()));
                default -> addContactMenu = false;
            }
        }
    }

    private void addPhoneNumber(int clientId) {
        String type = scannerWrapper.
                getUserInput("Enter number type (HOME, OFFICE, MOBILE, FAX, OTHER): ", Function.identity());
        String number = scannerWrapper.
                getUserInput("Enter number (10 digits): ", Function.identity());
        boolean result = false;
        try {
            result = clientFacade.addPhoneNumber(clientId, number, type);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            addPhoneNumber(clientId);
            return;
        }
        if(result) {
            System.out.println("Phone number added.");
        } else {
            System.out.println("Phone number already in contacts");
        }
    }

    private void addAddress(int clientId) {
        String[] info = new String[4];
        info[3] = scannerWrapper.
                getUserInput("Enter Address type (MAIN, SECOND, OFFICE, DELIVERY, OTHER): ", Function.identity());
        info[0] = scannerWrapper.
                getUserInput("Please enter the country: ", Function.identity());
        info[1] = scannerWrapper.getUserInput("Please enter the city: ", Function.identity());
        info[2] = scannerWrapper.getUserInput("Please enter the address: ", Function.identity());
        boolean result = false;
        try {
            result = clientFacade.addAddress(clientId, info);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            addPhoneNumber(clientId);
            return;
        }
        if(result) {
            System.out.println("Address added.");
        } else {
            System.out.println("Address already in contacts");
        }
    }

    private void priorityChange(int clientId) {
        String priority = scannerWrapper.
                getUserInput("Please Enter the new priority (CRITICAL, HIGH, MEDIUM, LOW): "
                        , Function.identity());
        boolean result = clientFacade.priorityChange(clientId, priority);
        System.out.println(result? "Priority changed!" : "Wrong input!");
    }

    private void statusChange(int clientId) {
        String status = scannerWrapper.
                getUserInput("Please Enter the new status (CURRENT, PAST): ", Function.identity());
        boolean result = clientFacade.statusChange(clientId, status);
        System.out.println(result? "Status changed!" : "Wrong input!");
    }

    @Override
    public void close() {
        scannerWrapper.close();
    }
}
