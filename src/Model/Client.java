package Model;
import Model.Contact.Contact;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Client {
        private String name;
        private static final AtomicInteger count = new AtomicInteger(1);
        private final int clientID;
        private ClientStatus status;
        private ClientPriority priority;
        Contact contact;

        public Client(String name, ClientPriority priority) {
            this.name = name;
            status = ClientStatus.NEW;
            this.priority = priority;
            clientID = count.getAndIncrement();
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
