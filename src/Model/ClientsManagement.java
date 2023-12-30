package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientsManagement {
    private String companyName;
    private ArrayList<Client> clients;

    public ClientsManagement(String companyName) {
        this.companyName = companyName;
        this.clients = new ArrayList<>();
    }

    public <T extends Client> boolean addClient(T client) {
        if(findClient(client)) return false;
        clients.add(client);
        return true;
    }

    public void printClients() {
        clients.forEach(System.out::println);
    }

    public boolean addPhoneNumber(Client client, String number, String numberType) {
        return client.getContact().addNumber(number, numberType);
    }

    public boolean addAddress(Client client, String[] info) {
        return client.getContact().addAddress(info[0], info[1], info[2], info[3]);
    }

    public void setEmail(Client client, String emailAddress) {
        client.getContact().setEmailAddress(emailAddress);
    }

    private  <T extends Client> boolean findClient(T client) {
        return clients.stream().anyMatch(client::equals);
    }

    public List<? extends Client> findClient(String searchItem) {
        List<? extends Client> result = new ArrayList<>();
        result = clients.stream().filter(client -> client.getName()
                .contains(searchItem) || String.valueOf(client.getClientID()).equals(searchItem))
                .collect(Collectors.toList());
        if(result.isEmpty()) {
            result = clients.parallelStream().
                    filter(client -> client.getPriority().toString().equalsIgnoreCase(searchItem) ||
                            client.getStatus().toString().equalsIgnoreCase(searchItem) ).collect(Collectors.toList());
        }

        return result;
    }



}
