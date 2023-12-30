package Model.Contact;

import java.util.ArrayList;
import java.util.Arrays;

public class Contacts {
    private String contactName;
    private ArrayList<PhoneNumber> numbers;
    private String emailAddress;
    private ArrayList<Address> addresses;

    public Contacts(String contactName) {
        this.contactName = contactName;
        this.numbers = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public boolean addNumber(String number, String type) {
        if(numbers.stream().anyMatch(s -> s.getNumber().equals(number))) {
            return false;
        }
        numbers.add(new PhoneNumber(number, NumberType.valueOf(type.toUpperCase())));
        return true;
    }

    public boolean addAddress(String country, String city, String address, String addressType) {
        Address newAddress = new Address(country, city, address, AddressType.valueOf(addressType));
        if(addresses.contains(newAddress)) {
            return false;
        } else {
            addresses.add(newAddress);
            return true;
        }
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                %s%n
                Email address: %s%n
                Phone numbers:%n
                """.formatted(contactName, emailAddress));
        numbers.forEach(number -> sb.append(number.toString()).append("\n"));
        sb.append("Addresses%n".formatted());
        addresses.forEach(address -> sb.append(address.toString()).append("\n"));

        return sb.toString();
    }
}
