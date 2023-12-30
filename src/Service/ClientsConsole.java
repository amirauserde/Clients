package Service;

import Model.*;

import java.util.List;
import java.util.Scanner;

public class ClientsConsole implements AutoCloseable {

    private String companyName;
    Scanner scanner = new Scanner(System.in);

    public void run() {
        ClientsManagement newCRM = new ClientsManagement(companyName);

        System.out.println(companyName + " CRM panel");

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
                case "1" -> findMenu(newCRM);
                case "2" -> addClient(newCRM);
                case "3" -> newCRM.printClients();
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }

    }

    private void findMenu(ClientsManagement crm) {
        String search = getUserInput("Enter your search");
        List<? extends Client> searched =  crm.findClient(search);
        if(searched.isEmpty()) return;
        searched.forEach(client -> System.out.println(client.clientBrief(client)));
        String chosenID = getUserInput("For more details enter the client ID:");
        Client chosenClient = crm.findClient(chosenID).get(0);
        if(chosenClient == null) {
            System.out.println("Wrong ID!");
        } else {
            clientMenu(chosenClient, crm);
        }
    }

    private <T extends Client> void clientMenu(T client, ClientsManagement crm) {
        System.out.println(client);
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
                case "1" -> addContact(client, crm);
                case "2" -> statusChange(client);
                case "3" -> priorityChange(client);
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private void addClient(ClientsManagement crm) {
        int clientChoice = Integer.parseInt(getUserInput("Client type:%n1. Persona%n2. Legal".formatted()));
        if(clientChoice == 1) {
            String firstName = getUserInput("Enter first name: ");
            String lastName = getUserInput("Enter last name: ");
            ClientPriority priority = ClientPriority.valueOf(
                    getUserInput("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)").toUpperCase());
            if(crm.findClient(lastName + ", " + firstName).isEmpty()) {
                PersonaClient newClient = new PersonaClient(firstName, lastName, priority);
                crm.addClient(newClient);
                System.out.println("Congratulations, new client added.");
                if(getUserInput("To add contacts and address press 1").equals("1")) {
                    addContact(newClient, crm);
                };
            } else {
                System.out.println("Client already in our list.");
            }

        } else {
            String name = getUserInput("Enter company's name: ");
            String nationalCode = getUserInput("Enter company's national code: ");
            ClientPriority priority = ClientPriority.valueOf(
                    getUserInput("Enter client's priority (CRITICAL, HIGH, MEDIUM, LOW)").toUpperCase());
            if(crm.findClient(name).isEmpty()) {
                LegalClient newClient = new LegalClient(name, nationalCode, priority);
                crm.addClient(newClient);
                System.out.println("Congratulations, new client added.");
                if(getUserInput("To add contacts and address press 1").equals("1")) {
                    addContact(newClient, crm);
                };
            } else {
                System.out.println("Client already in our list.");
            }
        }


    }

    private <T extends Client> void addContact(T client, ClientsManagement crm) {
        int choice = Integer.parseInt(getUserInput("""
                Press:
                1. To add a Phone number,
                2. To add an Address,
                3. To add/update email address"""));
        switch (choice) {
            case 1 -> addPhoneNumber(client, crm);
            case 2 -> addAddress(client, crm);
            default -> crm.setEmail(client, getUserInput("Enter email address: "));
        };
    }

    private void addPhoneNumber(Client client, ClientsManagement crm) {
        String type = getUserInput("Enter number type (HOME, OFFICE, MOBILE, FAX, OTHER): ");
        String number = getUserInput("Enter number (10 digits): ");
        boolean result = crm.addPhoneNumber(client, number, type);
        if(result) {
            System.out.println("Phone number added.");
        } else {
            System.out.println("Phone number already in contacts");
        }
    }

    private void addAddress(Client client, ClientsManagement crm) {
        String[] info = new String[4];
        info[4] = getUserInput("Enter number type (MAIN, SECOND, OFFICE, DELIVERY, OTHER): ");
        info[0] = getUserInput("Please enter the country: ");
        info[1] = getUserInput("Please enter the city: ");
        info[2] = getUserInput("Please enter the address: ");
        boolean result = crm.addAddress(client, info);
        if(result) {
            System.out.println("Address added.");
        } else {
            System.out.println("Address already in contacts");
        }
    }

    private void priorityChange(Client client) {
        String priority = getUserInput("Please Enter the new priority (CRITICAL, HIGH, MEDIUM, LOW): ");
        client.setPriority(priority);
    }

    private void statusChange(Client client) {
        String status = getUserInput("Please Enter the new status (CURRENT, PAST): ");
        client.setStatus(status);
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
