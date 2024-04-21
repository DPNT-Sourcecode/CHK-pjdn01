package befaster.solutions.CHK.model;

import befaster.solutions.CHK.model.enums.ItemType;

import java.util.HashMap;

import java.util.Map;

public class Group {
    private final Map<ItemType, GroupMember> members = new HashMap<>();
    private final int unitPrice;
    private final int quantity;

    public Group(int unitPrice, int quantity, Map<ItemType, GroupMember> members) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.members.putAll(members);
    }


    public void addGroupMember(ItemType itemType, GroupMember groupMember) {
        if (itemType != null && groupMember != null) {
            members.put(itemType, groupMember);
        }
    }

    public Map<ItemType, GroupMember> getMembers() {
        return members;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
