package dto;

import Model.ClientPriority;
import Model.Contact.Contact;
import Model.Person;

public class RealClientDto extends ClientDto{
    Person client;
    String lastName;

    public RealClientDto(Integer id, String firstName, String lastName, ClientPriority priority) {
        super(id, lastName + ", " + firstName, priority);
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }
}
