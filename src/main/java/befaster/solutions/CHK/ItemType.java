package befaster.solutions.CHK;

public enum ItemType {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    UNKNOWN("UNKNOWN");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}




