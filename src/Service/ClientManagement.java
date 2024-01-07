package Service;
import Model.*;
import Model.Contact.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class ClientManagement {

    private ArrayList<Client> clients;
    private static ClientManagement instance;

    public static ClientManagement getInstance() {
        if(instance == null) {
            synchronized (ClientManagement.class) {
                if(instance == null){
                    instance = new ClientManagement();
                }
            }
        }
        return instance;
    }

    private ClientManagement() {
        this.clients = new ArrayList<>();
    }

    public <T extends Client> int addClient(T client) {
        clients.add(client);
        return client.getClientID();
    }

    public int addRealClient(String firstName, String lastName, String priority) {
        if(findClient(lastName + ", " + firstName).size() > 0) {
            return -1;
        }
        RealClient newClient = new RealClient(firstName, lastName, ClientPriority.valueOf(priority.toUpperCase()));
        return addClient(newClient);
    }

    public int addLegalClient(String name, String nationalCode, String priority) {
        if(findClient(name).size() > 0) {
            return -1;
        }
        LegalClient newClient = new LegalClient(name, nationalCode, ClientPriority.valueOf(priority.toUpperCase()));
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

    public void printClients() {
        clients.stream().filter(not(s -> s.getStatus().equalsIgnoreCase("Past")))
                .forEach(System.out::println);
    }

    public boolean addPhoneNumber(int clientId, String number, String numberType) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(c -> c.getContact())
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return false;
        }

        if(contact.getNumbers().stream().anyMatch(s -> s.getNumber().equals(number))) {
            return false;
        }
        contact.getNumbers().add(new PhoneNumber(number, numberType));
        return true;
    }

    public boolean addAddress(int clientId, String[] info) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(c -> c.getContact())
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
                .map(c -> c.getContact())
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return;
        }
        contact.setEmailAddress(emailAddress);
    }

    private  <T extends Client> boolean findClient(T client) {
        return clients.stream().anyMatch(client::equals);
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

    public String activeClientBrief(int clientId) {
        Client client = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .filter(not(c -> c.getStatus().equalsIgnoreCase("Past")))
                .findFirst()
                .orElse(null);
        if (client == null) {
            return "No client found";
        }
        return "%d) %-15s%-5s%s".formatted(clientId,
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
