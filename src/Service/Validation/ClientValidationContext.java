package Service.Validation;

import Model.RealClient;
import Service.Exception.ValidationException;
import dto.ClientDto;
import dto.RealClientDto;
import facade.impl.ValidationContext;

public class ClientValidationContext extends ValidationContext<ClientDto> {

    public ClientValidationContext() {

        addValidation(client -> {
            String fullName = client.getName();
            String name = fullName.substring(fullName.lastIndexOf(',') + 1);
            name = name.trim();
            if(name.isEmpty()) {
                throw new ValidationException("Name must not be empty or null");
            }
        });

        addValidation(client -> {
            if(client instanceof RealClientDto) {
                String family = client.getName().split(",")[0];
                if(family == null ||
                family.trim().isEmpty()) {
                    throw new ValidationException("Family must not be empty or null");
                }
            }
        });



    }

}
