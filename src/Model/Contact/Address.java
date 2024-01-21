package Model.Contact;

import util.LookupUtil;

enum AddressType {
    MAIN, SECOND, OFFICE, DELIVERY, OTHER;
    static public AddressType lookup(String id) {
        return LookupUtil.lookup(AddressType.class, id);
    }
}

public class Address {
    private AddressType addressType;
    private String country;
    private String city;
    private String address;

    public Address(String country, String city, String address, String  addressType) {
        this.addressType = AddressType.lookup(addressType);
        this.country = country;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return "(%s) %s, %s, %s".formatted(addressType, country, city, address);
    }
}

