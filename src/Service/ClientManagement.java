package Service;
import Model.*;
import Model.Contact.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class ClientManagement {

    private final ArrayList<Client> clients;
    private static ClientManagement INSTANCE;

    public static ClientManagement getInstance() {
        if(INSTANCE == null) {
            synchronized (ClientManagement.class) {
                if(INSTANCE == null){
                    INSTANCE = new ClientManagement();
                }
            }
        }
        return INSTANCE;
    }

    private ClientManagement() {
        this.clients = new ArrayList<>();
    }

    public <T extends Client> int addClient(T client) {
        clients.add(client);
        return client.getClientID();
    }

    public int addClient(int type, String name, String secondElement, String priority) {
        if(findClient(name).size() > 0) {
            if(findClient(secondElement + ", " + name).size() > 0 ||
                    findClient(secondElement).size() > 0) {
                return -1;
            }
        }
        Client newClient;

        if(type == 1) {
            newClient = new RealClient(name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        } else {
            newClient = new LegalClient(name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        }

        return addClient(newClient);
    }


    public boolean priorityChange(int clientId, String priority) {
        if(ClientPriority.contains(priority)) {
            Client client = clients.stream()
                    .filter(c -> c.getClientID() == clientId)
                    .findFirst()
                    .orElse(null);
            if (client == null) {
                return false;
            }
            client.setPriority(priority);
            return true;
        }
        return false;
    }

    public boolean statusChange(int clientId, String status) {
        if(ClientStatus.contains(status)) {
            Client client = clients.stream()
                    .filter(c -> c.getClientID() == clientId)
                    .findFirst()
                    .orElse(null);
            if (client == null) {
                return false;
            }
            client.setStatus(status);
            return true;
        }
        return false;
    }

    public boolean addPhoneNumber(int clientId, String number, String numberType) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return false;
        }

        if(contact.getNumbers().stream().anyMatch(s -> s.getNumber().equals(number))) {
            return false;
        }
        if(!(number.length() == 10 && number.matches("\\d{10}"))) {
            throw new ArithmeticException("Please Enter the number in correct format!");
        }
        contact.getNumbers().add(new PhoneNumber(number, numberType));
        return true;
    }

    public boolean addAddress(int clientId, String[] info) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return false;
        }
        Address newAddress = new Address(info[0], info[1], info[2], info[3].toUpperCase());
        if(contact.getAddresses().contains(newAddress)) {
            return false;
        }
        contact.getAddresses().add(newAddress);
        return true;
    }

    public void setEmail(int clientId, String emailAddress) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return;
        }
        contact.setEmailAddress(emailAddress);
    }


    public List<Integer> findClient(String searchItem) {
        List<Integer> results;
        results = clients.stream().filter(client -> client.getName().toUpperCase()
                .contains(searchItem.toUpperCase()) || String.valueOf(client.getClientID()).equals(searchItem))
                .map(Client::getClientID).collect(Collectors.toList());
        if(results.isEmpty()) {
            results = clients.parallelStream().
                    filter(client -> client.getPriority().equalsIgnoreCase(searchItem) ||
                            client.getStatus().equalsIgnoreCase(searchItem))
                    .map(Client::getClientID).collect(Collectors.toList());
        }
        return results;
    }

    public void printClients() {
        if(clients.isEmpty()) {
            System.out.println("There is still no client :(");
            return;
        }
        clients.stream().filter(not(s -> s.getStatus().equalsIgnoreCase("Past")))
                .forEach(s -> System.out.println(activeClientBrief(s.getClientID())));
    }

    public String activeClientBrief(int clientId) {
        Client client = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .filter(not(c -> c.getStatus().equalsIgnoreCase("Past")))
                .findFirst()
                .orElse(null);
        if (client == null) {
            return "No client found";
        }
        return "%d) %-15s%-10s%s".formatted(clientId,
                client.getName(), client.getPriority(), client.getStatus());
    }

    public String printClientDetails(int clientId) {
        Client client = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .findFirst()
                .orElse(null);
        if (client == null) {
            return "No client found";
        }
        return client.toString();
    }
}
