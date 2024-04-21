package befaster.solutions.CHK.model;

public class GroupMember {
    private final ItemPrice itemPrice;
    private final int quantity;

    public GroupMember(ItemPrice itemPrice, int quantity) {
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public ItemPrice getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}

