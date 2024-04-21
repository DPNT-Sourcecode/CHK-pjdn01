package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public enum OfferType {
    DISCOUNT("Discount"),
    GROUP_DISCOUNT("GroupDiscount"),
    FREEBIES("Freebies"),
    UNKNOWN("UNKNOWN");

    private static final Map<String, OfferType> enums = new HashMap<>(5);

    static {
        for (OfferType offerType : OfferType.values()) {
            enums.put(offerType.type, offerType);
        }
    }

    private final String type;

    OfferType(String type) {
        this.type = type;
    }

    public static OfferType forName(String name) {
        OfferType offerType = enums.get(name);
        if (offerType == null) {
            return UNKNOWN;
        }
        return offerType;
    }

    public String toString() {
        return this.type;
    }
}

