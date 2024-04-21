package befaster.solutions.CHK;

public class Offer {
    private final Integer quantity;
    private final ItemType itemType;
    private final Integer unitPrice;
    private final OfferType offerType;
    private final Integer freebieUnit;
    private final Integer frequency;

    private final String groupDiscountName;

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = null;
        this.frequency = null;
        this.groupDiscountName = null;
    }

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
        this.frequency = null;
        this.groupDiscountName = null;
    }

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit, Integer frequency) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
        this.frequency = frequency;
        this.groupDiscountName = null;
    }

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit, Integer frequency, String groupDiscountName) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
        this.frequency = frequency;
        this.groupDiscountName = groupDiscountName;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public Integer getFreebieUnit() {
        return freebieUnit;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getGroupDiscountName() {
        return groupDiscountName;
    }
}

