package Model;

public record Company(String name, String nationalCode) {

    @Override
    public String toString() {
        return name + "(" + nationalCode + ")";
    }
}
