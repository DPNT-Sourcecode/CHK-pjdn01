package befaster.solutions.CHK;

public class Freebies {
    private final ItemType itemType;
    private final int quantity;

    public Freebies(ItemType itemType, int quantity) {
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }
}

