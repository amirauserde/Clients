package Model;

public enum ClientPriority {
    CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low");

    private final String name;


    ClientPriority(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
