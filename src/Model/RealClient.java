package Model;

import Model.Contact.Contact;

public class RealClient extends Client {
    Person client;

    public RealClient(String firstName, String lastName, ClientPriority priority) {
        super(lastName + ", " + firstName, priority);
        client = new Person(firstName, lastName);
        contact = new Contact(firstName + " " + lastName + "contact details");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "%15s: %s".formatted("ID",super.getClientID()) + "\n" +
                "first name: %-10s family name: %s%n".formatted(client.firstName(), client.lastName()) +
                "Priority: %s%n".formatted(super.getPriority()) +
                super.contact.toString();
    }
}
