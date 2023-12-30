package Model;

import Model.Contact.Contacts;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private String name;
    private static final AtomicInteger count = new AtomicInteger(1001);
    private int clientID;
    private ClientStatus status;
    private ClientPriority priority;
    Contacts contact;

    public Client(String name, ClientPriority priority) {
        this.name = name;
        this.status = ClientStatus.NEW;
        this.priority = priority;
        clientID = count.incrementAndGet();
        contact = new Contacts(name);
    }


    Contacts getContact() {
        return contact;
    }

    public void setStatus(String status) {
        ClientStatus s = ClientStatus.valueOf(status.toUpperCase());
        this.status = s;
    }

    public void setPriority(String priority) {
        ClientPriority p = ClientPriority.valueOf(priority.toUpperCase());
        this.priority = p;
    }

    public <T extends Client> String clientBrief(T client) {
        return "%d) %-10s%-5s%s".formatted(client.getClientID(),
                client.getName(), client.getPriority(), client.getStatus());
    }

    public int getClientID() {
        return clientID;
    }

    public String getPriority() {
        return priority.toString();
    }

    public String getStatus() {
        return status.toString();
    }

    public String getName() {
        return name;
    }


}
