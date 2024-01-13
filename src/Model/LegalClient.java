package Model;

public class LegalClient extends Client {
    String companyName;
    String nationalCode;

    public LegalClient(String name, String nationalCode, ClientPriority priority) {
        super(name, priority);
        this.companyName = name;
        this.nationalCode = nationalCode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "%15s: %s".formatted("ID",super.getClientID()) + "\n" +
                "name: %-10s National code: %s%n".formatted(companyName, nationalCode) +
                "Priority: %s%n".formatted(super.getPriority()) + "\n" +
                super.contact.toString();
    }
}
