package Model.Contact;

import Model.ClientPriority;
import util.LookupUtil;

enum NumberType {
    HOME, OFFICE, MOBILE, FAX, OTHER;

    static public NumberType lookup(String id) {
        return LookupUtil.lookup(NumberType.class, id);
    }
}

public class PhoneNumber {
    private NumberType numberType;
    private String number;

    public PhoneNumber(String number, String  numberType) {
        this.numberType = NumberType.lookup(numberType.toUpperCase());
        this.number = numberFormat(number);
    }

    public String getNumber() {
        return number.replaceAll("[()-]", "");
    }

    @Override
    public String toString() {
        return "%-10s%s".formatted(numberType, numberFormat(number));
    }
    private String numberFormat(String number) {
        return number.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }

}
