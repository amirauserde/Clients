package Model;

public enum ClientStatus {
    PROSPECT("Prospect"), NEW("New"), CURRENT("Current"), PAST("Past");

    private final String name;

    public static boolean contains(String test) {

        for (ClientStatus c : ClientStatus.values()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    ClientStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
