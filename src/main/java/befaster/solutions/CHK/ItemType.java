package befaster.solutions.CHK;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ItemType {
    A("A"),
    B("B"),
    C("C"),
    D("D"),

    E("E"),
    F("F"),
    UNKNOWN("UNKNOWN");

    private static final Map<String, ItemType> enums = new HashMap<>(5);

    static {
        for (ItemType itemType : ItemType.values()) {
            enums.put(itemType.type, itemType);
        }
    }

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public static ItemType forName(String name) {
        ItemType itemType = enums.get(name);
        if (itemType == null) {
            return UNKNOWN;
        }
        return itemType;
    }

    @JsonValue
    public String toString() {
        return this.type;
    }
}

