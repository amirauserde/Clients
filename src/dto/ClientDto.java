package dto;
import Model.ClientPriority;
import Model.ClientStatus;
import Model.Contact.Contact;

public class ClientDto {
    private String name;
    private final Integer clientID;
    private ClientStatus status;
    private ClientPriority priority;
    Contact contact;

    public ClientDto (Integer id, String name, ClientPriority priority) {
        this.name = name;
        status = ClientStatus.NEW;
        this.priority = priority;
        clientID = id;
        contact = new Contact(name);
    }

    public Contact getContact() {
        return contact;
    }

    public void setStatus(String status) {
        this.status = ClientStatus.lookup(status.toUpperCase());;
    }

    public void setPriority(String priority) {
        ClientPriority p = ClientPriority.lookup(priority.toUpperCase());
        this.priority = p;
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
