package Model;

import Model.Contact.Contacts;

public class PersonaClient extends Client {
    Person client;


    public PersonaClient(String firstName, String lastName, ClientPriority priority) {
        super(lastName + ", " + firstName, priority);
        client = new Person(firstName, lastName);
        contact = new Contacts(firstName + " " + lastName + "contact details");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("%15s%n".formatted(super.getClientID()))
                .append("first name: %-10s family name: %s%n".formatted(client.firstName(), client.lastName()))
                .append("Priority: %s%n".formatted(super.getPriority()));
        return sb.toString();
    }
}
