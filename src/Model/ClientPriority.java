package Model;

public enum ClientPriority {
    CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low");

    private final String name;

    public static boolean contains(String test) {

        for (ClientPriority c : ClientPriority.values()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }


    ClientPriority(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
