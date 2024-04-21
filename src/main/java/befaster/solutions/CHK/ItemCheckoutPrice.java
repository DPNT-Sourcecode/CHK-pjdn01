package befaster.solutions.CHK;

import java.util.List;

public class ItemCheckoutPrice {
    private final ItemType itemType;
    private final List<Integer> prices;

    public ItemCheckoutPrice(ItemType itemType, List<Integer> prices) {
        this.itemType = itemType;
        this.prices = prices;
    }
}

