package befaster.solutions.CHK;

public class Offer {
    private final int quantity;
    private final ItemType itemType;
    private final int unitPrice;
    private final OfferType offerType;
    private final int freebieUnit;

    public Offer(int quantity, ItemType itemType, int unitPrice, OfferType offerType) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = 0;
    }

    public Offer(int quantity, ItemType itemType, int unitPrice, OfferType offerType, int freebieUnit) {
        this.quantity = quantity;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.offerType = offerType;
        this.freebieUnit = freebieUnit;
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
}
