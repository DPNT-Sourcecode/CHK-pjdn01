package befaster.solutions.CHK.model;

import befaster.solutions.CHK.model.enums.ItemType;

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

