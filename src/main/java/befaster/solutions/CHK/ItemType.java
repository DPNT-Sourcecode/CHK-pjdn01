package befaster.solutions.CHK;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum ItemType implements Serializable {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I"),
    J("J"),
    K("K"),
    L("L"),
    M("M"),
    N("N"),
    O("O"),
    P("P"),
    Q("Q"),
    R("R"),
    S("S"),
    T("T"),
    U("U"),
    V("V"),
    W("W"),
    X("X"),
    Y("Y"),
    Z("Z"),

    //Group Names
    GroupA("GroupA"),
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

    @JsonCreator
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


