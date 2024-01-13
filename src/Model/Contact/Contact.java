package Model.Contact;
import java.util.ArrayList;

public class Contact {
    private String contactName;
    private final ArrayList<PhoneNumber> numbers;
    private String emailAddress;
    private final ArrayList<Address> addresses;

    public Contact(String contactName) {
        this.contactName = contactName;
        this.numbers = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public ArrayList<PhoneNumber> getNumbers() {
        return numbers;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }


    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                %s %n
                Email address: %s%n
                Phone numbers:%n
                """.formatted(contactName, emailAddress));
        numbers.forEach(number -> sb.append(number.toString()).append("\n"));
        sb.append("Addresses%n".formatted());
        addresses.forEach(address -> sb.append(address.toString()).append("\n"));

        return sb.toString();
    }
}
