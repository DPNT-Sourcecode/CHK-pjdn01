package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public enum ItemType {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    UNKNOWN("UNKNOWN");

    private static  final Map<String, ItemType> enums = new HashMap<>(5);
    static {
        for(ItemType itemType: ItemType.values()){
            enums.put(itemType.toString())
        }
    }
    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public static ItemType forName(String name) {
        ItemType itemType =
    }

    public String toString() {
        return this.type;
    }
}
