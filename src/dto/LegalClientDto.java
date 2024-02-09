package dto;

import Model.ClientPriority;

public class LegalClientDto extends ClientDto{
    String companyName;
    String nationalCode;

    public LegalClientDto(Integer id,String name, String nationalCode, ClientPriority priority) {
        super(id, name, priority);
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
