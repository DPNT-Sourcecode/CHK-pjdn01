package befaster.solutions.CHK;

public class Offer {
    private final Integer quantity;
    private final ItemType itemType;
    private final Integer unitPrice;
    private final OfferType offerType;
    private final Integer freebieUnit;
    private final Integer frequency;

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = 0;
        this.frequency = 0;
    }

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
        this.frequency = 0;
    }

    public Offer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit, Integer frequency) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
        this.frequency = frequency;
    }


    public int getQuantity() {
        return quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public int getFreebieUnit() {
        return freebieUnit;
    }

    public int getFrequency() {
        return frequency;
    }
}

