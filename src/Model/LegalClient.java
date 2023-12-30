package Model;

import Model.Contact.Contacts;

public class LegalClient extends Client {

    Company company;
    Person contactPerson;


    public LegalClient(String name, String nationalCode, ClientPriority priority) {
        super(name, priority);
        this.company = new Company(name, nationalCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("%15s".formatted(super.getClientID())).append("\n")
                .append("name: %-10s National code: %s%n".formatted(company.name(), company.nationalCode()))
                .append("Priority: %s%n".formatted(super.getPriority()))
                .append("Contact Person: " + contactPerson.firstName() + contactPerson.lastName());
        return sb.toString();
    }
}
