package Model.Contact;

enum NumberType {
    HOME, OFFICE, MOBILE, FAX, OTHER
}

public class PhoneNumber {
    private NumberType numberType;
    private String number;

    public PhoneNumber(String number, NumberType numberType) {
        this.numberType = numberType;
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
