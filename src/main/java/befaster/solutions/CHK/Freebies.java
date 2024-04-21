package befaster.solutions.CHK;

public class Freebies {
    private final ItemType itemType;
    private final int numberOfItem;

    public Freebies(ItemType itemType, int numberOfItem) {
        this.itemType = itemType;
        this.numberOfItem = numberOfItem;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }
}
