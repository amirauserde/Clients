package Model.Contact;

enum AddressType {
    MAIN, SECOND, OFFICE, DELIVERY, OTHER
}

public class Address {
    private AddressType addressType;
    private String country;
    private String city;
    private String address;

    public Address(String country, String city, String address, String  addressType) {
        try {
            this.addressType = AddressType.valueOf(addressType);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong address type!");
        }
        this.country = country;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return "(%s) %s, %s, %s".formatted(addressType, country, city, address);
    }
}

