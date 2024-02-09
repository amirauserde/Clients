package facade.impl;
import Model.ClientPriority;
import Service.ClientManagement;
import Service.Exception.ValidationException;
import Service.Validation.ClientValidationContext;
import dto.ClientDto;
import dto.LegalClientDto;
import dto.RealClientDto;
import mapper.ClientMapper;

import java.util.List;

public class ClientFacadeImpl {

    private final ClientManagement clientManagement;
    private static ClientFacadeImpl INSTANCE;
    private ValidationContext<ClientDto> validationContext;


    public static ClientFacadeImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (ClientFacadeImpl.class) {
                if(INSTANCE == null){
                    INSTANCE = new ClientFacadeImpl();
                }
            }
        }
        return INSTANCE;
    }

    private ClientFacadeImpl() {
        this.clientManagement = ClientManagement.getInstance();
        validationContext = new ClientValidationContext();
    }

    public int addClient(int type, String name, String secondElement, String priority) throws ValidationException {
        if(findClient(name).size() > 0) {
            if(findClient(secondElement + ", " + name).size() > 0 ||
                    findClient(secondElement).size() > 0) {
                return -1;
            }
        }
        ClientDto newClientDto;

        if(type == 1) {
            newClientDto = new RealClientDto(null, name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        } else {
            newClientDto = new LegalClientDto(null, name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        }
        validationContext.validate(newClientDto);

        return clientManagement.addClient(type, name, secondElement, priority);
    }

    public void printClients() {
        clientManagement.printClients();
    }

    public String printClientDetails(int clientId) {
        return clientManagement.printClientDetails(clientId);
    }

    public String activeClientBrief(int clientId) {
        return clientManagement.activeClientBrief(clientId);
    }

    public List<Integer> findClient(String searchItem) {
        return clientManagement.findClient(searchItem);
    }

    public boolean addPhoneNumber(int clientId, String number, String numberType) {
        return clientManagement.addPhoneNumber(clientId, number, numberType);
    }

    public void setEmail(int clientId, String emailAddress) {
        clientManagement.setEmail(clientId, emailAddress);
    }

    public boolean addAddress(int clientId, String[] info) {
        return clientManagement.addAddress(clientId, info);
    }

    public boolean statusChange(int clientId, String status) {
        return clientManagement.statusChange(clientId, status);
    }

    public boolean priorityChange(int clientId, String priority) {
        return clientManagement.priorityChange(clientId, priority);
    }
}
